package me.rajput.practice.it.services;

import java.util.List;

import me.rajput.practice.it.domain.User;

/**
 * 
 * Description: This interface provides an user access feature to the application. 
 * 
 * @author Deependra Rajput
 * @date Jun 17, 2018
 *
 */
public interface UserService extends WebEntityService<User> {
	
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
	 * Provided the current user of the session.
	 * @return
	 */
	User currentUser();
	
}
