package com.ddokddak.auth.entity;

import com.ddokddak.auth.enums.AuthenticationType;
import com.ddokddak.common.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class EmailAuthentication extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 100)
    private String addressee;

    @Column(unique = true)
    private String authenticationNumber;

    @NotNull
    private String authenticationType;

    private int transmissionCount;

    public void initializeTransmissionCount() {
        this.transmissionCount = 0;
    }
//
    public void plusTransmissionCount() {
        this.transmissionCount++;
    }

    public void modifyAuthenticationNumber(String authenticationNumber){
        this.authenticationNumber = authenticationNumber;
    }

    public boolean isNextDay(){
        return this.getModifiedAt().plusDays(1).isAfter( LocalDateTime.now() );
    }

    /* 1분 후에 대한 테스트용
    public boolean isNextSeconds(){
        return this.getModifiedAt().plusSeconds(60000L).isAfter( LocalDateTime.now() );
    }*/
    public boolean isExceedingCountOfPossible(){
        return this.getTransmissionCount()==5;
    }
    public boolean isExceedingTimeOfPossible(){
        return this.getModifiedAt().plusMinutes(5).isAfter( LocalDateTime.now() );
    }
}