package com.wecare.booking.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.wecare.booking.dto.BookingDTO;
import com.wecare.booking.entity.BookingEntity;
import com.wecare.booking.dto.CoachDTO;
import com.wecare.booking.dto.UserDTO;
import com.wecare.booking.exception.ExceptionConstants;
import com.wecare.booking.exception.WecareException;
import com.wecare.booking.repository.BookRepository;
import com.wecare.booking.utility.MailUtility;

import io.vavr.concurrent.Future;

@Service
@PropertySource("classpath:messages.properties")
public class BookService {
	
	@Autowired 
	BookRepository bookRepository;

	@Autowired
	private Environment environment;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BookCircuitBreakerService circuitBreaker;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	private MailUtility mail;
	
	public boolean bookAppointment(String userId, String coachId, LocalDate appointmentDate, String slot){
		logger.info("Inside bookAppointment method of {}",this.getClass());
		BookingEntity entity = new BookingEntity();
		String userName,coachName,email;
		Future<UserDTO> fUser = circuitBreaker.getUserDTO(userId);
		Future<CoachDTO> fCoach = circuitBreaker.getCoachDTO(coachId);
		UserDTO user = fUser.get();
		CoachDTO coach = fCoach.get();
		entity.setUserId(user.getUserId());
		userName = user.getName();
		email = user.getEmail();
		entity.setCoachId(coach.getCoachId());
		coachName = coach.getName();
		entity.setAppointmentDate(appointmentDate);
		entity.setSlot(slot);
		BookingEntity bookingEntity = bookRepository.saveAndFlush(entity);
		//bookAppointment mail 
		Integer bookingId = bookingEntity.getBookingId();
		mail.sendSchedulingEmail(userName, coachName, email, bookingId, slot, appointmentDate);
		return true;
	}
	
	public boolean rescheduleAppointment(Integer bookingId, LocalDate appointmentDate, String slot) throws WecareException{
		logger.info("Inside rescheduleAppointment method of {}",this.getClass());
		String userName,coachName,email;
		Optional<BookingEntity> op = bookRepository.findById(bookingId);
		if(op.isPresent()) {
			BookingEntity entity = op.get();
			entity.setAppointmentDate(appointmentDate);
			entity.setSlot(slot);
			bookRepository.save(entity);
			//rescheduleAppointment mail 
			Future<UserDTO> fUser = circuitBreaker.getUserDTO(entity.getUserId());
			Future<CoachDTO> fCoach = circuitBreaker.getCoachDTO(entity.getCoachId());
			UserDTO user = fUser.get();
			CoachDTO coach = fCoach.get();
			userName = user.getName();
			email = user.getEmail();
			coachName = coach.getName();
			mail.sendReschedulingEmail(userName, coachName, email, bookingId, slot, appointmentDate);
			return true;
		}
		throw new WecareException(environment.getProperty(ExceptionConstants.BOOKING_NOT_FOUND.toString()));
	}
	
	public void cancelAppointment(Integer bookingId) throws WecareException {
		logger.info("Inside cancelAppointment method of {}",this.getClass());
		String userName,coachName,email,slot;
		LocalDate appointmentDate;
		Optional<BookingEntity> op = bookRepository.findById(bookingId);
		if(op.isPresent()) {
			BookingEntity entity = op.get();
			//cancelAppointment mail 
			Future<UserDTO> fUser = circuitBreaker.getUserDTO(entity.getUserId());
			Future<CoachDTO> fCoach = circuitBreaker.getCoachDTO(entity.getCoachId());
			UserDTO user = fUser.get();
			CoachDTO coach = fCoach.get();
			userName = user.getName();
			email = user.getEmail();
			coachName = coach.getName();
			slot = entity.getSlot();
			appointmentDate = entity.getAppointmentDate();
			mail.sendCancellingEmail(userName, coachName, email, bookingId, slot, appointmentDate);
			bookRepository.delete(entity);
		}else {
			throw new WecareException(environment.getProperty(ExceptionConstants.BOOKING_NOT_FOUND.toString()));
		}
	}
	
	public BookingDTO findByBookingId(Integer bookingId) throws WecareException {
		logger.info("Inside findByBookingId method of {}",this.getClass());
		Optional<BookingEntity> op = bookRepository.findById(bookingId);
		if(op.isPresent()) {
			BookingEntity entity = op.get();
			BookingDTO dto = mapper.map(entity, BookingDTO.class);
			return dto;
		}
		throw new WecareException(environment.getProperty(ExceptionConstants.BOOKING_NOT_FOUND.toString()));
	}
	
	public List<BookingDTO> findBookingByUserId(String userId) throws WecareException{
		logger.info("Inside findBookingByUserId method of {}",this.getClass());
		List<BookingEntity> bookingEntityLst = bookRepository.findBookingByUserId(userId,LocalDate.now());
		List<BookingDTO> bookingLst = bookingEntityLst.stream().map(b->mapper.map(b, BookingDTO.class)).collect(Collectors.toList());
		return bookingLst;
	}
	
	public List<BookingDTO> findBookingByCoachId(String coachId) throws WecareException{
		logger.info("Inside findBookingByCoachId method of {}",this.getClass());
		List<BookingEntity> bookingEntityLst = bookRepository.findBookingByCoachId(coachId,LocalDate.now());
		List<BookingDTO> bookingLst = bookingEntityLst.stream().map(b->mapper.map(b, BookingDTO.class)).collect(Collectors.toList());
		return bookingLst;
	}
}
