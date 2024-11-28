package com.wecare.coach.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wecare.coach.dto.BookingDTO;

@FeignClient(name="WeCareBookingMS", url="http://localhost:9400/")
public interface CoachBookingFeign {
	@GetMapping("/booking/{coachId}")
	List<BookingDTO> getAppointments(@PathVariable String coachId);
}
