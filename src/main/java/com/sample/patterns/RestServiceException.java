package com.sample.patterns;

public class RestServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	private Throwable cause;

	public RestServiceException() {
	}

	public RestServiceException(String message) {
		super(message);
		this.message = message;
	}

	public RestServiceException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

}
