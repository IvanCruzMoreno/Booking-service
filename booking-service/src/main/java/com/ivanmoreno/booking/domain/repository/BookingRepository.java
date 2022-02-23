package com.ivanmoreno.booking.domain.repository;

import java.util.Collection;

import com.ivanmoreno.commons.domain.repository.Repository;

public interface BookingRepository<E, ID> extends Repository<E, ID> {

	boolean containsName(String name) throws Exception;

	public Collection<E> findByName(String name) throws Exception;
}
