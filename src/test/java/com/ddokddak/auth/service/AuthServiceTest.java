package com.ddokddak.auth.service;

import com.ddokddak.DatabaseCleanUp;
import com.ddokddak.auth.batch.EmailAuthenticationScheduler;
import com.ddokddak.auth.entity.EmailAuthentication;
import com.ddokddak.auth.repository.EmailAuthenticationRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.member.dto.checkAuthenticationNumberRequest;
import com.ddokddak.member.dto.requestAuthenticationNumberRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
//@Transactional // 롤백 수행 <- 레이지 로딩이 동작하지 않음
class AuthServiceTest {

    @Autowired
    EmailAuthenticationService emailAuthenticationService;

    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @Autowired
    EmailAuthenticationRepository emailAuthenticationRepository;

    @Autowired
    EmailAuthenticationScheduler emailAthenticationScheduler;

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
        requestAuthenticationNumberRequest request = requestAuthenticationNumberRequest.builder()
                                                                    .addressee("yon914@naver.com")
                                                                    .authenticationType("JOIN")
                                                                    .build();

        // when
        emailAuthenticationService.mailSendingProcess( request );

        // then
        Assertions.assertNotNull(emailAuthenticationRepository.findByAddressee("yon914@naver.com"));
    }

    @Test
    void testCheckAuthenticationNumber() throws Exception {
        // given
        requestAuthenticationNumberRequest request1 = requestAuthenticationNumberRequest.builder()
                                                                                        .addressee("yon914@naver.com")
                                                                                        .authenticationType("JOIN")
                                                                                        .build();
        emailAuthenticationService.mailSendingProcess( request1 );

        EmailAuthentication target = emailAuthenticationRepository.findByAddressee("yon914@naver.com").get();

        checkAuthenticationNumberRequest request2 = checkAuthenticationNumberRequest.builder()
                                                                    .addressee("yon914@naver.com")
                                                                    .authenticationNumber(target.getAuthenticationNumber())
                                                                    .build();

        // when
        boolean result = emailAuthenticationService.checkAuthenticationNumber( request2 );

        // then
        Assertions.assertTrue( result );
    }

    @Test
    void testCheckToExceedingCountOfPossible() throws Exception {
        // given
        requestAuthenticationNumberRequest request1 = requestAuthenticationNumberRequest.builder()
                                                                                        .addressee("yon914@naver.com")
                                                                                        .authenticationType("JOIN")
                                                                                        .build();

        for( int i=0;i<5;i++ ){
            emailAuthenticationService.mailSendingProcess( request1 );
        }

        assertThrows( NotValidRequestException.class, () ->{
            emailAuthenticationService.mailSendingProcess( request1 );
        });
    }

    @Test
    void testInitializationScheduler() throws InterruptedException {
        // given
        requestAuthenticationNumberRequest request1 = requestAuthenticationNumberRequest.builder()
                                                                                        .addressee("yon914@naver.com")
                                                                                        .authenticationType("JOIN")
                                                                                        .build();

        for( int i=0;i<5;i++ ){ // 5번 초과
            emailAuthenticationService.mailSendingProcess( request1 );
        }

        Thread.sleep(60000L);

        EmailAuthentication target = emailAuthenticationRepository
                                            .findByAddresseeAndAuthenticationType("yon914@naver.com", "JOIN").get();

        assertThat( target.getTransmissionCount() ).isEqualTo(0);
    }


}