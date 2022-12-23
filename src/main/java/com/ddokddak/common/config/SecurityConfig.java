package com.ddokddak.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용하지 않음, 토큰 방식
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/", "/css/**").permitAll()

                        // TODO 아래 두 접근에 대해서 관리자만 접근 가능하도록 제한 설정을 해둘 필요성!
                        // actuator 라이브러리 추가할지 의논해볼 필요
                        .antMatchers("/actuator/health", "/h2-console/**").permitAll()
                        .antMatchers("/docs/**", "/v3/api-docs", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                        .anyRequest().permitAll() //.authenticated()
                );

        return http.build();
    }
}
