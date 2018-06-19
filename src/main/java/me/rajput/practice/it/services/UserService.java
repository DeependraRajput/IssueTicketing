package me.rajput.practice.it.services;

import java.util.List;

import me.rajput.practice.it.model.UserType;
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
	UserDto login(String loginId, String password);
	
	/**
	 * Add a new user with the given attributes. (By Admin user only).
	 * @param actor
	 * @param newUserDto
	 * @return
	 */
	UserDto addUser(UserDto actor, UserDto newUserDto);
	
	/**
	 * Reset the password of the currently logged in user.
	 * @param actor
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	boolean resetPassword(UserDto actor, String oldPassword, String newPassword);
	
	/**
	 * Find all users based on the given criteria.	
	 * @param params
	 * @return
	 */
	List<UserDto> searchUsers(UserDto user);
	
	/**
	 * Delete the user for given login id. (By Admin user only.)
	 * @param actor
	 * @param loginId
	 * @return
	 */
	boolean deleteUser(UserDto actor, String loginId);
	
	/**
	 * User user type of the user for given login id. (By Admin user only.)
	 * @param actor
	 * @param loginId
	 * @param type
	 * @return
	 */
	boolean updateUserType(UserDto actor, String loginId, UserType type);

}
