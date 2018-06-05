package com.example.groovy_app

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableFeignClients
class GroovyApplication {

    static void main(String[] args) {
        SpringApplication.run GroovyApplication, args
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }

}
