package com.ddokddak.auth.service;

import com.ddokddak.auth.domain.entity.EmailAuthentication;
import com.ddokddak.auth.domain.enums.EmailAuthenticationType;
import com.ddokddak.auth.repository.EmailAuthenticationRepository;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.EmailException;
import com.ddokddak.auth.domain.dto.AuthenticationNumberRequest;
import com.ddokddak.auth.domain.dto.CheckEmailAuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailAuthenticationService {
    private final JavaMailSender sender;
    private final EmailAuthenticationRepository emailAuthenticationRepository;
    private final TemplateEngine templateEngine;
    private final String MAIL_SUBJECT = "[DoDone] 이메일 인증 번호를 알려드립니다.";

    @Transactional
    public Long mailSendingProcess(AuthenticationNumberRequest request) {
        EmailAuthentication target  = verifyTarget(
                                        request.email(),
                                        request.authenticationType()
                                    );

        send( request.email(), request.authenticationType(), target.getAuthenticationNumber() );
        return emailAuthenticationRepository.save( target ).getId();
    }

    @Transactional
    public EmailAuthentication verifyTarget(String email, EmailAuthenticationType authenticationType) {
        var target = emailAuthenticationRepository
                                    .findByEmailAndAuthenticationType(email, authenticationType)
                                    .orElse(EmailAuthentication
                                            .builder()
                                            .email(email)
                                            .authenticationType(authenticationType)
                                            .build()
                                    );

        if( target.getId()!=null && target.isNextDay() ){
            target.initializeTransmissionCount();
        }
        if( target.isExceedingTransmissionCountOfPossible() ){
            throw new NotValidRequestException(EmailException.EXCEEDED_TRANSMISSION_LIMIT_COUNT);
        }
        target.modifyAuthenticationNumber(getRandomCode());
        target.plusTransmissionCount();
        return target;
    }

    private void send(String email, EmailAuthenticationType authenticationType, String authenticationNumber) {
        try {
            sender.send( createForm(email, authenticationNumber) );
        } catch (MailAuthenticationException e) {
            log.error( EmailAuthenticationService.class.getEnclosingMethod() + "ERROR ::: {}", e.getMessage() );
            throw new CustomApiException(EmailException.FAIL_TO_MAIL_AUTH);
        } catch (MailSendException e) {
            log.error( EmailAuthenticationService.class.getEnclosingMethod() + "ERROR ::: {}", e.getMessage() );
            throw new CustomApiException(EmailException.FAIL_TO_MAIL);
        }
    }

    private MimeMessage createForm(String email, String authenticationNumber) {
        MimeMessage mailMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "UTF-8");
            helper.setTo(email);
            helper.setSubject(MAIL_SUBJECT);

            //메일 내용 설정 : 템플릿 프로세스
            Context context = new Context();
            context.setVariable("authenticationNumber", authenticationNumber);

            String html = templateEngine.process("MAIL_AUTH", context);
            helper.setText(html, true);
        } catch (MessagingException e) {
            log.error( "EmailAuthenticationService.createForm() exception occur ::: {}", e.getMessage() );
            throw new CustomApiException(EmailException.FAIL_TO_CREATING_MAIL_FORM);
        }
        return mailMessage;
    }

    private boolean equalsAuthCode(String storedNumber, String targetNumber) {
        return storedNumber.equals(targetNumber);
    }

    @Transactional
    public boolean checkAuthenticationNumber(CheckEmailAuthenticationRequest request) {
        var searchEmail = emailAuthenticationRepository
                .findById(request.authenticationRequestId())
                .orElseThrow(() -> new NotValidRequestException(EmailException.NOT_VALID_ID));

        if (!searchEmail.isExceedingTimeOfPossible()) throw new CustomApiException(EmailException.EXCEEDED_TIME_LIMIT);
        if (!searchEmail.isExceedingFailCountOfPossible()) throw new CustomApiException(EmailException.EXCEEDED_RETRY_LIMIT_COUNT);

        if (!equalsAuthCode(searchEmail.getAuthenticationNumber(), request.authenticationNumber())) {
            searchEmail.plusFailCount();
            throw new CustomApiException(EmailException.NOT_VALID_AUTHENTICATION_NUMBER);
        }
        return Boolean.TRUE;
    }

    private String getRandomCode() {
        StringBuilder result = new StringBuilder();
        try {
            SecureRandom randomForCombination = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < 6; i++) {
                switch( randomForCombination.nextInt(3) ){
                    case 0 -> result.append((char) ((randomForCombination.nextInt(26)) + 97)); // a~z  (ex. 1+97=98 => (char)98 = 'b')
                    case 1 -> result.append((char) ((randomForCombination.nextInt(26)) + 65)); // A~Z
                    case 2 -> result.append((randomForCombination.nextInt(10))); // 0~9
                    default -> result.append((0));
                }
            }

        } catch (NoSuchAlgorithmException e) {
            log.error("EmailAuthenticationService.getRandomNumber() exception occur");
            throw new CustomApiException(EmailException.WRONG_MATCH_AUTHENTICATION_PROVIDER);
        }
        return result.toString();
    }
}