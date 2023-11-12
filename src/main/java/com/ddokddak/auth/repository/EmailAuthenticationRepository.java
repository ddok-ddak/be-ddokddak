package com.ddokddak.auth.repository;

import com.ddokddak.auth.domain.entity.EmailAuthentication;
import com.ddokddak.auth.domain.enums.EmailAuthenticationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthenticationRepository extends JpaRepository<EmailAuthentication, Long> {
    Optional<EmailAuthentication> findByEmailAndAuthenticationType(String email, EmailAuthenticationType authenticationType);
    Optional<EmailAuthentication> findByIdAndAuthenticationNumber(Long authenticationRequestId, String authenticationNumber);
}
