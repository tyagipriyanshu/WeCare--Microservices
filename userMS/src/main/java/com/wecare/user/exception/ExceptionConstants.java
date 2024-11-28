package com.wecare.user.exception;

public enum ExceptionConstants {
	SERVER_ERROR("server.error"),
	USER_NOT_FOUND("user.not.found"),
	USER_INSERT_SUCCESS("user.insert.success"),
	USER_LOGIN_SUCCESS("user.login.success"),
	USER_PASSWORD_INVALID("user.password.invalid"),
	BOOKING_ALREADY_EXISTS("booking.already.exists"),
	COACH_NOT_FOUND("coach.not.found"),
	COACH_DATA_UNAVAILABLE("coach.data.unavailable"),
	COACH_INSERT_SUCCESS("coach.insert.success"),
	COACH_PASSWORD_INVALID("coach.password.invalid"),
	BOOKING_NOT_FOUND("booking.not.found"),
	SPRING_MAIL_USERNAME("spring.mail.username");
	private final String type;
	private ExceptionConstants(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return this.type;
	}
}
