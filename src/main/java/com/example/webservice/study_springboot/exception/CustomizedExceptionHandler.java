package com.example.webservice.study_springboot.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.webservice.study_springboot.user.UserNotFoundException;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetail> handleAllException(Exception ex, WebRequest request) throws Exception {
		ErrorDetail error = new ErrorDetail(LocalDateTime.now()
				, ex.getMessage()
				, request.getDescription(false));
		return new ResponseEntity<ErrorDetail>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetail> handleUserNotFound(Exception ex, WebRequest request) throws Exception {
		ErrorDetail error = new ErrorDetail(LocalDateTime.now()
				, ex.getMessage()
				, request.getDescription(false));
		return new ResponseEntity<ErrorDetail>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorDetail error = new ErrorDetail(LocalDateTime.now()
				, ex.getFieldError().getDefaultMessage()
				, request.getDescription(false));
		
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
}
