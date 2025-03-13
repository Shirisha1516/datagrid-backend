package com.tml.AIP_POSITION_JDG_TRANS.exceptions;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	    @Value(value = "${data.exception.message1}")
	    private String message1;
	    @Value(value = "${data.exception.message2}")
	    private String message2;
	    @Value(value = "${data.exception.message3}")
	    private String message3;
	    @Value(value = "${data.exception.message4}")
	    private String message4;
	    
	    @ExceptionHandler(value = BlogNotFoundException.class)
	    public ResponseEntity<String> blogNotFoundException(BlogNotFoundException blogNotFoundException) {
	        return new ResponseEntity<String>(message2, HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(value = Exception.class)
	    public ResponseEntity<Object> databaseConnectionFailsException(Exception exception) {
	        return new ResponseEntity<>(message4, HttpStatus.INTERNAL_SERVER_ERROR);
	       }
	    
	    @ExceptionHandler(NotFoundException.class)
	    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException){
	    	
			/*
			 * ResponseException responseException = new ResponseException();
			 * responseException.setDateTime(LocalDateTime.now());
			 * responseException.setMessage(message2); ResponseEntity<Object> entity = new
			 * ResponseEntity<Object>(responseException, HttpStatus.NOT_FOUND); return
			 * entity;
			 */

			return new ResponseEntity<>(message2,HttpStatus.NOT_FOUND);
	    	
	    }
}
