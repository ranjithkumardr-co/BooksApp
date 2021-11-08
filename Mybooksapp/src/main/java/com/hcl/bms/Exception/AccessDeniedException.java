package com.hcl.bms.Exception;

public class AccessDeniedException  extends RuntimeException{

	
private String message;
	
	 public AccessDeniedException(String msg)
	{
		this.message=msg;
		
	}
	
	public String getErrorMessage()
	{
		return this.message;
	}
}
