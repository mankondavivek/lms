package io.needle.lms.dto;

import lombok.Data;

@Data
public class CustomError{
	
	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public CustomError(String msg){
		errorMessage = msg;
	}
}