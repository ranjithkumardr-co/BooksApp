package com.hcl.bms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.bms.Exception.AccessDeniedException;
import com.hcl.bms.Exception.IdnotfoundException;
import com.hcl.bms.Exception.ResponseMessage;
import com.hcl.bms.beans.Books;
import com.hcl.bms.beans.LoginCredentials;
import com.hcl.bms.beans.Users;
import com.hcl.bms.service.UserService;

@RestController
public class BooksController {
	
	@Autowired
	UserService service;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> login(@RequestBody LoginCredentials user,HttpServletRequest request) {
		
		
		String username= user.getUsername();
		String password=user.getPassword();
		
		Users searcheduser=service.searchUser(username);
		
	
		if(username.equals(searcheduser.getName()) && password.equals(searcheduser.getPassword()))
		{
			HttpSession usersession = request.getSession();

			usersession.setAttribute("userSessionID", searcheduser.getUserid());
			ResponseMessage m=new ResponseMessage();
			m.setMessage(username +" logged In Successfully");
			m.setErrorCode(200);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.OK);
		}
		else
		{
			ResponseMessage m=new ResponseMessage();
			m.setMessage("Username or password incorrect");
			m.setErrorCode(300);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		
		
		
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> logout(HttpServletRequest request){
		
		
		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			ResponseMessage m=new ResponseMessage();
			m.setMessage("please login first to logout");
			m.setErrorCode(300);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		else
		{
			usersession.invalidate();
			ResponseMessage m=new ResponseMessage();
			m.setMessage("logged out successfully");
			m.setErrorCode(200);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		
		
		
	}
	@RequestMapping(value = "/registerAdmin", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> registerAdmin(@RequestBody Users user,HttpServletRequest request) {
		
		
		if(user.getRole().equals("admin"))
		{
			service.adduser(user);
			ResponseMessage m=new ResponseMessage();
			m.setMessage(user.getName()+ " Added as Admin");
			m.setErrorCode(200);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.OK);
			
		}
		else
		{
			ResponseMessage m=new ResponseMessage();
			m.setMessage("Sorry !! only Admins can Register");
			m.setErrorCode(300);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		
		
		
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> addUser(@RequestBody Users user,HttpServletRequest request) {
		
		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			ResponseMessage m=new ResponseMessage();
			m.setMessage("please login first to add the user");
			m.setErrorCode(300);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
			
			Users searcheduser=service.findUser(userSessionId);
			
			if(searcheduser.getRole().equals("admin"))
			{
				service.adduser(user);
				ResponseMessage m=new ResponseMessage();
				m.setMessage(user+ " Added Successfully");
				m.setErrorCode(200);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.OK);
			}
			else
			{
				ResponseMessage m=new ResponseMessage();
				m.setMessage("Access Denied !! only admin can add Users");
				m.setErrorCode(300);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
			}
		}
		

		
		
	}
	
	@RequestMapping(value = "/updateUser/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateUser(@RequestBody Users user,HttpServletRequest request,@PathVariable("userId") Integer userId) {
		
		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			ResponseMessage m=new ResponseMessage();
			m.setMessage("please login first to add the user");
			m.setErrorCode(300);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
			
			Users searcheduser=service.findUser(userSessionId);
			
			if(searcheduser.getRole().equals("admin"))
			{
				Users updateduser=service.updateUser(user,userId);
				ResponseMessage m=new ResponseMessage();
				m.setMessage(updateduser.getName()+ "Updated Successfully");
				m.setErrorCode(200);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.OK);
			}
			else
			{
				ResponseMessage m=new ResponseMessage();
				m.setMessage("Access Denied !! only admin can add Users");
				m.setErrorCode(300);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
			}
		}
		

		
		
	}
	
