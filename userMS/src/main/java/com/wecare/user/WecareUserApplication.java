package com.wecare.user;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class WecareUserApplication{
	@Bean
	ModelMapper modelMapper() {
        return new ModelMapper();
    }
	public static void main(String[] args) {
		SpringApplication.run(WecareUserApplication.class, args);
	}
}
