package com.wecare.booking.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wecare.booking.exception.WecareException;
import com.wecare.booking.service.BookService;
import com.wecare.booking.dto.BookingDTO;

@RestController
@Validated
public class BookRestController {
	@Autowired
	BookService service;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/users/{userId}/booking/{coachId}")
	public ResponseEntity<Boolean> bookAppointment(@PathVariable("userId") String userId,@PathVariable("coachId") String coachId,@Valid @RequestBody Map<String, String> InputJson) 
			throws WecareException, ConstraintViolationException, MethodArgumentNotValidException{
		logger.info("Inside bookAppointment method of {}",this.getClass());
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateOfAppointment = LocalDate.parse(InputJson.get("dateOfAppointment").toString(), dateFormat);
		String slot = InputJson.get("slot").toString();
		return ResponseEntity.ok().body(service.bookAppointment(userId, coachId, dateOfAppointment, slot));
	}
	
	@PutMapping("/booking/{bookingId}")
	public ResponseEntity<Boolean> rescheduleAppointment(@PathVariable  Integer bookingId, @Valid @RequestBody Map<String, String> InputJson) 
			throws WecareException, ConstraintViolationException, MethodArgumentNotValidException{
		logger.info("Inside rescheduleAppointment method of {}",this.getClass());
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateOfAppointment = LocalDate.parse(InputJson.get("dateOfAppointment").toString(), dateFormat);
		String slot = InputJson.get("slot").toString();
		return ResponseEntity.ok().body(service.rescheduleAppointment(bookingId, dateOfAppointment, slot));
	}
	
	@DeleteMapping("/booking/{bookingId}")
	public ResponseEntity<?> cancelAppointment(@PathVariable  Integer bookingId)throws WecareException, ConstraintViolationException{
		logger.info("Inside cancelAppointment method of {}",this.getClass());
		service.cancelAppointment(bookingId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/booking/{id}")
	public ResponseEntity<List<BookingDTO>> showMyAppointment(@PathVariable("id")  String id) throws WecareException, ConstraintViolationException{
		logger.info("Inside showMyAppointment method of {}",this.getClass());
		char[] idArray = id.toCharArray();
		if(idArray[0]== 'U') {
			return ResponseEntity.ok().body(service.findBookingByUserId(id));
		}else {
			return ResponseEntity.ok().body(service.findBookingByCoachId(id));
		}
	}
	
}
