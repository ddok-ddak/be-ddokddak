package com.ddokddak;

import com.ddokddak.common.props.AppProperties;
import com.ddokddak.common.props.AuthProperties;
import com.ddokddak.common.props.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({CorsProperties.class, AuthProperties.class, AppProperties.class})
@SpringBootApplication
public class BeDdokddakApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeDdokddakApplication.class, args);
    }

}
