package com.ivanmoreno.booking.common;

import lombok.Data;

@Data
public class BookingNotFoundException extends Exception{

	private String message;
	private Object[] args;
	
	public BookingNotFoundException(String name) {
		this.message = "Booking [" + name + "] is not found";
	}
	
	public BookingNotFoundException(Object[] args) {
		this.args = args;
	}
	
	public BookingNotFoundException(String message, Object[] args) {
		this.message = message;
		this.args = args;
	}
	
	private static final long serialVersionUID = 1L;
	
}
