package org.example.ristoranteprogetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.example.ristoranteprogetto.Repository")
@EntityScan("org.example.ristoranteprogetto.Model.Entity")
public class RistoranteProgettoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RistoranteProgettoApplication.class, args);
    }

}
