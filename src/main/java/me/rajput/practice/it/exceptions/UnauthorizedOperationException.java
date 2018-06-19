/**
 * 
 */
package me.rajput.practice.it.exceptions;

/**
 * Description: Exception to denote an unauthorised operation attempt.
 * 
 * @author Deependra Rajput
 * @date Jun 17, 2018
 *
 */
public class UnauthorizedOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7544937850781025990L;

	/**
	 * 
	 */
	public UnauthorizedOperationException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public UnauthorizedOperationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public UnauthorizedOperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public UnauthorizedOperationException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public UnauthorizedOperationException(Throwable arg0) {
		super(arg0);
	}

}
