/**
 * 
 */
package me.rajput.practice.it.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import me.rajput.practice.it.domain.User;
import me.rajput.practice.it.domain.UserType;
import me.rajput.practice.it.exceptions.LoginFailedException;
import me.rajput.practice.it.repositories.UserRepository;

/**
 * Description: Service to provide all features for user accessibility and operations. 
 * 
 * @author Deependra Rajput
 * @date Jun 17, 2018
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordManager passwordManager;
	
	@Autowired
	@Qualifier("currentUser")
	private User currentUser;

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String loginId, String password) {
		
		Assert.notNull(loginId, "Login Id must not be null!");
		Assert.notNull(password, "Password must not be null!");
		
		User user = userRepo.findUserByLoginId(loginId);
		if(user != null) {
			String userPassword = user.getPassword();
			if(StringUtils.isEmpty(userPassword) || passwordManager.getEncryptedPassword(password).equals(userPassword)) {
				//Copy the user values to session scoped current user.
				modelMapper.map(user, this.currentUser);
				LOGGER.info("{} has successfully logged into the system", user);
				
				return user;
			}
		}
		
		throw new LoginFailedException("Invalid Credentials!");
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#logout()
	 */
	@Override
	public void logout() {
		
		User currentUser = this.currentUser();
		String userName = currentUser.toString();
		
		modelMapper.map(new User(), currentUser);
		currentUser.setType(UserType.INVALID);
		
		LOGGER.info("{} has successfully logged out of the system", userName);
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#getById(java.lang.Long)
	 */
	@Override
	public User getById(Long id) {
		Optional<User> userOp = userRepo.findById(id);
		
		if(!userOp.isPresent()) {
			throw new IllegalArgumentException("Resource not available");
		}
		
		return userOp.get();
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#create(me.rajput.practice.it.model.User)
	 */
	@Override
	public Long create(User user) {
		
		Assert.isTrue(isCurrentUserAdmin(), "Current user must be an Admin");
		
		Assert.notNull(user, "Details must not be null!");
		Assert.notNull(user.getLoginId(), "Login Id must not be null!");
		Assert.notNull(user.getFirstName(), "First name must not be null!");
		Assert.notNull(user.getLastName(), "Last name must not be null!");
		user.setId(null);
		user = userRepo.save(user);

		LOGGER.info("{} has successfully been added to the system", user);
		
		return user.getId();
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#save(me.rajput.practice.it.model.User)
	 */
	@Override
	public User update(User user) {
		
		Assert.isTrue(isCurrentUserAdmin(), "Current user must be an Admin");
		
		Assert.notNull(user, "Details must not be null!");
		Assert.notNull(user.getLoginId(), "Login Id must not be null!");
		Assert.notNull(user.getFirstName(), "First name must not be null!");
		Assert.notNull(user.getLastName(), "Last name must not be null!");
		
		//Protect overwriting JsonIgnored fields with blank only for update. TODO: User ModelMapper properties.
		Optional<User> existingValueOp = userRepo.findById(user.getId());
		if(existingValueOp.isPresent()) {
			User existingValue = existingValueOp.get();
			existingValue.setEmail(user.getEmail());
			existingValue.setFirstName(user.getFirstName());
			existingValue.setMiddleName(user.getMiddleName());
			existingValue.setLastName(user.getLastName());
			existingValue.setType(user.getType());
			user = userRepo.save(existingValue);
		} else {
			throw new RuntimeException("User not found");
		}

		LOGGER.info("{} has successfully been updated in the system", user);
		
		return user;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#resetPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean resetPassword(String oldPassword, String newPassword) {
		
		boolean isSuccess = false;
		User actor = this.currentUser();
		if(actor != null && actor.getId() != null) {
			String password = actor.getPassword();
			if(password == null || password.isEmpty() || password.equals(passwordManager.getEncryptedPassword(oldPassword))) {
				if(passwordManager.checkPasswordStrength(newPassword)) {
					actor.setPassword(passwordManager.getEncryptedPassword(newPassword));
					actor = userRepo.save(actor);
					modelMapper.map(actor, this.currentUser());
					isSuccess = true;
				}
			}
		}

		LOGGER.info("Password change for {} has {}", actor, (isSuccess?"been successful":"failed"));
		
		return isSuccess;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#searchUsers(me.rajput.practice.it.model.User)
	 */
	@Override
	public List<User> searchUsers(User user) {
		
		List<User> allUsers = Collections.<User>emptyList();
		if(!StringUtils.isEmpty(user.getLoginId())) {
			User u = userRepo.findUserByLoginId(user.getLoginId());
			if(u != null) { 
				allUsers = Arrays.asList(u);
			}
		} else if(!StringUtils.isEmpty(user.getEmail())) {
			User u = userRepo.findUserByEmail(user.getEmail());
			if(u != null) { 
				allUsers = Arrays.asList(u);
			}
		} else if(!StringUtils.isEmpty(user.getFirstName())) {
			if(!StringUtils.isEmpty(user.getLastName())) {
				allUsers = userRepo.findUserByFirstNameAndLastName(user.getFirstName(), user.getLastName());
			} else {
				allUsers = userRepo.findUserByFirstName(user.getFirstName());
			}
		} else {
			if(!StringUtils.isEmpty(user.getLastName())) {
				allUsers = userRepo.findUserByLastName(user.getLastName());
			}
		}
		
		//TODO: How to replace above construct
		//Example<User> example = null;
		//List<User> allUsers = userRepo.findAll(example);
		
		return allUsers == null? null: allUsers.stream()
				.map(u->modelMapper.map(u, User.class))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#deleteById(java.lang.Long)
	 */
	@Override
	public void deleteById(Long id) {
		
		Assert.isTrue(isCurrentUserAdmin(), "Current user must be an Admin");
		userRepo.deleteById(id);
		LOGGER.error("User with Login Id [{}] has been deleted successfully.", id);
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#currentUser()
	 */
	@Override
	public User currentUser() {
		return UserType.INVALID.equals(this.currentUser.getType())? null: this.currentUser;
	}
	
	/**
	 * Checks is the user is Admin or not.
	 * @param User
	 * @return
	 */
	private boolean isCurrentUserAdmin() {
		return currentUser() != null && UserType.ADMIN.equals(currentUser().getType());
	}

}
