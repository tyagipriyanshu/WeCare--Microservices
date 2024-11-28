package com.wecare.booking.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CoachDTO {
	private String coachId;
	@NotNull
	@Size(min = 3,max = 50)
	private String name;
	@NotNull
	private String gender;
	@NotNull
	private LocalDate dateOfBirth;
	@NotNull
	@Size(min = 5,max = 10)
	private String password;
	@NotNull
	@Pattern(regexp="(^$|[0-9]{10})")
	private String mobileNumber;
	@NotNull
	@Size(min = 3,max = 50)
	private String speciality;
	public String getCoachId() {
		return coachId;
	}
	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	
}
