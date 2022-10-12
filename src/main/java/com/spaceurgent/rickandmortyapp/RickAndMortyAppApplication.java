package com.spaceurgent.rickandmortyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@EnableScheduling
@SpringBootApplication
public class RickAndMortyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RickAndMortyAppApplication.class, args);
    }

    @Bean
    @Description("Spring Message Resolver")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
}
