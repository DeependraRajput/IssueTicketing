package me.rajput.practice.it.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.StdDateFormat;

import me.rajput.practice.it.model.dto.UserDto;
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
	 * Initialise the data conversion for Date to String with given format.
	 * @param binder
	 */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new StdDateFormat(), false));
    }
	
    /**
     * Method called to login to the system with credentials and initialise a session until log-out.
     * @param newUser
     * @return
     */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public UserDto login(@RequestParam String loginId, @RequestParam String password) {
		
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
	@RequestMapping("/addUser")
	public UserDto createUser(@Valid @ModelAttribute UserDto newUser) {
		return service.addUser(newUser);
	}

	/**
	 * Method called to delete an existing user.
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/deleteUser")
	public boolean deleteUser(@RequestParam String loginId) {
		return service.deleteUser(loginId);
	}
	
	/**
	 * Method called to search a list of user based on user values.
	 * @param user values
	 * @return
	 */
	@RequestMapping("/searchUser")
	public List<UserDto> searchUser(@ModelAttribute UserDto values) {
		return service.searchUsers(values);
	}
	
}
