/**
 * 
 */
package me.rajput.practice.it.exceptions;

/**
 * Description: Exception to denote login attempt failure.
 * 
 * @author Deependra Rajput
 * @date Jun 17, 2018
 *
 */
public class LoginFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginFailedException() {
	}

	/**
	 * @param arg0
	 */
	public LoginFailedException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public LoginFailedException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public LoginFailedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public LoginFailedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
