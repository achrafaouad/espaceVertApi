package com.example.dashborad_pipe.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@Configuration
public class CorsFilterConfig {

    @Bean
    public org.springframework.web.filter.CorsFilter corsFilter() {
        System.out.println("************* filtring cross origin *************");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow requests from all origins
        //config.addAllowedOriginPattern("http://example.com");
        //config.addAllowedOriginPattern("http://localhost:8080");
        config.addAllowedOriginPattern("http://localhost:4200");
        config.addAllowedOriginPattern("https://178.170.116.28");
        config.addAllowedOriginPattern("https://localhost");
        config.addAllowedOriginPattern("*");
        // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        // Allow all headers
        config.addAllowedHeader("*");

        // Allow cookies to be sent/received
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    }
