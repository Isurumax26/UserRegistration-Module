package com.example.demo.Exception;

import java.awt.TrayIcon.MessageType;

public class FieldValidationError {
	
	private String field;
	private String meessage;
	private MessageType type;
	

	
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMeessage() {
		return meessage;
	}
	public void setMeessage(String meessage) {
		this.meessage = meessage;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	
	

}
