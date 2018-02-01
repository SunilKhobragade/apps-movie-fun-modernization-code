package org.superbiz.moviefun;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Profile("development")
@Configuration
@EnableOAuth2Sso
public class DevelopmentSecurityConfig {

    @Bean
    public RestOperations restTemplate() {
        return new RestTemplate();
    }
}
