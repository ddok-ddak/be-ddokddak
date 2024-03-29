package com.ddokddak.auth.service;

import com.ddokddak.auth.domain.enums.EmailAuthenticationType;
import com.ddokddak.auth.repository.EmailAuthenticationRepository;
import com.ddokddak.auth.domain.dto.AuthenticationNumberRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
@SpringBootTest
//@Transactional // 롤백 수행 <- 레이지 로딩이 동작하지 않음
class AuthServiceTest {
    /*@Spy
    @InjectMocks*/
    @Autowired
    EmailAuthenticationService emailAuthenticationService;
    //@Mock
    @Autowired
    EmailAuthenticationRepository emailAuthenticationRepository;
    //@Mock
    @Autowired
    JavaMailSender sender;
    //@Mock
    @Autowired
    TemplateEngine templateEngine;

    /*@Autowired
    DatabaseCleanUp databaseCleanUp;*/

    final AuthenticationNumberRequest request = AuthenticationNumberRequest.builder()
            .email("yon914@naver.com")
            .authenticationType(EmailAuthenticationType.JOIN)
            .build();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        /*databaseCleanUp.afterPropertiesSet();
        databaseCleanUp.execute();*/
        // 레포지토리를 이용해 데이터를 삭제하고자 하는 경우에는 소프트 딜리트를 사용하므로 따로 테스트용 딜리트 메소드가 필요하다.
    }

    @Test
    void testMailSendingProcess() throws Exception {
        // given

        // when
        /*doNothing().when( emailAuthenticationService ).mailSendingProcess( request );
        lenient().doNothing().when(emailAuthenticationService).mailSendingProcess(request);

        // then
        verify( emailAuthenticationService, times(1) ).mailSendingProcess( request );*/
/*
        // when
        var createdId = emailAuthenticationService.mailSendingProcess( request );

        // then
        Assertions.assertNotNull(emailAuthenticationRepository.findById(createdId));*/
    }

    @Test
    void testCheckAuthenticationNumber() throws Exception {
        // given
        /*RequestAuthenticationNumberRequest request1 = RequestAuthenticationNumberRequest.builder()
                                                                                        .email("yon914@naver.com")
                                                                                        .authenticationType("JOIN")
                                                                                        .build();
        emailAuthenticationService.mailSendingProcess( request1 );

        EmailAuthentication target = emailAuthenticationRepository.findByemail("yon914@naver.com").get();

        CheckAuthenticationNumberRequest request2 = CheckAuthenticationNumberRequest.builder()
                                                                    .email("yon914@naver.com")
                                                                    .authenticationNumber(target.getAuthenticationNumber())
                                                                    .build();

        // when
        boolean result = emailAuthenticationService.checkAuthenticationNumber( request2 );

        // then
        Assertions.assertTrue( result );*/
    }

    @Test
    void testCheckToExceedingCountOfPossible() throws Exception {
        /*
        // given
        RequestAuthenticationNumberRequest request1 = RequestAuthenticationNumberRequest.builder()
                                                                                        .email("yon914@naver.com")
                                                                                        .authenticationType("JOIN")
                                                                                        .build();

        for( int i=0;i<5;i++ ){
            emailAuthenticationService.mailSendingProcess( request1 );
        }

        assertThrows( NotValidRequestException.class, () ->{
            emailAuthenticationService.mailSendingProcess( request1 );
        }).getStatus().equals("HttpStatus.TOO_MANY_REQUESTS");*/
    }

    @Test
    void testInitializationScheduler() throws InterruptedException {
        /*
        // given
        RequestAuthenticationNumberRequest request1 = RequestAuthenticationNumberRequest.builder()
                                                                                        .email("yon914@naver.com")
                                                                                        .authenticationType("JOIN")
                                                                                        .build();

        for( int i=0;i<5;i++ ){ // 5번 초과
            emailAuthenticationService.mailSendingProcess( request1 );
        }

        Thread.sleep(60000L);

        EmailAuthentication target = emailAuthenticationRepository
                                            .findByemailAndAuthenticationType("yon914@naver.com", "JOIN").get();

        assertThat( target.getTransmissionCount() ).isEqualTo(0);
        */
    }


}