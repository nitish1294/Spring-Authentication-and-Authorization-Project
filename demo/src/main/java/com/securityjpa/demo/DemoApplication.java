package com.securityjpa.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.securityjpa.demo")  // Ensure this is the correct package where 'repo' is located
@EntityScan(basePackages = "com.securityjpa.demo")  // Ensure this is the correct package where your entity classes are located
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

