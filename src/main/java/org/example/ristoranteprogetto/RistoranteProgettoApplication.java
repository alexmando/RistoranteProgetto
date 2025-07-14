package org.example.ristoranteprogetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("org.example.ristoranteprogetto.Repository")
@EntityScan("org.example.ristoranteprogetto.Model.Entity")
public class RistoranteProgettoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RistoranteProgettoApplication.class, args);
    }

}
