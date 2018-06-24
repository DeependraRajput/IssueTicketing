package me.rajput.practice.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import me.rajput.practice.it.exceptions.ApplicationException;
import me.rajput.practice.it.exceptions.ApplicationException.ErrorType;
import me.rajput.practice.it.model.db.User;
import me.rajput.practice.it.services.UserService;

/**
 * Description: MVC Controller to handle all REST calls for users.
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@Slf4j
@RestController
public class UserController {
	
	private static final String CONTEXT_PATH = "/rajput";
	
	private static final String URI_MAPPING = "/user";
	
	@Autowired
	private UserService userService;
	
    /**
     * Method called to login to the system with credentials and initialise a session until log-out.
     * @param newUser
     * @return
     */
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public User login(@RequestParam String loginId, @RequestParam String password) {
		
		if(loginId.matches(".*\\W.*")) throw new IllegalArgumentException("Illegal characters found in the lognId");
		return userService.login(loginId, password);
	}
	
    /**
     * Method called to login to the system with credentials.
     * @param newUser
     * @return
     */
	@RequestMapping("/logout")
	public void logout() {
		userService.logout();
	}
	
	/**
	 * Retrieves a user based on it's id.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		LOGGER.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			LOGGER.error("User with id {} not found.", id);
			return new ResponseEntity<>(new ApplicationException(ErrorType.READ_FAILURE,
					"User with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Create a new user.
	 * @param user
	 * @param ucBuilder
	 * @return the URI location of new user.
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		LOGGER.info("Creating User : {}", user);

		ResponseEntity<?> response = null;
		try {
			user.setId(null);
			Long id = userService.saveUser(user);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path(CONTEXT_PATH + URI_MAPPING + "/{id}").buildAndExpand(id).toUri());
			response = new ResponseEntity<String>(headers, HttpStatus.CREATED);
		} catch(Exception e) {
			response = new ResponseEntity<>(new ApplicationException(ErrorType.WRITE_FAILURE, 
					"Unable to create. A User with name [" + user.getLoginId() + "] already exist."),
					HttpStatus.CONFLICT);
		}

		return response;
	}

	/**
	 * Updates an existing user.
	 * @param id
	 * @param user
	 * @return new values of updated user.
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		LOGGER.info("Updating User with id {}", id);

		ResponseEntity<?> response = null;
		try {
			user.setId(id);
			userService.saveUser(user);
			response = new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Unable to update. User with id {} not found.", id);
			response = new ResponseEntity<>(new ApplicationException(ErrorType.WRITE_FAILURE,
					"Unable to upate. User with id [" + id + "] not found."),
					HttpStatus.NOT_FOUND);
		}

		return response;
	}

	/**
	 * Deletes an existing user.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		LOGGER.info("Fetching & Deleting User with id {}", id);

		User user = userService.findById(id);
		if (user == null) {
			LOGGER.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity<>(new ApplicationException(ErrorType.WRITE_FAILURE,
					"Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUser(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
}
