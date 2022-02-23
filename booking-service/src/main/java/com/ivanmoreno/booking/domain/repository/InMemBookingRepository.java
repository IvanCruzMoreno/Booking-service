package com.ivanmoreno.booking.domain.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ivanmoreno.booking.common.BookingNotFoundException;
import com.ivanmoreno.booking.domain.model.entity.Booking;

@Repository("bookingRepository")
public class InMemBookingRepository implements BookingRepository<Booking, String>{

	private static final Map<String, Booking> entities;
	
	static {
		entities =  new ConcurrentHashMap<>(Map.ofEntries(
				Map.entry("1", new Booking("1", "Booking 1", "1", "1", "1", LocalDate.now(), LocalTime.now()) ),
				Map.entry("2", new Booking("2", "Booking 2", "2", "2", "2", LocalDate.now(), LocalTime.now()) )
				));
	}
	
	@Override
	public void add(Booking entity) {
		entities.put(entity.getId(), entity);
	}

	@Override
	public void remove(String id) {
		entities.computeIfPresent(id, (k,v) -> entities.remove(k));
	}

	@Override
	public void update(Booking entity) {
		entities.computeIfPresent(entity.getId(), (k,v) -> entities.put(k, entity));
	}

	@Override
	public boolean contains(String id) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public Booking get(String id) {
		return entities.get(id);
	}

	@Override
	public Collection<Booking> getAll() {
		return entities.values();
	}

	@Override
	public boolean containsName(String name) throws Exception {
		try {
			return !this.findByName(name).isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Collection<Booking> findByName(String name) throws Exception {
		
		List<Booking> bookings = entities.entrySet().stream()
				.filter(entry -> entry.getValue().getName().toLowerCase().contains(name))
				.map(entry -> entry.getValue())
				.collect(Collectors.toList());
		
		if(bookings != null && bookings.isEmpty()) {
			Object[] args = {name};
			throw new BookingNotFoundException("bookingNotFound", args);
		}
		
		return bookings;
	}

	
}
