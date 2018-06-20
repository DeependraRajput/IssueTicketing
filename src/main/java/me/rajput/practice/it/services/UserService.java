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
	 * Logs out the current user.
	 * @return
	 */
	void logout();
	
	/**
	 * Add a new user with the given attributes. (By Admin user only).
	 * @param newUserDto
	 * @return
	 */
	UserDto addUser(UserDto newUserDto);
	
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
	List<UserDto> searchUsers(UserDto user);
	
	/**
	 * Delete the user for given login id. (By Admin user only.)
	 * @param loginId
	 * @return
	 */
	boolean deleteUser(String loginId);
	
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
	UserDto currentUser();

}
