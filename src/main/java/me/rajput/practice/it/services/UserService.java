package me.rajput.practice.it.services;

import java.util.List;

import me.rajput.practice.it.model.UserType;
import me.rajput.practice.it.model.db.User;
import me.rajput.practice.it.model.dto.UserDto;

/**
 * 
 * Description: This interface provides an user access feature to the application. 
 * 
 * @author Deependra Rajput
 * @date Jun 17, 2018
 *
 */
public interface UserService {
	
	/**
	 * Logs in the user for provided loginId and password.
	 * @param loginId
	 * @param password
	 * @return
	 */
	User login(String loginId, String password);
	
	/**
	 * Logs out the current user.
	 * @return
	 */
	void logout();
	
	/**
	 * Find the user by id.
	 * @param id
	 * @return
	 */
	User findById(Long id);
	
	/**
	 * Saves a user with the given attributes. (By Admin user only).
	 * @param user
	 * @return
	 */
	Long saveUser(User user);
	
	/**
	 * Reset the password of the currently logged in user.
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	boolean resetPassword(String oldPassword, String newPassword);
	
	/**
	 * Find all users based on the given criteria.	
	 * @param params
	 * @return
	 */
	List<User> searchUsers(User user);
	
	/**
	 * Delete the user for given login id. (By Admin user only.)
	 * @param loginId
	 * @return
	 */
	boolean deleteUser(Long id);
	
	/**
	 * User user type of the user for given login id. (By Admin user only.)
	 * @param loginId
	 * @param type
	 * @return
	 */
	boolean updateUserType(String loginId, UserType type);
	
	/**
	 * Provided the current user of the session.
	 * @return
	 */
	User currentUser();
	
	/**
	 * Gets the User DTO by id.
	 * @param id
	 * @return
	 */
	UserDto getUserDtoById(Long id);
	
}
