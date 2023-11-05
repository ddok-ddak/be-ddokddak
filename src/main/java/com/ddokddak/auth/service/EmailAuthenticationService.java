package com.ddokddak.auth.service;

import com.ddokddak.auth.entity.EmailAuthentication;
import com.ddokddak.auth.repository.EmailAuthenticationRepository;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.EmailException;
import com.ddokddak.member.dto.AuthenticationNumberRequest;
import com.ddokddak.member.dto.CheckEmailAuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
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

    public Long mailSendingProcess(AuthenticationNumberRequest request) {
        String authenticationNumber = getRandomCode();
        EmailAuthentication target  = verifyTarget(
                                        request.email(),
                                        request.authenticationType(),
                                        authenticationNumber
                                    );
        send( request.email(), request.authenticationType(), authenticationNumber );
        return emailAuthenticationRepository.save( target ).getId();
    }

    private EmailAuthentication verifyTarget(String email, String authenticationType, String authenticationNumber) {
        var target = emailAuthenticationRepository
                                    .findByEmailAndAuthenticationType(email, authenticationType )
                                    .orElse(EmailAuthentication
                                            .builder()
                                            .email(email)
                                            .authenticationNumber(authenticationNumber)
                                            .authenticationType(authenticationType)
                                            .build()
                                    );

        if( target.isExceedingTransmissionCountOfPossible() ){
            throw new NotValidRequestException(EmailException.EXCEEDED_TRANSMISSION_LIMIT_COUNT);
        }

        if( target.isExceedingFailCountOfPossible() ){
            throw new NotValidRequestException(EmailException.EXCEEDED_RETRY_LIMIT_COUNT);
        }

        if( target.getId()!=null && target.isNextDay() ){
            target.initializeTransmissionCount();
        }

        target.plusTransmissionCount();
        target.modifyAuthenticationNumber(authenticationNumber);

        return target;
    }

    private void send(String email, String authenticationType, String authenticationNumber) {
        try {
            sender.send( createForm(email, authenticationType, authenticationNumber) );
        } catch (Exception e) {
            log.error( EmailAuthenticationService.class.getEnclosingMethod() + "ERROR ::: {}", e.getMessage() );
            throw new CustomApiException(EmailException.FAIL_TO_MAIL);
        }
    }

    private MimeMessage createForm(String email, String authenticationType, String authenticationNumber) {
        MimeMessage mailMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[DoDone] 이메일 인증 번호");

            //메일 내용 설정 : 템플릿 프로세스
            Context context = new Context();
            context.setVariable("authenticationNumber", authenticationNumber);

            String html = templateEngine.process(authenticationType, context);
            helper.setText(html, true);
        }catch(MessagingException e){
            log.error( "EmailAuthenticationService.createForm() exception occur ::: {}", e.getMessage() );
            throw new CustomApiException(EmailException.FAIL_TO_CREATING_MAIL_FORM);
        }
        return mailMessage;
    }

    private boolean equalsAuthCode(String storedNumber, String targetNumber) {
        return storedNumber.equals(targetNumber);
    }

    public boolean checkAuthenticationNumber(CheckEmailAuthenticationRequest request) {
        var searchEmail = emailAuthenticationRepository
                .findByIdAndAuthenticationNumber( request.authenticationRequestId(), request.authenticationNumber() )
                .orElseThrow( () -> new NotValidRequestException(EmailException.NOT_VALID_AUTHENTICATION_NUMBER) );

        return isWithinValidTime( searchEmail )
                && equalsAuthCode( searchEmail.getAuthenticationNumber(), request.authenticationNumber() );
    }

    private boolean isWithinValidTime(EmailAuthentication target) {
        return target.isExceedingTimeOfPossible();
    }

    private String getRandomCode() {
        StringBuilder result = new StringBuilder();
        try {
            SecureRandom randomForCombination = SecureRandom.getInstance("SHA1PRNG");
            for( int i=0;i<8;i++ ){
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