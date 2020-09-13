package com.springboot.error;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ErrorDetails implements Serializable {

	private static final long serialVersionUID = -8167734678987694839L;

	private List<String> errorDetails;
	private Date dateTime;

	public ErrorDetails(Date dateTime, List<String> errorDetails) {
		super();
		this.dateTime = dateTime;
		this.errorDetails = errorDetails;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public List<String> getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(List<String> errorDetails) {
		this.errorDetails = errorDetails;
	}
}
