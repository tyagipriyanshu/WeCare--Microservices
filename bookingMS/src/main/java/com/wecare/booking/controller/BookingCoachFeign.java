package com.wecare.booking.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wecare.booking.dto.CoachDTO;

@FeignClient(name="WeCareCoachMS", url="http://localhost:9400/")
public interface BookingCoachFeign {
	@GetMapping("coaches/{coachId}")
	CoachDTO getSpecificCoach(@PathVariable String coachId);
}
