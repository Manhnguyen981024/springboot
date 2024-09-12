package com.example.webservice.study_springboot.exception;

import java.time.LocalDateTime;

public class ErrorDetail {
	private LocalDateTime localDateTime;
	private String message;
	private String decription;
	
	public ErrorDetail(LocalDateTime localDateTime, String message, String decription) {
		super();
		this.localDateTime = localDateTime;
		this.message = message;
		this.decription = decription;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public String getMessage() {
		return message;
	}

	public String getDecription() {
		return decription;
	}	
}
