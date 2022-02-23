package com.ivanmoreno.booking.common;

import lombok.Data;

@Data
public class InvalidBookingException extends Exception {

	private String message;
	private Object[] args;

	public InvalidBookingException(String arg) {
		this.message = String.format("%s is an invalid booking.", arg);
	}

	public InvalidBookingException(Object[] args) {
		this.args = args;
	}

	public InvalidBookingException(String message, Object[] args) {
		this.message = message;
		this.args = args;
	}
	
	private static final long serialVersionUID = 1L;
}
