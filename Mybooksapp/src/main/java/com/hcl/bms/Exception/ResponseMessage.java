package com.hcl.bms.Exception;

import lombok.Data;

@Data
public class ResponseMessage {
	
	private String message;
	private int errorCode;

}
