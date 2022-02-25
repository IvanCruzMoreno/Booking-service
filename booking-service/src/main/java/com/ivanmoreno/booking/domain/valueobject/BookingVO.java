package com.ivanmoreno.booking.domain.valueobject;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BookingVO {

	private String name;
	private String id;
	private String restaurantId;
	private String userId;
	private LocalDate date;
	private LocalDate time;
	private String tableId;
	
	
}
