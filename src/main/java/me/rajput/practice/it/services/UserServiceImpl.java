/**
 * 
 */
package me.rajput.practice.it.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	
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
	private UserDto currentUser;

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public UserDto login(String loginId, String password) {
		
		Assert.notNull(loginId, "Login Id must not be null!");
		Assert.notNull(password, "Password must not be null!");
		
		User user = userRepo.findByLoginId(loginId);
		if(user != null) {
			UserSecurity userSec = secRepo.findOne(user.getId());
			if(userSec == null || passwordManager.getEncryptedPassword(password).equals(userSec.getPassword())) {
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
		
		UserDto currentUser = this.currentUser();
		UserDto user = currentUser.clone();
		
		modelMapper.map(new UserDto(), currentUser);
		currentUser.setType(UserType.INVALID);
		
		LOGGER.info(user.getFirstName() + " " + user.getLastName() + "["+ user.getLoginId()+"] has successfully logged out of the system");
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#addUser(me.rajput.practice.it.model.dto.UserDto)
	 */
	@Override
	public UserDto addUser(UserDto newUserDto) {
		
		Assert.isTrue(isCurrentUserAdmin(), "Current user must be an Admin");
		
		Assert.notNull(newUserDto, "Details must not be null!");
		Assert.notNull(newUserDto.getLoginId(), "Login Id must not be null!");
		Assert.notNull(newUserDto.getFirstName(), "First name must not be null!");
		Assert.notNull(newUserDto.getLastName(), "Last name must not be null!");

		User newUser = modelMapper.map(newUserDto, User.class);
		if(newUser.getType() == null)
			newUser.setType(UserType.USER);
		
		Date currentDate = new Date();
		newUser.setUpdatedAt(currentDate);
		
		userRepo.save(newUser);
		
		LOGGER.info(newUser.getFirstName() + " " + newUser.getLastName() + "["+ newUser.getLoginId()+"] has successfully been added to the system");
		
		return newUserDto;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#resetPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean resetPassword(String oldPassword, String newPassword) {
		
		boolean isSuccess = false;
		UserDto actor = this.currentUser();
		if(actor != null) {
			User user = userRepo.findByLoginId(actor.getLoginId());
			if(user != null) {
				UserSecurity userSec = secRepo.findOne(user.getId());
				if(userSec == null || 
						userSec.getPassword().equals(passwordManager.getEncryptedPassword(oldPassword))) {
					if(passwordManager.checkPasswordStrength(newPassword)) {
						userSec.setPassword(passwordManager.getEncryptedPassword(newPassword));
						secRepo.save(userSec);
						isSuccess = true;
					}
				}
			}
		}

		LOGGER.info("Password change for {" + actor.getFirstName() + " " + actor.getLastName() + "["+ actor.getLoginId()+"]} has " + (isSuccess?"been successful":"failed"));
		
		return isSuccess;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#searchUsers(me.rajput.practice.it.model.dto.UserDto)
	 */
	@Override
	public List<UserDto> searchUsers(UserDto user) {
		Example<User> example = null;
		// TODO : What should be the criteria.
		List<User> allUsers = userRepo.findAll(example);
		
		return allUsers == null? null: allUsers.stream()
				.map(u->modelMapper.map(u, UserDto.class))
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
				user.setUpdatedAt(new Date());
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
	public UserDto currentUser() {
		return UserType.INVALID.equals(this.currentUser.getType())? null: this.currentUser;
	}
	
	/**
	 * Checks is the user is Admin or not.
	 * @param userDto
	 * @return
	 */
	private boolean isCurrentUserAdmin() {
		return currentUser() != null && UserType.ADMIN.equals(currentUser().getType());
	}



}
