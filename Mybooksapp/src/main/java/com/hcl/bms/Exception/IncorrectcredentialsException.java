package com.hcl.bms.Exception;

public class IncorrectcredentialsException extends RuntimeException{

private String message;
	
	public IncorrectcredentialsException(String msg)
	{
		this.message=msg;
		
	}
	
	public String getErrorMessage()
	{
		return this.message;
	}
}
