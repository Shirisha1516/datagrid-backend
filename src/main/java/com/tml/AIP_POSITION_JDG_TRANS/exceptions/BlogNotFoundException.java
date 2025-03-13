package com.tml.AIP_POSITION_JDG_TRANS.exceptions;

public class BlogNotFoundException extends RuntimeException {
  
	private String message;

    public BlogNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public BlogNotFoundException() {
    }
}
