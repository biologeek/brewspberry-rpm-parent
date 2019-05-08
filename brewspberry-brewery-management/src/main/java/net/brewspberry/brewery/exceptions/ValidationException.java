package net.brewspberry.brewery.exceptions;

/**
 * Exception thrown when a functional error occurs on brewery management
 *
 */
public class ValidationException extends Exception {

	public ValidationException(String message) {
		super(message);
	}
}
