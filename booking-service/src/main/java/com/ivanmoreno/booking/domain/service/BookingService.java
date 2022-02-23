package com.ivanmoreno.booking.domain.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ivanmoreno.booking.domain.model.entity.Booking;
import com.ivanmoreno.commons.domain.model.entity.Entity;

public interface BookingService {

	public void addBooking(Booking booking) throws Exception;
	
	public void update(Booking booking) throws Exception;

	public void delete(String id) throws Exception;
	
	public Entity<String> findById(String id) throws Exception;
	
	public Collection<Booking> findByName(String name) throws Exception;
	
	public Collection<Booking> findByCriteria(Map<String, List<String>> name) throws Exception;
}
