package me.rajput.practice.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import me.rajput.practice.it.domain.User;
import me.rajput.practice.it.services.WebEntityService;
import me.rajput.practice.it.services.UserService;

/**
 * Description: MVC Controller to handle all REST calls for users.
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@RestController
public class UserController extends BaseController<User> {
	
	
	/** URI mapping for this controller. */
	private static final String URI_MAPPING = "/user";

	@Autowired
	private UserService userService;
	
	/**
	 * Default Constructor.
	 */
	public UserController() {
		super(URI_MAPPING, "User");
	}
	
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
	@RequestMapping(value = URI_MAPPING + ID_PARAM, method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") Long id, Pageable pageable) {
		return super.getEntity(id, pageable);
	}

	/**
	 * Create a new user.
	 * @param user
	 * @param ucBuilder
	 * @return the URI location of new user.
	 */
	@RequestMapping(value = URI_MAPPING, method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		return super.createEntity(user, ucBuilder);
	}

	/**
	 * Updates an existing user.
	 * @param id
	 * @param user
	 * @return new values of updated user.
	 */
	@RequestMapping(value = URI_MAPPING + ID_PARAM, method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		return super.updateEntity(id, user);
	}

	/**
	 * Deletes an existing user.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = URI_MAPPING + ID_PARAM, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		return super.deleteEntity(id);
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.controller.UserController#getEntityService()
	 */
	@Override
	protected WebEntityService<User> getEntityService() {
		return this.userService;
	}
	
}
