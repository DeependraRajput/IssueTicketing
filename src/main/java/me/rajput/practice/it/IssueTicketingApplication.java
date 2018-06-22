package me.rajput.practice.it;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.annotation.SessionScope;

import me.rajput.practice.it.model.UserType;
import me.rajput.practice.it.model.db.User;

@SpringBootApplication
@EnableJpaAuditing
public class IssueTicketingApplication {

    public static void main(final String[] args) {
        SpringApplication.run(IssueTicketingApplication.class, args);
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    
    @Bean
    @SessionScope
    public User currentUser() {
        User User = new User();
        User.setType(UserType.INVALID);
		return User;
    }
    
}
