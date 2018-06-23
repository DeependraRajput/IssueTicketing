package me.rajput.practice.it.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.rajput.practice.it.model.db.User;
import me.rajput.practice.it.services.UserService;

/**
 * Description: MVC Controller to handle all REST calls for users.
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
    /**
     * Method called to login to the system with credentials and initialise a session until log-out.
     * @param newUser
     * @return
     */
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public User login(@RequestParam String loginId, @RequestParam String password) {
		
		if(loginId.matches(".*\\W.*")) throw new IllegalArgumentException("Illegal characters found in the lognId");
		return service.login(loginId, password);
	}
	
    /**
     * Method called to login to the system with credentials.
     * @param newUser
     * @return
     */
	@RequestMapping("/logout")
	public void logout() {
		service.logout();
	}
	
    /**
     * Method called to add a new user to the system.
     * @param newUser
     * @return
     */
	@RequestMapping(path="/user", method=RequestMethod.POST)
	public User createUser(@Valid @RequestBody User newUser) {
		return service.addUser(newUser);
	}

	/**
	 * Method called to delete an existing user.
	 * @param loginId
	 * @return
	 */
	@RequestMapping(path="/user", method=RequestMethod.DELETE)
	public boolean deleteUser(@RequestParam String loginId) {
		return service.deleteUser(loginId);
	}
	
	/**
	 * Method called to search a list of user based on user values.
	 * @param user values
	 * @return
	 */
	@RequestMapping(path="/user/searchUsers", method=RequestMethod.GET)
	public List<User> searchUser(@ModelAttribute User values) {
		return service.searchUsers(values);
	}
	
}
