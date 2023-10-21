package com.ddokddak.auth.service;

import com.ddokddak.auth.entity.EmailAuthentication;
import com.ddokddak.auth.repository.EmailAuthenticationRepository;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import com.ddokddak.member.dto.checkAuthenticationNumberRequest;
import com.ddokddak.member.dto.requestAuthenticationNumberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

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

    public void mailSendingProcess(requestAuthenticationNumberRequest request) {
        String authenticationNumber = getRandomCode();
        EmailAuthentication target  = verifyTarget(
                                        request.addressee(),
                                        request.authenticationType(),
                                        authenticationNumber
                                    );
        send( request.addressee(), request.authenticationType(), authenticationNumber );
        emailAuthenticationRepository.save( target );
    }

    private EmailAuthentication verifyTarget(String addressee, String authenticationType, String authenticationNumber) {
        var target = emailAuthenticationRepository
                                    .findByAddresseeAndAuthenticationType(addressee, authenticationType )
                                    .orElse(EmailAuthentication
                                            .builder()
                                            .addressee(addressee)
                                            .authenticationNumber(authenticationNumber)
                                            .authenticationType(authenticationType)
                                            .build()
                                    );

        if( target.isExceedingCountOfPossible() ){
            throw new NotValidRequestException("시도 가능 횟수를 초과했습니다.");
        }

        target.plusTransmissionCount();
        target.modifyAuthenticationNumber(authenticationNumber);

        return target;
    }

    private void send(String addressee, String authenticationType, String authenticationNumber) {
        try {
            sender.send( createForm(addressee, authenticationType, authenticationNumber) );
        } catch (Exception e) {
            log.error(EmailAuthenticationService.class.getEnclosingMethod() + "ERROR ::: addressee : {}, authenticationType : {}, content : {}", addressee, authenticationType, authenticationNumber);
            throw new CustomApiException("UNABLE TO SEND EMAIL");
        }
    }

    private MimeMessage createForm(String addressee, String authenticationType, String authenticationNumber) throws MessagingException {
        MimeMessage mailMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "UTF-8");
            helper.setTo(addressee);
            helper.setSubject("[똑딱] 이메일 인증 번호");

            //메일 내용 설정 : 템플릿 프로세스
            Context context = new Context();
            context.setVariable("authenticationNumber", authenticationNumber);

            String html = templateEngine.process(authenticationType, context);
            helper.setText(html, true);
        }catch(Exception e){
            e.printStackTrace();
        }
        return mailMessage;
    }

    public boolean checkAuthenticationNumber(checkAuthenticationNumberRequest request) {
        var searchEmail = emailAuthenticationRepository
                                            .findByAddresseeAndAuthenticationType( request.addressee(), request.authenticationNumber() )
                                            .orElseThrow(() -> new NotValidRequestException(NotValidRequest.TEMP_EMAIL_ID));

        return isWithinValidTime( searchEmail )
                && equalsAuthCode( searchEmail.getAuthenticationNumber(), request.authenticationNumber() );
    }

    private boolean equalsAuthCode(String storedNumber, String targetNumber) {
        return storedNumber.equals(targetNumber);
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
            log.error("EmailAuthenticationSerivce.getRandomNumber() exception occur");
            throw new CustomApiException("Wrong Match Auth Provider");
        }
        return result.toString();
    }

}