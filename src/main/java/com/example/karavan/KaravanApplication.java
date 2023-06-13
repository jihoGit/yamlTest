package com.example.karavan;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KaravanApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaravanApplication.class, args);
    }

//    @Bean
//    public CamelContext camelContext() {
//        return new DefaultCamelContext();
//    }

}
