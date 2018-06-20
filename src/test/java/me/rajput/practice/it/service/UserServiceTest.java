package me.rajput.practice.it.service;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import me.rajput.practice.it.TestBase;
import me.rajput.practice.it.model.UserType;
import me.rajput.practice.it.model.dto.UserDto;
import me.rajput.practice.it.services.UserService;

@Transactional
public class UserServiceTest extends TestBase {
	
	@Autowired
	private UserService service;
	
	public void testSearchUsers() {
		
		UserDto user = new UserDto();
		user.setLastName("Rajput");
		service.searchUsers(user);
		
	}
	
	@Test
	public void testLogin() {
		
		UserDto currentUser = service.currentUser();
		
		Assert.assertNull("Current user must be null before login", currentUser.getType());
		
		UserDto loggedInUser = service.login("drajput", null);
		
		Assert.assertNull("Current user must be null before login", currentUser.getType());
		
		
		
	}

}
