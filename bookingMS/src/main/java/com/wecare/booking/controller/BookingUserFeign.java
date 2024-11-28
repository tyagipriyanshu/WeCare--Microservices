package com.wecare.booking.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wecare.booking.dto.UserDTO;

@FeignClient(name="WeCareUserMS", url="http://localhost:9400/")
public interface BookingUserFeign {
	@GetMapping("users/{userId}")
	UserDTO getSpecificUser(@PathVariable String userId);
}
