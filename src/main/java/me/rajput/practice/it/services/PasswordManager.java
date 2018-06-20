package me.rajput.practice.it.services;

public interface PasswordManager {
	
	/**
	 * Check the strength of password to match with required patterns.
	 * @param password
	 * @return
	 */
	boolean checkPasswordStrength(String password);
	
	/**
	 * Returns the encrypted password for the given password.
	 * @param password
	 * @return
	 */
	String getEncryptedPassword(String password);

}
