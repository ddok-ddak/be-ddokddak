package com.ddokddak.common.config;

import com.ddokddak.auth.handler.JwtAccessDeniedHandler;
import com.ddokddak.auth.handler.OAuth2AuthenticationFailureHandler;
import com.ddokddak.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.ddokddak.auth.repository.OAuth2CookieAuthorizationRequestRepository;
import com.ddokddak.auth.handler.JwtAuthenticationEntryPoint;
import com.ddokddak.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2CookieAuthorizationRequestRepository oAuth2CookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    /*
     * security 설정 시, 사용할 인코더 설정
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authz -> authz
                        .antMatchers("/", "/css/**").permitAll()

                        // TODO 아래 두 접근에 대해서 관리자만 접근 가능하도록 제한 설정을 해둘 필요성!
                        // actuator 라이브러리 추가할지 의논해볼 필요
                        .antMatchers("/actuator/health", "/h2-console/**").permitAll()
                        .antMatchers("/docs/**", "/v3/api-docs", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                        .anyRequest().permitAll() //.authenticated()
                )
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2CookieAuthorizationRequestRepository)
                .and()
                .userInfoEndpoint()
//                .userService(customOAuth2UserService) 명시하지 않아도 빈에 등록되어 우선적으로 활용된다.
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)	// 401
                .accessDeniedHandler(jwtAccessDeniedHandler);		// 403

        return http.build();
    }
}