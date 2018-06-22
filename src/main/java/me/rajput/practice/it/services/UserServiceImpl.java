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
import me.rajput.practice.it.exceptions.LoginFailedException;
import me.rajput.practice.it.model.UserType;
import me.rajput.practice.it.model.db.User;
import me.rajput.practice.it.model.db.UserSecurity;
import me.rajput.practice.it.model.dto.UserDto;
import me.rajput.practice.it.repositories.UserRepository;
import me.rajput.practice.it.repositories.UserSecurityRepository;

/**
 * Description: Service to provide all features for user accessibility and operations. 
 * 
 * @author Deependra Rajput
 * @date Jun 17, 2018
 *
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserSecurityRepository secRepo;
	
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
		
		User user = userRepo.findByLoginId(loginId);
		if(user != null) {
			Optional<UserSecurity> userSec = secRepo.findById(user.getId());
			if(!userSec.isPresent() || passwordManager.getEncryptedPassword(password).equals(userSec.get().getPassword())) {
				modelMapper.map(user, this.currentUser);
				LOGGER.info(user.getFirstName() + " " + user.getLastName() + "["+ user.getLoginId()+"] has successfully logged into the system");
				return this.currentUser.clone();
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
		User user = currentUser.clone();
		
		modelMapper.map(new User(), currentUser);
		currentUser.setType(UserType.INVALID);
		
		LOGGER.info(user.getFirstName() + " " + user.getLastName() + "["+ user.getLoginId()+"] has successfully logged out of the system");
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#addUser(me.rajput.practice.it.model.User)
	 */
	@Override
	public User addUser(User newUser) {
		
		Assert.isTrue(isCurrentUserAdmin(), "Current user must be an Admin");
		
		Assert.notNull(newUser, "Details must not be null!");
		Assert.notNull(newUser.getLoginId(), "Login Id must not be null!");
		Assert.notNull(newUser.getFirstName(), "First name must not be null!");
		Assert.notNull(newUser.getLastName(), "Last name must not be null!");

		if(newUser.getType() == null)
			newUser.setType(UserType.USER);
		
		userRepo.save(newUser);
		
		LOGGER.info(newUser.getFirstName() + " " + newUser.getLastName() + "["+ newUser.getLoginId()+"] has successfully been added to the system");
		
		return newUser;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#resetPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean resetPassword(String oldPassword, String newPassword) {
		
		boolean isSuccess = false;
		User actor = this.currentUser();
		if(actor != null) {
			User user = userRepo.findByLoginId(actor.getLoginId());
			if(user != null) {
				Optional<UserSecurity> userSec = secRepo.findById(user.getId());
				if(!userSec.isPresent() || 
						userSec.get().getPassword().equals(passwordManager.getEncryptedPassword(oldPassword))) {
					if(passwordManager.checkPasswordStrength(newPassword)) {
						UserSecurity us = userSec.get();
						us.setPassword(passwordManager.getEncryptedPassword(newPassword));
						secRepo.save(us);
						isSuccess = true;
					}
				}
			}
		}

		LOGGER.info("Password change for {" + actor.getFirstName() + " " + actor.getLastName() + "["+ actor.getLoginId()+"]} has " + (isSuccess?"been successful":"failed"));
		
		return isSuccess;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#searchUsers(me.rajput.practice.it.model.User)
	 */
	@Override
	public List<User> searchUsers(User user) {
		
		List<User> allUsers = Collections.<User>emptyList();
		if(!StringUtils.isEmpty(user.getLoginId())) {
			User u = userRepo.findByLoginId(user.getLoginId());
			if(u != null) { 
				allUsers = Arrays.asList(u);
			}
		} else if(!StringUtils.isEmpty(user.getEmail())) {
			User u = userRepo.findByEmail(user.getEmail());
			if(u != null) { 
				allUsers = Arrays.asList(u);
			}
		} else if(!StringUtils.isEmpty(user.getFirstName())) {
			if(!StringUtils.isEmpty(user.getLastName())) {
				allUsers = userRepo.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());
			} else {
				allUsers = userRepo.findByFirstName(user.getFirstName());
			}
		} else {
			if(!StringUtils.isEmpty(user.getLastName())) {
				allUsers = userRepo.findByLastName(user.getLastName());
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
	 * @see me.rajput.practice.it.services.UserService#deleteUser(java.lang.String)
	 */
	@Override
	public boolean deleteUser(String loginId) {
		
		Assert.isTrue(isCurrentUserAdmin(), "Current user must be an Admin");
		
		boolean isSuccess = false;
		try {
			User user = userRepo.findByLoginId(loginId);
			if(user != null && user.getId() != null) {
				userRepo.findByLoginId(loginId);
				isSuccess = true;
				LOGGER.error("User with Login Id ["+loginId+"] has been deleted successfully.");
			}
		} catch(Exception e) {
			LOGGER.error("Unable to delete the user with Login Id ["+loginId+"]");
		}
		
		return isSuccess;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#updateUserType(java.lang.String, me.rajput.practice.it.model.UserType)
	 */
	@Override
	public boolean updateUserType(String loginId, UserType type) {
		
		Assert.isTrue(isCurrentUserAdmin(), "Current user must be an Admin");
		
		boolean isSuccess = false;
		try {
			User user = userRepo.findByLoginId(loginId);
			if(user != null && user.getId() != null) {
				user.setType(type);
				userRepo.save(user);
				isSuccess = true;
			}
		} catch(Exception e) {
			LOGGER.error("Unable to update the type for the user with Login Id ["+loginId+"]");
		}
		
		return isSuccess;
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#currentUser()
	 */
	@Override
	public User currentUser() {
		return UserType.INVALID.equals(this.currentUser.getType())? null: this.currentUser;
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#getUserDtoById(java.lang.Long)
	 */
	@Override
	public UserDto getUserDtoById(Long id) {
		
		if(id == null) return null;
		
		Optional<User> userOp = userRepo.findById(id);
		if(userOp.isPresent()) {
			return modelMapper.map(userOp.get(), UserDto.class);
		}
		
		return null;
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
