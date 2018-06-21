package me.rajput.practice.it.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import me.rajput.practice.it.model.User;

public class UserControllerTest extends ControllerTestBase  {
	
	
	@Test
	public void testAddUser() {
		String url = "/addUser?loginId={userId}";
		
		String value = "drajput";
		
		ResponseEntity<User> userRe = restTemplate.getForEntity(url, User.class, value);
		
		Assert.assertEquals("Response status should be OK", HttpStatus.OK.value(), userRe.getStatusCodeValue());
		User user = userRe.getBody();
		
		Assert.assertNotNull("User Id must not be null", user.getId());
		Assert.assertEquals("User name must match", value, user.getLoginId());
		
	}
	
	@Test
	public void testAddUserTwice() {
		String url = "/addUser?loginId={userId}";
		
		String value = "drajput";
		
		ResponseEntity<User> userRe = restTemplate.getForEntity(url, User.class, value);
		
		Assert.assertEquals("Response status should be OK", HttpStatus.OK.value(), userRe.getStatusCodeValue());
		User user = userRe.getBody();
		
		Assert.assertNotNull("User Id must not be null", user.getId());
		Assert.assertEquals("User name must match", value, user.getLoginId());
		
		ResponseEntity<User> userRe2 = restTemplate.getForEntity(url, User.class, value);
		
		Assert.assertEquals("Response status should be OK", HttpStatus.OK.value(), userRe2.getStatusCodeValue());
		User user2 = userRe2.getBody();
		
		Assert.assertNotNull("User Id must not be null", user2.getId());
		Assert.assertEquals("User name must match", value, user2.getLoginId());
		
	}
	
	@Test
	public void testAddUserWithIllegalArgument() {
		String url = "/addUser?loginId={userId}";
		
		String value = "%drajput";
		
		ResponseEntity<User> userRe = restTemplate.getForEntity(url, User.class, value);
		
		Assert.assertEquals("Response status should be OK", HttpStatus.INTERNAL_SERVER_ERROR.value(), userRe.getStatusCodeValue());
		User user = userRe.getBody();
		
		Assert.assertNull("User Id must be null", user.getId());
		Assert.assertNull("User name must beNull", user.getLoginId());
	}
	
	@Test
	public void testFindUser() {
		
		String url = "/searchUser?loginId={id}";
		
		String value = "drajput";
		
		ResponseEntity<User> userRe = restTemplate.getForEntity(url, User.class, value);
		
		Assert.assertEquals("Response status should be OK", HttpStatus.OK.value(), userRe.getStatusCodeValue());
		User user = userRe.getBody();
		
		Assert.assertNotNull("User Id must not be null", user.getId());
		Assert.assertEquals("User name must match", value, user.getLoginId());
		
		
	}
	
	@Test
	public void testFindUserWithIllegalArgument() {
		String url = "/searchUser?loginId={id}";
		
		String value = "%drajput";
		
		ResponseEntity<User> userRe = restTemplate.getForEntity(url, User.class, value);
		
		Assert.assertEquals("Response status should be OK", HttpStatus.INTERNAL_SERVER_ERROR.value(), userRe.getStatusCodeValue());
		User user = userRe.getBody();
		
		Assert.assertNull("User Id must be null", user.getId());
		Assert.assertNull("User name must beNull", user.getLoginId());
	}

}
