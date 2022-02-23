package com.ivanmoreno.booking.domain.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ivanmoreno.commons.domain.model.entity.BaseEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Booking extends BaseEntity<String> {

	private String restaurantId;
	private String userId;
	private LocalDate date;
	private LocalTime time;
	private String tableId;

	public Booking(String id, String name, String restaurantId, String tableId, String userId,
			LocalDate date, LocalTime time) {
		super(id, name);
		this.restaurantId = restaurantId;
		this.tableId = tableId;
		this.userId = userId;
		this.date = date;
		this.time = time;
	}

	public Booking(String id, String name) {
		super(id, name);
	}
	
	public static Booking getDummyBooking() {
		return new Booking(null, null);
	}
}
