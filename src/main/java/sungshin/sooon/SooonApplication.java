package sungshin.sooon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SooonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SooonApplication.class, args);
    }

}
