package org.superbiz.moviefun.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MovieServicesApplication {
    public static void main(String... args) {
        SpringApplication.run(MovieServicesApplication.class, args);
    }
}