	@RequestMapping(value = "/displayUsers", method = RequestMethod.GET)
	public ResponseEntity<List<Users>> displayUsers(HttpServletRequest request) {
		
		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			throw new AccessDeniedException("please login to continue");
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
			
			Users searcheduser=service.findUser(userSessionId);
			
			if(searcheduser.getRole().equals("admin"))
			{
				return new ResponseEntity<List<Users>>(service.displayUsers(), HttpStatus.OK);
			}
			else
			{
				throw new AccessDeniedException("Access Denied !! Only Admins Can View User Details");
			}
		}
		
		
		
	}
	
	
	@RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> deleteUser(HttpServletRequest request,@PathVariable("userId") Integer userId) {
		
		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			ResponseMessage m=new ResponseMessage();
			m.setMessage("please login first to add the user");
			m.setErrorCode(300);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
			
			Users searcheduser=service.findUser(userSessionId);
			
			if(searcheduser.getRole().equals("admin"))
			{
				service.deleteUser(userId);
				ResponseMessage m=new ResponseMessage();
				m.setMessage("userId "+userId+ " Deleted Successfully");
				m.setErrorCode(200);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.OK);
			}
			else
			{
				ResponseMessage m=new ResponseMessage();
				m.setMessage("Access Denied !! only admin can add Users");
				m.setErrorCode(300);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
			}
		}
		

		
		
	}
	
	@RequestMapping(value = "/displayBooks", method = RequestMethod.GET)
	public ResponseEntity<List<Books>> displaybooks(HttpServletRequest request) {
		

		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			throw new AccessDeniedException("please login to continue");
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
			
			Users searcheduser=service.findUser(userSessionId);
			
			if(searcheduser.getRole().equals("admin"))
			{
				return new ResponseEntity<List<Books>>(service.displayBooks(), HttpStatus.OK);
			}
			else
			{
				throw new AccessDeniedException("Access Denied !! Only Admins Can View Books");
			}
		}
		
		
		
	}
	
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> addBook(@RequestBody Books book,HttpServletRequest request) {
		
		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			ResponseMessage m=new ResponseMessage();
			m.setMessage("please login first to add the book");
			m.setErrorCode(300);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
			
			Users searcheduser=service.findUser(userSessionId);
			
			if(searcheduser.getRole().equals("admin"))
			{
				Books bookadded=service.addBook(book);
				ResponseMessage m=new ResponseMessage();
				m.setMessage(bookadded + "Added Successfully");
				m.setErrorCode(200);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.OK);
			}
			else
			{
				ResponseMessage m=new ResponseMessage();
				m.setMessage("Access Denied !! only admin can add Book");
				m.setErrorCode(300);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
			}
		}
		

		
		
	}
	
	@RequestMapping(value = "/updateBook/{bookId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateBook(@RequestBody Books book,HttpServletRequest request,@PathVariable("bookId") Integer userId) {
		
		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			ResponseMessage m=new ResponseMessage();
			m.setMessage("please login first to add the user");
			m.setErrorCode(300);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
			
			Users searcheduser=service.findUser(userSessionId);
			
			if(searcheduser.getRole().equals("admin"))
			{
				Books updatedbook=service.updateBook(book,userId);
				ResponseMessage m=new ResponseMessage();
				m.setMessage(updatedbook.getBookname()+ "Updated Successfully");
				m.setErrorCode(200);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.OK);
			}
			else
			{
				ResponseMessage m=new ResponseMessage();
				m.setMessage("Access Denied !! only admin can add Users");
				m.setErrorCode(300);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
			}
		}
		

		
		
	}
	@RequestMapping(value = "/searchBooks/{id}", method = RequestMethod.GET)
	public ResponseEntity<Books> searchbook(@PathVariable("id") Integer id) {
		
		return new ResponseEntity<Books>(service.searchbook(id), HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value = "/getBooksByUserId/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Books>> getBooksById(@PathVariable("id") Integer id) {
		
		return new ResponseEntity<List<Books>>(service.getBooksById(id), HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value = "/likeBook/{bookId}", method = RequestMethod.GET)
	public ResponseEntity<Users> likeBook(@PathVariable("bookId") Integer bookid,HttpServletRequest request) {
		
		

		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			throw new AccessDeniedException("please login To like the Book");
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
		return new ResponseEntity<Users>(service.likeBook(bookid,userSessionId), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/unLikeBook/{bookId}", method = RequestMethod.GET)
	public ResponseEntity<Users> unLikeBook(@PathVariable("bookId") Integer bookid,HttpServletRequest request) {
		
		
		

		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			throw new AccessDeniedException("please login To unlike the Book");
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
		return new ResponseEntity<Users>(service.unLikeBook(bookid,userSessionId), HttpStatus.OK);
		}
		
		
	}
	
	@RequestMapping(value = "/deleteBook/{bookId}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> deleteBook(@PathVariable("bookId") Integer bookid,HttpServletRequest request) {
		
		
		HttpSession usersession = request.getSession(false);
		if( usersession==null || usersession.getAttribute("userSessionID")==null)
		{

			ResponseMessage m=new ResponseMessage();
			m.setMessage("please login first to add the user");
			m.setErrorCode(300);
			return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
		}
		else
		{
			Integer userSessionId=(Integer) usersession.getAttribute("userSessionID");
			
			Users searcheduser=service.findUser(userSessionId);
			
			if(searcheduser.getRole().equals("admin"))
			{
				String deletionstatus=service.removeBook(bookid);
				ResponseMessage m=new ResponseMessage();
				m.setMessage(deletionstatus);
				m.setErrorCode(200);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.OK);
			}
			else
			{
				ResponseMessage m=new ResponseMessage();
				m.setMessage("Access Denied !! only admin can add Users");
				m.setErrorCode(300);
				return new ResponseEntity<ResponseMessage>(m, HttpStatus.CREATED);
			}
		}
		

		
		
		
		
	
		
		
		
	}
}
