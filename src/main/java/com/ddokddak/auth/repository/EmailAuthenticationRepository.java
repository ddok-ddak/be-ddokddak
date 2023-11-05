package com.ddokddak.auth.repository;

import com.ddokddak.auth.entity.EmailAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthenticationRepository extends JpaRepository<EmailAuthentication, Long> {
    Optional<EmailAuthentication> findByEmailAndAuthenticationType(String email, String authenticationType);
    Optional<EmailAuthentication> findByIdAndAuthenticationNumber(Long authenticationRequestId, String authenticationNumber);
}
