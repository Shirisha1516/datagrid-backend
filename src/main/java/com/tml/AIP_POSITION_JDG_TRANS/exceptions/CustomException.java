package com.tml.AIP_POSITION_JDG_TRANS.exceptions;

public class CustomException extends RuntimeException {

	private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException() {
    }
}
