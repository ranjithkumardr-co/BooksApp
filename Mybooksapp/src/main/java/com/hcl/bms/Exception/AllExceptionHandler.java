package com.hcl.bms.Exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AllExceptionHandler {
	
	

	@ExceptionHandler(IdnotfoundException.class)
	public ResponseEntity<ResponseMessage> handleIdnotFoundException(HttpServletRequest request,IdnotfoundException ex)
	{
		ResponseMessage rm= new ResponseMessage();
		rm.setMessage(ex.getErrorMessage());
		rm.setErrorCode(404);
		return new ResponseEntity<ResponseMessage>(rm,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ResponseMessage> handleAccessDeniedException(HttpServletRequest request,AccessDeniedException ex)
	{
		ResponseMessage rm= new ResponseMessage();
		rm.setMessage(ex.getErrorMessage());
		rm.setErrorCode(404);
		return new ResponseEntity<ResponseMessage>(rm,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IncorrectcredentialsException.class)
	public ResponseEntity<ResponseMessage> handleIncorrect(HttpServletRequest request,IncorrectcredentialsException ex)
	{
		ResponseMessage rm= new ResponseMessage();
		rm.setMessage(ex.getErrorMessage());
		rm.setErrorCode(404);
		return new ResponseEntity<ResponseMessage>(rm,HttpStatus.NOT_FOUND);
	}

}
