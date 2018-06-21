package me.rajput.practice.it.repositories;

import org.springframework.data.repository.CrudRepository;

import me.rajput.practice.it.model.UserSecurity;

/**
 * 
 * Description: Repository to access users of the application in the database. 
 * 
 * @author Deependra Rajput
 * @date Jun 18, 2018
 *
 */
public interface UserSecurityRepository extends CrudRepository<UserSecurity, Long> {

}
