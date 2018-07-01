package me.rajput.practice.it.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.rajput.practice.it.domain.User;

/**
 * 
 * Description: Repository to access users of the application in the database. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * Find the user by it's loginId.
	 * @param loginId
	 * @return
	 */
	User findUserByLoginId(String loginId);
	
	/**
	 * Find the user by it's email.
	 * @param email
	 * @return
	 */
	User findUserByEmail(String email);
	
	/**
	 * Find the user by it's first name.
	 * @param firstName
	 * @return
	 */
	List<User> findUserByFirstName(String firstName);
	
	/**
	 * Find the user by it's last name.
	 * @param lastName
	 * @return
	 */
	List<User> findUserByLastName(String lastName);
	
	/**
	 * Find the user by it's first name and last name.
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	List<User> findUserByFirstNameAndLastName(String firstName, String lastName);
	
	/**
	 * Find the user by it's first name, middle name and last name.
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @return
	 */
	List<User> findUserByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);

}
