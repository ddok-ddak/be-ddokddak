package com.ddokddak.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ThymeleafConfig {
    @Value("${spring.thymeleaf.prefix}")
    private String prefix;
    @Value("${spring.thymeleaf.suffix}")
    private String suffix;
    @Value("${spring.thymeleaf.mode}")
    private String mode;
    @Value("${spring.thymeleaf.check-template-location}")
    private String checkTemplateLocation;
    @Value("${spring.thymeleaf.cache}")
    private boolean cache;
    @Value("${spring.thymeleaf.encoding}")
    private String encoding;

    @Bean
    public TemplateEngine htmlTemplateEngine() {
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(springResourceTemplateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver() {
        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setOrder(1);
        springResourceTemplateResolver.setPrefix(prefix);
        springResourceTemplateResolver.setSuffix(suffix);
        springResourceTemplateResolver.setTemplateMode(mode);
        springResourceTemplateResolver.setCharacterEncoding(encoding);
        springResourceTemplateResolver.setCacheable(cache);

        return springResourceTemplateResolver;
    }
}
