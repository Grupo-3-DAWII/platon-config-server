package edu.cibertec.platon.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class PlatonConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatonConfigServerApplication.class, args);
    }
}
