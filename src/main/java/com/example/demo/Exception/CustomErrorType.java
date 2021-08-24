package com.example.demo.Exception;

import com.example.demo.dto.UserDTO;

public class CustomErrorType extends UserDTO {
	
	private String errorMessage;
	
	public CustomErrorType(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
