package me.rajput.practice.it.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import me.rajput.practice.it.model.db.User;

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
	 * FInd the user by it's loginId.
	 * @param loginId
	 * @return
	 */
	User findByLoginId(String loginId);

}
