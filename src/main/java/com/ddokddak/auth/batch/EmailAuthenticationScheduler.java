package com.ddokddak.auth.batch;

import com.ddokddak.auth.repository.EmailAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailAuthenticationScheduler {
    @Resource(name="emailAuthenticationRepository")
    private EmailAuthenticationRepository emailAuthenticationRepository;

    @Transactional
    //@Scheduled(cron = "0 * * * * *") // 1분
    //@Scheduled(cron = "0 0 0 * * *")
    public void run(){
        //System.out.println("1분 주기 실행!!");
        emailAuthenticationRepository.findAll().forEach(target -> {
            //if( target.isNextSeconds() ){
            if( target.isNextDay() ){
                target.initializeTransmissionCount();
            }
        });
    }

}
