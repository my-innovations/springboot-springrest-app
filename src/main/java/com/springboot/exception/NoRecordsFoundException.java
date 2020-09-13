package com.springboot.exception;

public class NoRecordsFoundException extends RuntimeException {

	private static final long serialVersionUID = -4632443851454903594L;

	private String msg;

	public NoRecordsFoundException() {
	}

	public NoRecordsFoundException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
