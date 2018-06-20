package me.rajput.practice.it.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordManagerImpl implements PasswordManager {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Value("#{T(java.util.Arrays).asList('${password.regexes}'.split(':'))}")
	List<String> strengthCheckers;
	
	@Override
	public boolean checkPasswordStrength(String password) {
		
		boolean isGood = password != null;
		for(String regex:  strengthCheckers) {
			if(!isGood) return false;
			isGood = password.matches(regex);
		}
		return isGood;
	}
	
	@Override
	public String getEncryptedPassword(String password) {
		return passwordEncoder.encode(password);
	}
	
}
