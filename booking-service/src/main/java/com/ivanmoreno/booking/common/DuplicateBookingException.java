package com.ivanmoreno.booking.common;

import lombok.Data;

@Data
public class DuplicateBookingException extends Exception {

	private String message;
	private Object[] args;

	public DuplicateBookingException(String name) {
		this.message = String.format("There is already a booking with the name - %s", name);
	}

	public DuplicateBookingException(Object[] args) {
		this.args = args;
	}

	public DuplicateBookingException(String message, Object[] args) {
		this.message = message;
		this.args = args;
	}

	private static final long serialVersionUID = 1L;

}
