package me.rajput.practice.it.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.rajput.practice.it.model.UserType;

@Data
@EqualsAndHashCode(of="loginId")
public class User implements Cloneable {
	
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
    
    @Override
    public User clone() {
    	try {
			return (User)super.clone();
		} catch (CloneNotSupportedException willNotOccur) {}
		return null;
    }
}
