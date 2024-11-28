package com.wecare.user.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.wecare.user.dto.LoginDTO;
import com.wecare.user.dto.UserDTO;
import com.wecare.user.entity.UserEntity;
import com.wecare.user.exception.ExceptionConstants;
import com.wecare.user.exception.WecareException;
import com.wecare.user.repository.UserRepository;

@Service
@PropertySource("classpath:messages.properties")
public class UserService {
	@Autowired 
	UserRepository userRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private Environment environment;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public String createUser(UserDTO userDTO) {
		logger.info("Inside createUser method of {}",this.getClass());
		UserEntity entity = mapper.map(userDTO, UserEntity.class);
		UserEntity e = userRepository.saveAndFlush(entity);
		String userID = e.getUserId();
		logger.info(environment.getProperty(ExceptionConstants.USER_INSERT_SUCCESS.toString())+" with User ID "+userID);
		return userID;
	}
	
	
	public boolean loginUser(LoginDTO loginDTO) throws WecareException {
		logger.info("Inside loginUser method of {}",this.getClass());
		Optional<UserEntity> op = userRepository.findById(loginDTO.getId());
		if(op.isPresent()) {
			UserEntity entity = op.get();
			if(entity.getPassword().contentEquals(loginDTO.getPassword())) {
				logger.info(environment.getProperty(ExceptionConstants.USER_LOGIN_SUCCESS.toString())+" with User ID "+loginDTO.getId(),this.getClass());
				return true;
			}
			throw new WecareException(environment.getProperty(ExceptionConstants.USER_PASSWORD_INVALID.toString())); 
		}
		throw new WecareException(environment.getProperty(ExceptionConstants.USER_NOT_FOUND.toString()));
	}
	
	
	public UserDTO getUserProfile(String userId) throws WecareException{
		logger.info("Inside getUserProfile method of {}",this.getClass());
		Optional<UserEntity> op = userRepository.findById(userId);
		if(op.isPresent()) {
			UserEntity entity = op.get();
			UserDTO dto = mapper.map(entity, UserDTO.class);
			return dto;
		}
		throw new WecareException(environment.getProperty(ExceptionConstants.USER_NOT_FOUND.toString())); 
	}
}
