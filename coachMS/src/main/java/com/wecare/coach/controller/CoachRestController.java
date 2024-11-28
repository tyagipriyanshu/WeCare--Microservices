package com.wecare.coach.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wecare.coach.dto.CoachDTO;
import com.wecare.coach.dto.ErrorMessage;
import com.wecare.coach.dto.LoginDTO;
import com.wecare.coach.exception.WecareException;
import com.wecare.coach.service.CoachService;
import com.wecare.coach.dto.BookingDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@Validated
@RequestMapping("/coaches")
public class CoachRestController {
	@Autowired
	CoachService service;
	
	@Autowired
	CoachBookingFeign bookingFeign;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping
	public ResponseEntity<String> createCoach(@Valid @RequestBody CoachDTO coachDTO, Errors errors)throws MethodArgumentNotValidException{
		logger.info("Inside createCoach method of {}",this.getClass());
		if (errors.hasErrors()) {
			String response = "";
			response = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(","));
			ErrorMessage error = new ErrorMessage();
			error.setMessage(response);
			return ResponseEntity.badRequest().body(error.getMessage());
		}	
		return ResponseEntity.ok().body(service.createCoach(coachDTO));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Boolean> loginCoach(@Valid @RequestBody LoginDTO loginDTO)throws WecareException,MethodArgumentNotValidException{
		logger.info("Inside loginCoach method of {}",this.getClass());
		return ResponseEntity.ok().body(service.loginCoach(loginDTO));
	}
	
	@GetMapping("/{coachId}")
	public ResponseEntity<CoachDTO> getCoachProfile(@PathVariable  String coachId)throws ConstraintViolationException, WecareException{
		logger.info("Inside getCoachProfile method of {}",this.getClass());
		return ResponseEntity.ok().body(service.getCoachProfile(coachId));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CoachDTO>> showAllCoaches()throws WecareException{
		logger.info("Inside showAllCoaches method of {}",this.getClass());
		return ResponseEntity.ok().body(service.showAllCoaches());
	}
	
	@CircuitBreaker(name="coachService", fallbackMethod = "showMyAppointmentsFallback")
	@GetMapping("/booking/{coachId}")
	public ResponseEntity<List<BookingDTO>> showMyAppointments(@PathVariable  String coachId) throws WecareException, ConstraintViolationException{
		logger.info("Inside showMyAppointments method of {}",this.getClass());
		List<BookingDTO> lst = bookingFeign.getAppointments(coachId);
		return ResponseEntity.ok().body(lst);
	}
	
	public ResponseEntity<List<BookingDTO>> showMyAppointmentsFallback(String coachId, Throwable throwable){
		logger.info("Inside showMyAppointmentsFallback method of {}",this.getClass());
		List<BookingDTO> lst = new ArrayList<>();
		return ResponseEntity.ok().body(lst);
	}
}
