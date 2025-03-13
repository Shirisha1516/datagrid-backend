package com.tml.AIP_POSITION_JDG_TRANS.exceptions;

import java.time.LocalDateTime;

public class ResponseException {
	private String message;
    private LocalDateTime dateTime;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
    
    
	
}
