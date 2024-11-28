package com.wecare.user.controller;

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

import com.wecare.user.dto.BookingDTO;
import com.wecare.user.dto.ErrorMessage;
import com.wecare.user.dto.LoginDTO;
import com.wecare.user.dto.UserDTO;
import com.wecare.user.exception.WecareException;
import com.wecare.user.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@Validated
@RequestMapping("/users")
public class UserRestController {
	@Autowired
	UserService service;
	
	@Autowired
	UserBookingFeign bookingFeign;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping
	public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO, Errors errors)throws MethodArgumentNotValidException{
		logger.info("Inside createUser method of {}",this.getClass());
		if (errors.hasErrors()) {
			String response = "";
			response = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(","));
			ErrorMessage error = new ErrorMessage();
			error.setMessage(response);
			return ResponseEntity.badRequest().body(error.getMessage());
		}
		return ResponseEntity.ok().body(service.createUser(userDTO));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Boolean> loginUser(@Valid @RequestBody LoginDTO loginDTO) throws WecareException, MethodArgumentNotValidException{
		logger.info("Inside loginUser method of {}",this.getClass());
		return ResponseEntity.ok().body(service.loginUser(loginDTO));
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserProfile(@PathVariable  String userId) throws WecareException, ConstraintViolationException{
		logger.info("Inside getUserProfile method of {}",this.getClass());
		return ResponseEntity.ok().body(service.getUserProfile(userId));
	}
	
	@CircuitBreaker(name="userService", fallbackMethod = "showMyAppointmentsFallback")
	@GetMapping("/booking/{userId}")
	public ResponseEntity<List<BookingDTO>> showMyAppointments(@PathVariable String userId) throws WecareException, ConstraintViolationException{
		logger.info("Inside showMyAppointments method of {}",this.getClass());
		List<BookingDTO> lst = bookingFeign.getAppointments(userId);
		return ResponseEntity.ok().body(lst);
	}
	
	public ResponseEntity<List<BookingDTO>> showMyAppointmentsFallback(String userId, Throwable throwable){
		logger.info("Inside showMyAppointmentsFallback method of {}",this.getClass());
		List<BookingDTO> lst = new ArrayList<>();
		return ResponseEntity.ok().body(lst);
	}
}
