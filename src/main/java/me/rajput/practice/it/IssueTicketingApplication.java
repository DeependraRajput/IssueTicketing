package me.rajput.practice.it;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IssueTicketingApplication {

    public static void main(final String[] args) {
        SpringApplication.run(IssueTicketingApplication.class, args);
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
}
