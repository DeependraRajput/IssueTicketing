package me.rajput.practice.it.model.web;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.rajput.practice.it.model.UserType;

/**
 * 
 * Description: Data model representing a user on the web page. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@Data
@EqualsAndHashCode(of="loginId")
public class UserForm {
	
	@NotBlank
	@Pattern(regexp = "[a-z]+\\d*")
	private String loginId;
	
	@NotNull
	@Email
	private String email;
	
	@NotBlank
	@Pattern(regexp = "[A-Z][a-z]+")
    private String firstName;
	
	@Pattern(regexp = "[A-Z][a-z]+")
    private String middleName;
	
	@NotBlank
	@Pattern(regexp = "[A-Z][a-z]+")
    private String lastName;
    
    @NotNull
    private UserType type;
    
    @NotNull
    private Date updatedAt;

}
