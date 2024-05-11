package com.found_404.funco.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgument(MethodArgumentNotValidException e) {
		StringBuilder errorCode = new StringBuilder();
		StringBuilder message = new StringBuilder();

		e.getBindingResult()
			.getFieldErrors()
			.forEach(error ->
			{
				errorCode.append(error.getField()).append(" ");
				message.append(error.getDefaultMessage()).append(" ");
			});

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(new BaseException(HttpStatus.BAD_REQUEST,
				errorCode.toString(),
				message.toString())));
	}

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex));
	}
}
