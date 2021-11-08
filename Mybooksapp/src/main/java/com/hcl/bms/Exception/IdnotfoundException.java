package com.hcl.bms.Exception;

public class IdnotfoundException  extends RuntimeException{
	
	private String message;
	
	public IdnotfoundException(String msg)
	{
		this.message=msg;
		
	}
	
	public String getErrorMessage()
	{
		return this.message;
	}

}
