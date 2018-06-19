package me.rajput.practice.it.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PasswordManager {
	
	@Value("#{T(java.util.Arrays).asList('${password.regexes}'.split(':'))}")
	List<String> strengthCheckers;
	
	/**
	 * Check the strength of password to match with required patterns.
	 * @param password
	 * @return
	 */
	public boolean checkPasswordStrength(String password) {
		
		boolean isGood = password != null;
		for(String regex:  strengthCheckers) {
			if(!isGood) return false;
			isGood = password.matches(regex);
		}
		return isGood;
	}
	
	/**
	 * TODO: Complete
	 * Returns the encrypted password for the given password.
	 * @param password
	 * @return
	 */
	public String getEncryptedPassword(String password) {
		return password;
	}
	
	/**
	 * TODO: Complete
	 * Returns the decrypted password for the given encrypted password.
	 * @param password
	 * @return
	 */
	public String getDecryptedPassword(String encryptedPassword) {
		return encryptedPassword;
	}
}
