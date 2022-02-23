package com.ivanmoreno.booking.domain.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ivanmoreno.booking.common.DuplicateBookingException;
import com.ivanmoreno.booking.common.InvalidBookingException;
import com.ivanmoreno.booking.domain.model.entity.Booking;
import com.ivanmoreno.booking.domain.repository.BookingRepository;
import com.ivanmoreno.commons.domain.model.entity.Entity;
import com.ivanmoreno.commons.domain.service.BaseService;

@Service("bookingService")
public class BookingServiceImpl extends BaseService<Booking, String> implements BookingService{

	private BookingRepository<Booking, String> bookingRepo;
	
	public BookingServiceImpl(BookingRepository<Booking, String> repo) {
		super(repo);
		bookingRepo = repo;
	}

	@Override
	public void update(Booking booking) throws Exception {
		bookingRepo.update(booking);
	}

	@Override
	public void delete(String id) throws Exception {
		bookingRepo.remove(id);
	}

	@Override
	public Entity<String> findById(String id) throws Exception {
		return bookingRepo.get(id);
	}

	@Override
	public Collection<Booking> findByName(String name) throws Exception {
		return bookingRepo.findByName(name);
	}

	@Override
	public Collection<Booking> findByCriteria(Map<String, List<String>> name) throws Exception {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public void addBooking(Booking booking) throws Exception {
		
		if(booking.getName() == null || booking.getName().equals("")) {
			Object[] args = {"Booking with null or empty name"};
			throw new InvalidBookingException("invalidBooking", args);
		}
		
		if(bookingRepo.containsName(booking.getName())) {
			Object[] args = {booking.getName()};
			throw new DuplicateBookingException("duplicateBooking", args);
		}
		super.add(booking);
	}

}
