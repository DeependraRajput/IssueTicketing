/**
 * 
 */
package me.rajput.practice.it.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Description: Exception Class for application specific error codes to the client. 
 * 
 * @author Deependra Rajput
 * @date Jun 24, 2018
 *
 */

@Getter
@AllArgsConstructor
@ToString
public class ApplicationException {
	
	private ErrorType errorType;
	private String customMessage;
	
	@Getter
	@AllArgsConstructor
	@ToString
	public static enum ErrorType {
		
		READ_FAILURE(1, "Failed to read"),
		WRITE_FAILURE(2, "Failed to write");
		
		private int exceptionCode;
		private String message;
		
	}
	
}
