package com.fik.springmvc.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FIKResponse {

	String status;
	String responseCode;
	String message;

	
	@XmlElement
	public String getStatus() {
		return status;
	}
	
	@XmlElement
	public String getResponseCode() {
		return responseCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@XmlElement
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
