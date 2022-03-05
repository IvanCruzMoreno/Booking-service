package com.ivanmoreno.booking.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookingControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	// Required to Generate JSON content from Java objects
	public static final ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	void testGetById() {

		Map<String, Object> response = 
				restTemplate.getForObject("http://localhost:" + port + "/v1/booking/1",
				Map.class);

		assertNotNull(response);

		// Asserting API Response
		String id = response.get("id").toString();
		assertNotNull(id);
		assertEquals("1", id);

		String name = response.get("name").toString();
		assertNotNull(name);
		assertEquals("Booking 1", name);

		Boolean isModified = (Boolean) response.get("isModified");
		assertNull(isModified);
	}

	@Test
	void testGetById_NoContent() {

		ResponseEntity<Map> responseE = 
				restTemplate.getForEntity("http://localhost:" + port + "/v1/booking/666",
				Map.class);

		assertNotNull(responseE);

		// Should return no content as there is no booking with id 666
		assertEquals(HttpStatus.NO_CONTENT, responseE.getStatusCode());
	}

	@Test
	void testGetByName() {

		Map<String, Object> requestParam = new HashMap<>();
		requestParam.put("name", "Booking");

		ResponseEntity<List> responseEntity = 
				restTemplate.exchange(
				"http://localhost:" + port + "/v1/booking?name={name}", HttpMethod.GET, null, List.class, requestParam);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Map<String, Object>> responses = responseEntity.getBody();
		assertNotNull(responses);

		// Assumed two instance exist for booking name contains "Booking"
		assertTrue(responses.size() == 2);

		Map<String, Object> response = responses.get(0);

		String id = response.get("id").toString();
		assertNotNull(id);
		assertEquals("1", id);

		String name = response.get("name").toString();
		assertNotNull(name);
		assertEquals("Booking 1", name);

		Boolean isModified = (Boolean) response.get("isModified");
		assertNull(isModified);
	}

	@Test
	void testAdd() throws JsonProcessingException {
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("name", "testBooking 3");
		requestBody.put("id", "3");
		requestBody.put("restaurantId", "1");
		requestBody.put("userId", "3");
		requestBody.put("tableId", "1");
		LocalDate nowDate = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		requestBody.put("date", nowDate);
		requestBody.put("time", nowTime);

		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    objectMapper.findAndRegisterModules();
	    
	    HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody),
	        headers);
		
		ResponseEntity<Map> responseE = 
				restTemplate.exchange("http://localhost:" + port + "/v1/booking", HttpMethod.POST, entity, Map.class);

		assertNotNull(responseE);

		// Should return created (status code 201)
		assertEquals(HttpStatus.CREATED, responseE.getStatusCode());

		// validating the newly created booking using API call
		Map<String, Object> response = 
				restTemplate.getForObject("http://localhost:" + port + "/v1/booking/3",
				Map.class);

		assertNotNull(response);

		// Asserting API Response
		String id = response.get("id").toString();
		assertNotNull(id);
		assertEquals("3", id);

		String name = response.get("name").toString();
		assertNotNull(name);
		assertEquals("testBooking 3", name);

		String restaurantId = response.get("restaurantId").toString();
		assertNotNull(restaurantId);
		assertEquals("1", restaurantId);

		String userId = response.get("userId").toString();
		assertNotNull(userId);
		assertEquals("3", userId);

		String tableId = response.get("tableId").toString();
		assertNotNull(tableId);
		assertEquals("1", tableId);
		
		String date = response.get("date").toString();
		assertNotNull(date);
		assertEquals(nowDate, LocalDate.parse(date));
		
		String time = response.get("time").toString();
		assertNotNull(time);
		assertEquals(nowTime, LocalTime.parse(time));
		
	}

}
