package com.wecare.user.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {
	private String userId;
	@NotNull
	@Size(min = 3,max = 50)
	private String name;
	@NotNull
	private char gender;
	@NotNull
	private LocalDate dateOfBirth;
	@NotNull
	@Size(min = 5,max = 10)
	private String password;
	@NotNull
	@Pattern(regexp="(^$|[0-9]{10})")
	private String mobileNumber;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Pattern(regexp="(^$|[0-9]{6})")
	private String pincode;
	@NotNull
	@Size(min = 3,max = 20)
	private String city;
	@NotNull
	@Size(min = 3,max = 20)
	private String state;
	@NotNull
	@Size(min = 3,max = 20)
	private String country;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
