package com.ivanmoreno.booking.controller;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivanmoreno.booking.common.BookingNotFoundException;
import com.ivanmoreno.booking.common.DuplicateBookingException;
import com.ivanmoreno.booking.common.InvalidBookingException;
import com.ivanmoreno.booking.domain.model.entity.Booking;
import com.ivanmoreno.booking.domain.service.BookingService;
import com.ivanmoreno.booking.domain.valueobject.BookingVO;
import com.ivanmoreno.commons.domain.model.entity.Entity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/booking")
public class BookingController {
	
	private BookingService bookingService;
	
	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
	@GetMapping
	public ResponseEntity<Collection<Booking>> findByName(@RequestParam String name) throws Exception {
		
		log.info(String.format("booking-service findByName() invoked: { %s } for { %s } ",
				bookingService.getClass().getName(), name));
		
		name = name.trim().toLowerCase();
		
		Collection<Booking> bookings;
		
		try {
			bookings = bookingService.findByName(name);
		} catch (BookingNotFoundException e) {
			log.error("Exception raised findByName REST Call", e);
			throw e;
		} catch (Exception ex) {
			log.error("Exception raised findByName REST Call", ex);
			throw ex;
		}
		
		return bookings == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
	}
	
	@GetMapping("/{booking_id}")
	public ResponseEntity<Entity<String>> findById(@PathVariable(name = "booking_id") String id) throws Exception {
		
		log.info(String.format("booking-service findById() invoked: { %s } for { %s } ",
				bookingService.getClass().getName(), id));
		
		id = id.trim();
		Entity<String> booking;
		
		try {
			booking = bookingService.findById(id);
		} catch (Exception e) {
			log.error("Exception raised findById REST Call", e);
			throw e;
		}
		
		return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Booking> add(@RequestBody BookingVO bookingVO) throws Exception {
		
		log.info(String.format("booking-service add() invoked:{ %s } for { %s } ",
				bookingService.getClass().getName(), bookingVO.getName()));
		
		Booking booking = Booking.getDummyBooking();
		BeanUtils.copyProperties(bookingVO, booking);
		
		try {
			bookingService.addBooking(booking);
		} catch (DuplicateBookingException | InvalidBookingException e) {
			log.error("Exception raised add REST Call", e);
			throw e;
		} catch(Exception e) {
			log.error("Exception raised add REST Call", e);
			throw e;
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(booking);
	}
	
}
