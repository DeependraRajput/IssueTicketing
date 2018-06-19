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
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import me.rajput.practice.it.exceptions.LoginFailedException;
import me.rajput.practice.it.model.UserType;
import me.rajput.practice.it.model.db.User;
import me.rajput.practice.it.model.dto.UserDto;
import me.rajput.practice.it.repositories.UserRepository;

/**
 * Description: 
 * 
 * @author Deependra Rajput
 * @date Jun 17, 2018
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordManager passwordManager;

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public UserDto login(String loginId, String password) {
		
		Assert.notNull(loginId, "Login Id must not be null!");
		Assert.notNull(password, "Password must not be null!");
		
		//Question: Should the password be fetched in to the JVM like this? 
		User user = repository.findByLoginId(loginId);
		if(user != null && passwordManager.getEncryptedPassword(password).equals(user.getPassword())) {
			return modelMapper.map(user, UserDto.class);
		}
		
		throw new LoginFailedException("Invalid Credentials!");
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#addUser(me.rajput.practice.it.model.dto.UserDto)
	 */
	@Override
	public UserDto addUser(UserDto actor, UserDto newUserDto) {
		
		Assert.isTrue(isCurrentUserAdmin(actor), "Current user must be an Admin");
		
		Assert.notNull(newUserDto, "Details must not be null!");
		Assert.notNull(newUserDto.getLoginId(), "Login Id must not be null!");
		Assert.notNull(newUserDto.getFirstName(), "First name must not be null!");
		Assert.notNull(newUserDto.getLastName(), "Last name must not be null!");

		User newUser = modelMapper.map(newUserDto, User.class);
		if(newUser.getType() == null)
			newUser.setType(UserType.USER);
		
		Date currentDate = new Date();
		newUser.setCreatedAt(currentDate);
		newUser.setUpdatedAt(currentDate);
		
		repository.save(newUser);
		
		return newUserDto;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#resetPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean resetPassword(UserDto actor, String oldPassword, String newPassword) {
		
		boolean isSuccess = false;
		if(actor != null) {
			User user = repository.findByLoginId(actor.getLoginId());
			if(StringUtils.isEmpty(user.getPassword()) || user.getPassword().equals(passwordManager.getEncryptedPassword(oldPassword))) {
				if(passwordManager.checkPasswordStrength(newPassword)) {
					user.setPassword(passwordManager.getEncryptedPassword(newPassword));
					repository.save(user);
					isSuccess = true;
				}
			}
		}
		return isSuccess;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#searchUsers(me.rajput.practice.it.model.dto.UserDto)
	 */
	@Override
	public List<UserDto> searchUsers(UserDto user) {
		Example<User> example = null;
		// TODO : What should be the criteria.
		List<User> allUsers = repository.findAll(example);
		
		return allUsers == null? null: allUsers.stream()
				.map(u->modelMapper.map(u, UserDto.class))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#deleteUser(me.rajput.practice.it.model.dto.UserDto, java.lang.String)
	 */
	@Override
	public boolean deleteUser(UserDto actor, String loginId) {
		
		Assert.isTrue(isCurrentUserAdmin(actor), "Current user must be an Admin");
		
		boolean isSuccess = false;
		try {
			User user = repository.findByLoginId(loginId);
			if(user != null && user.getId() != null) {
				repository.findByLoginId(loginId);
				isSuccess = true;
			}
		} catch(Exception e) {
			LOGGER.error("Unable to delete the user with Login Id ["+loginId+"]");
		}
		
		return isSuccess;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.UserService#updateUserType(me.rajput.practice.it.model.dto.UserDto, java.lang.String, me.rajput.practice.it.model.UserType)
	 */
	@Override
	public boolean updateUserType(UserDto actor, String loginId, UserType type) {
		
		Assert.isTrue(isCurrentUserAdmin(actor), "Current user must be an Admin");
		
		boolean isSuccess = false;
		try {
			User user = repository.findByLoginId(loginId);
			if(user != null && user.getId() != null) {
				user.setType(type);
				user.setUpdatedAt(new Date());
				repository.save(user);
				isSuccess = true;
			}
		} catch(Exception e) {
			LOGGER.error("Unable to update the type for the user with Login Id ["+loginId+"]");
		}
		
		return isSuccess;
	}
	
	/**
	 * Checks is the user is Admin or not.
	 * @param userDto
	 * @return
	 */
	private boolean isCurrentUserAdmin(UserDto userDto) {
		return userDto != null && UserType.ADMIN.equals(userDto.getType());
	}


}
