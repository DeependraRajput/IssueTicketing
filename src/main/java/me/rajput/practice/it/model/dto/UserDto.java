package me.rajput.practice.it.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.rajput.practice.it.model.UserType;

@Data
@EqualsAndHashCode(of="loginId")
public class UserDto {
	
	private String loginId;
    private String firstName;
    private String middleName;
    private String lastName;
    private UserType type;
}
