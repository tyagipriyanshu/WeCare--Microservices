package com.wecare.user.exception;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wecare.user.dto.ErrorMessage;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	@ExceptionHandler(WecareException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler(WecareException ex){
		ErrorMessage error = new ErrorMessage();
		error.setMessage(ex.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception ex) {
		return ex.getMessage().toString();
	}
	
	//for reqParam
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) 
	{
		ErrorMessage error = new ErrorMessage();
	    error.setMessage(e.getBindingResult().getAllErrors()
	    		 		  	.stream().map(ObjectError::getDefaultMessage)//lambda equivalent -> x->x.getDefaultMessage()
	    		 		  	.collect(Collectors.joining(", ")));
	    return ResponseEntity.badRequest().body(error);
	}
	
	//for dto
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> ConstraintValidationExceptionsHandler(ConstraintViolationException ex) 
	{
		ErrorMessage error = new ErrorMessage();
	     error.setMessage(ex.getConstraintViolations()
	    		 			.stream().map(ConstraintViolation::getMessage)//lambda equivalent -> x->x.getMessage()
	    		 			.collect(Collectors.joining(", ")));
	     return ResponseEntity.badRequest().body(error);
	}
}
