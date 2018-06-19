package me.rajput.practice.it.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.StdDateFormat;

import me.rajput.practice.it.model.UserType;
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
	 * Initialize the data conversion for Date to String with given format.
	 * @param binder
	 */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new StdDateFormat(), false));
    }
	
    /**
     * Method called to add a new user to the system.
     * TODO: Search how to check all the input parameters before passing on to the service. like AOP.
     * @param newUser
     * @return
     */
	@RequestMapping("/addUser")
	public UserDto createUser(@ModelAttribute UserDto newUser) {
		
		if(newUser.getLoginId().matches(".*\\W.*")) throw new IllegalArgumentException("Illegal characters found in the lognId");
		return service.addUser(getCurrentUser(), newUser);
	}

	/**
	 * Method called to delete an existing user.
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/deleteUser")
	public boolean deleteUser(@RequestParam String loginId) {
		return service.deleteUser(getCurrentUser(), loginId);
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
	
	/**
	 * Gets the current user of the request.
	 * TODO: Learn authentication with spring boot.
	 * @return
	 */
	private UserDto getCurrentUser() {
		UserDto user = new UserDto();
		user.setType(UserType.ADMIN);
		
		return user;
	}

}
