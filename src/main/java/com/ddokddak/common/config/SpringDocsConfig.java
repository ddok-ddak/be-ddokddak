package com.ddokddak.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Configuration
public class SpringDocsConfig {
    @Bean
    public OpenAPI openAPI(@Value("${app.version}") String version) {

        SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthenticationPrincipal.class);

        Info info = new Info()
                .title("DoDone API 문서")
                .version(version)
                .description("보람찬 하루를 보내고자 하는 사람들을 위한 DoDone API 문서입니다.")
                .contact(new Contact()
                        .name("DoDone")
                        .email("dodone.cs@gmail.com")
                        .url("https://dodonenow.com/"));

        // Security 스키마 설정
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        // Security 요청 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("JWT");

        return new OpenAPI()
                // Security 인증 컴포넌트 설정
                .components(new Components().addSecuritySchemes("JWT", securityScheme))
                // API 마다 Security 인증 컴포넌트 설정
                .addSecurityItem(securityRequirement)
                .info(info);
    }
}
