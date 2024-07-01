package com.ddokddak.common.config;

import com.ddokddak.common.props.CorsProperties;
import com.ddokddak.common.utils.CaseInsensitiveEnumConverter;
import com.ddokddak.member.domain.enums.AuthProviderType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AllArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private CorsProperties corsProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns(corsProperties.getAllowedOriginPatterns())
                //.allowedOrigins(corsProperties.getAllowedOriginPatterns())
                .allowedMethods(corsProperties.getAllowedMethods())
                .allowedHeaders(corsProperties.getAllowedHeaders())
                //.exposedHeaders(JwtUtil.AUTHORIZATION_HEADER) // , "*"
                .maxAge(corsProperties.getMaxAgeSec());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        List<Class<? extends Enum>> enums = List.of(AuthProviderType.class);
        enums.forEach(enumClass -> registry.addConverter(String.class, enumClass,
                new CaseInsensitiveEnumConverter<>(enumClass)));
    }
}