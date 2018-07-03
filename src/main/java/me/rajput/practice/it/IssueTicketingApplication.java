package me.rajput.practice.it;

import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.annotation.SessionScope;

import me.rajput.practice.it.domain.User;
import me.rajput.practice.it.domain.UserType;
import me.rajput.practice.it.services.auditing.ApplicationAuditorAware;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware") //, dateTimeProviderRef = "dateTimeProvider")
//@EnableAspectJAutoProxy
public class IssueTicketingApplication {

    public static void main(final String[] args) {
    	//Run the application in UTC time zone for portability.
    	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
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
    
    //Ignore this bean for now.
    @Bean
    @SessionScope
    public User currentUser() {
        User User = new User();
        User.setType(UserType.INVALID);
		return User;
    }
    
    /**
     * Just a sample for feature.
     * @return
     */
//    @Bean
//    public DateTimeProvider dateTimeProvider() {
//    	return new UniversalDateTimeProvider();
//    }
    
    @Bean
    public AuditorAware auditorAware() {
    	return new ApplicationAuditorAware();
    }
    
}
