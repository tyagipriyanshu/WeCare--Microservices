package com.wecare.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wecare.booking.controller.BookingCoachFeign;
import com.wecare.booking.controller.BookingUserFeign;
import com.wecare.booking.dto.CoachDTO;
import com.wecare.booking.dto.UserDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.vavr.concurrent.Future;

@Service
public class BookCircuitBreakerService {
	
	@Autowired
	BookingCoachFeign coachFeign;
	
	@Autowired
	BookingUserFeign userFeign;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@CircuitBreaker(name="bookingService",fallbackMethod = "getUserDTOFallback")
	public Future<UserDTO> getUserDTO(String userId) {
		logger.info("Inside getUserDTO method of {}",this.getClass());
//		return userFeign.getSpecificUser(userId);
		return Future.of(()->userFeign.getSpecificUser(userId));
	}
	
	public Future<UserDTO> getUserDTOFallback(String userId, Throwable throwable) {
		logger.info("Inside getUserDTOFallback method of {}",this.getClass());
		return Future.of(()-> new UserDTO());
	}
	
	
	@CircuitBreaker(name="bookingService",fallbackMethod = "getCoachDTOFallback")
	public Future<CoachDTO> getCoachDTO(String coachId) {
		logger.info("Inside getCoachDTO method of {}",this.getClass());
//		return coachFeign.getSpecificCoach(coachId);
		return Future.of(()->coachFeign.getSpecificCoach(coachId));
	}
	
	public Future<CoachDTO> getCoachDTOFallback(String coachId, Throwable throwable) {
		logger.info("Inside getCoachDTOFallback method of {}",this.getClass());
		return Future.of(()-> new CoachDTO());
	}
}
