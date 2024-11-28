package com.wecare.coach.service;

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

import com.wecare.coach.dto.CoachDTO;
import com.wecare.coach.dto.LoginDTO;
import com.wecare.coach.entity.CoachEntity;
import com.wecare.coach.exception.ExceptionConstants;
import com.wecare.coach.exception.WecareException;
import com.wecare.coach.repository.CoachRepository;

@Service
@PropertySource("classpath:messages.properties")
public class CoachService {
	@Autowired 
	CoachRepository coachRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private Environment environment;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String createCoach(CoachDTO coachDTO){
		logger.info("Inside createCoach method of {}",this.getClass());
		CoachEntity entity = mapper.map(coachDTO, CoachEntity.class);
		coachRepository.save(entity);
		environment.getProperty(ExceptionConstants.COACH_INSERT_SUCCESS.toString());
		return entity.getCoachId(); 	
	}
	
	public boolean loginCoach(LoginDTO loginDTO) throws WecareException {
		logger.info("Inside loginCoach method of {}",this.getClass());
		Optional<CoachEntity> op = coachRepository.findById(loginDTO.getId());
		if(op.isPresent()) {
			CoachEntity entity = op.get();
			if(entity.getPassword().contentEquals(loginDTO.getPassword())) {
				return true;
			}
			throw new WecareException(environment.getProperty(ExceptionConstants.COACH_PASSWORD_INVALID.toString())); 
		}
		throw new WecareException(environment.getProperty(ExceptionConstants.COACH_NOT_FOUND.toString()));
	}
	
	public CoachDTO getCoachProfile(String coachId) throws WecareException{
		logger.info("Inside getCoachProfile method of {}",this.getClass());
		Optional<CoachEntity> op = coachRepository.findById(coachId);
		if(op.isPresent()) {
			CoachEntity entity = op.get();
			CoachDTO dto = mapper.map(entity, CoachDTO.class);
			return dto;
		}
		throw new WecareException(environment.getProperty(ExceptionConstants.COACH_NOT_FOUND.toString())); 
	}
	
	public List<CoachDTO> showAllCoaches() throws WecareException{
		logger.info("Inside showAllCoaches method of {}",this.getClass());
		Optional<List<CoachEntity>> oplst = Optional.of(coachRepository.findAll());
		if(oplst.isPresent()) {
			List<CoachEntity> coachEntityList = oplst.get();
			List<CoachDTO> coachDTOList = coachEntityList.stream().map(coach->mapper.map(coach,CoachDTO.class)).collect(Collectors.toList());
			return coachDTOList;
		}
		throw new WecareException(environment.getProperty(ExceptionConstants.COACH_DATA_UNAVAILABLE.toString()));
	}
}
