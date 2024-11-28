package com.wecare.user.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wecare.user.dto.BookingDTO;

@FeignClient(name="WeCareBookingMS", url="http://localhost:9400/")
public interface UserBookingFeign {
	@GetMapping("/booking/{userId}")
	List<BookingDTO> getAppointments(@PathVariable String userId);
}
