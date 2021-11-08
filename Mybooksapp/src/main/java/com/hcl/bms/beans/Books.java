package com.hcl.bms.beans;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@Table(name="Books")
@JsonIdentityInfo(
generator = ObjectIdGenerators.PropertyGenerator.class,
property = "bookid")
public class Books {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="book_id")
	private Integer bookid;
	
	@Column(name="book_name")
	private String bookname;
	
	@Column(name="book_url")
	private String bookurl;
	
	@Column(name="book_category")
	private String category;
	
	@Column(name="author_name")
	private String authorname;
	
	
	@ManyToMany(targetEntity = Users.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH} )
	@JsonIgnoreProperties("bookslist")
	@JsonIgnore
	private List<Users> userslist=new ArrayList<Users>();
	public Integer getBookid() {
		return bookid;
	}
	
	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	@Override
	public String toString() {
		return "Books [bookid=" + bookid + ", bookname=" + bookname + ", bookurl=" + bookurl + ", category=" + category
				+ ", authorname=" + authorname + ", userslist=" + userslist + "]";
	}

	public String getBookurl() {
		return bookurl;
	}
	public void setBookurl(String bookurl) {
		this.bookurl = bookurl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAuthorname() {
		return authorname;
	}
	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}
	public List<Users> getUserslist() {
		return userslist;
	}
	public void setUserslist(List<Users> userslist) {
		this.userslist = userslist;
	}

	//@JsonIgnoreProperties("bookslist")
	//@JsonIdentityInfo(
//	 generator = ObjectIdGenerators.PropertyGenerator.class,
//	property = "bookid")
}
