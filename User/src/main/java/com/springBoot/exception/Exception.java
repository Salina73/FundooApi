package com.springBoot.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class Exception extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	String msg;
	int code;
	
	 public Exception(String msg)
	 {
		super(msg);
	 }
	 
	 	public Exception(int code, String msg)
	 	{
	 		super(msg);
	 		this.code =code;
	 	}
}
