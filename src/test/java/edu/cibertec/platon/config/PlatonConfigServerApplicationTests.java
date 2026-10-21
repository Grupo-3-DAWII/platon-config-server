package edu.cibertec.platon.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "eureka.client.enabled=false")
class PlatonConfigServerApplicationTests {

    @Test
    void contextLoads() {
    }
}
