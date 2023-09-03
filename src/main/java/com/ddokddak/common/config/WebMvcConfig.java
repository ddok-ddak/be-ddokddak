package com.ddokddak.common.config;

import com.ddokddak.common.props.CorsProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}