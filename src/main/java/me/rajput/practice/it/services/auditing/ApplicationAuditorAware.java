/**
 * 
 */
package me.rajput.practice.it.services.auditing;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.AuditorAware;

import me.rajput.practice.it.domain.User;

/**
 * Description: Application auditing information provider.
 * 
 * @author Deependra Rajput
 * @date Jun 23, 2018
 *
 */
public class ApplicationAuditorAware implements AuditorAware<User> {
	
	@Autowired
	@Qualifier("currentUser")
	private User currentUser;

	@Override
	public Optional<User> getCurrentAuditor() {
		return Optional.of(this.currentUser);
	}

}
