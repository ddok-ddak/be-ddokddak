package com.ddokddak.utils.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void encodeTest() {
        String passwd = "test1234";
        String firstEncoded = passwordEncoder.encode(passwd);
        String secondEncoded = passwordEncoder.encode(passwd);

        System.out.println("first try: " + firstEncoded);
        System.out.println("second try: " + secondEncoded);
        Assertions.assertTrue(!firstEncoded.equals(secondEncoded));
    }

    @Test
    void matchTest() {
        String passwd = "test1234";
        Assertions.assertTrue(passwordEncoder.matches(passwd, passwordEncoder.encode(passwd)));
    }

}
