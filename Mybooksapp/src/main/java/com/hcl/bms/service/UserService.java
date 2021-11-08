package com.hcl.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.bms.Dao.IBooksRepository;
import com.hcl.bms.Dao.IUsersRepository;
import com.hcl.bms.Exception.AccessDeniedException;
import com.hcl.bms.Exception.IdnotfoundException;
import com.hcl.bms.Exception.IncorrectcredentialsException;
import com.hcl.bms.beans.Books;
import com.hcl.bms.beans.Users;

@Service
public class UserService {

	@Autowired
	IUsersRepository userRepository;

	@Autowired
	IBooksRepository bookRepository;
	
	
	public Users searchUser(String username) {
		// TODO Auto-generated method stub
		
		Users user = userRepository.findByName(username);
        System.out.println(user);
		  if(user==null)
		  {
			  throw new IncorrectcredentialsException("username or password wrong");
		  }
		  else
		  {
		return user;
		  }
	}
	
	public Users findUser(Integer userSessionId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userSessionId).orElseThrow(() -> new IdnotfoundException("Id not found"));
	}


	public Users adduser(Users user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	public List<Users> displayUsers() {
		// TODO Auto-generated method stub

		return userRepository.findAll();

	}
	
	public Users updateUser(Users user,Integer userid) {
		
		Users u = userRepository.findById(userid).orElseThrow(() -> new IdnotfoundException("Id not found"));
		return userRepository.save(user);
		
	}
	
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		Users u = userRepository.findById(userId).orElseThrow(() -> new IdnotfoundException("Id not found"));
		userRepository.delete(u);
	}



	public List<Books> displayBooks() {
		// TODO Auto-generated method stub

		return bookRepository.findAll();

	}
	
	public Books addBook(Books book) {
		// TODO Auto-generated method stub
		return bookRepository.save(book);
	}
	
	public Books updateBook(Books book,Integer userid) {
		
		Books b = bookRepository.findById(userid).orElseThrow(() -> new IdnotfoundException("Id not found"));
		return bookRepository.save(book);
		
	}

	public Books searchbook(Integer id) {
		// TODO Auto-generated method stub
		return bookRepository.findById(id).orElseThrow(() -> new IdnotfoundException("Id not found"));
	}

	public List<Books> getBooksById(Integer id) {
		// TODO Auto-generated method stub

		Users user = userRepository.findById(id).orElseThrow(() -> new IdnotfoundException("Id not found"));
		List<Books> bookslist = user.getBookslist();
		return bookslist;
	}

	public Users likeBook(Integer id,Integer userId) {
		// TODO Auto-generated method stub
		

		Users user = userRepository.findById(userId).orElseThrow(() -> new IdnotfoundException("Id not found"));
		Books book1 = bookRepository.findById(id).orElseThrow(() -> new IdnotfoundException("Id not found"));

		user.getBookslist().add(book1);
		userRepository.save(user);
		return user;
	}

	public Users unLikeBook(Integer id,Integer userId) {
		// TODO Auto-generated method stub
		

		Users user = userRepository.findById(userId).orElseThrow(() -> new IdnotfoundException("Id not found"));
		Books book1 = bookRepository.findById(id).orElseThrow(() -> new IdnotfoundException("Id not found"));
		user.getBookslist().remove(book1);
		userRepository.save(user);
		return user;
	}

	public Users getUser(Integer sessionid) {
		// TODO Auto-generated method stub
		return userRepository.findById(sessionid).orElseThrow(() -> new IdnotfoundException("Id not found"));
	}

	

	public String removeBook(Integer bookid) {
		// TODO Auto-generated method stub
		
		Books book1 = bookRepository.findById(bookid).orElseThrow(() -> new IdnotfoundException("Id not found"));
		bookRepository.deletereference(bookid);
		bookRepository.delete(book1);
		return book1.getBookname()+ " Deleted SuccessFully";
		
		
		
		

			
		
	
	}




	





	


}
