package com.ddokddak.common.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private static String baseUrl;
    public static String getBaseUrl() {
        return baseUrl;
    }
}
