package com.hcl.bms.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;








@Entity
@Table(name="Users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer userid;
	
	@Column(name="password")
	private String password;
	
	@Column(name="user_name")
	private String name;
	
	@Column(name="user_role")
	private String role;
	
	@ManyToMany(targetEntity = Books.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH} )
    @JoinTable(name="user_books",
        joinColumns = {@JoinColumn(name="user_id")},
        inverseJoinColumns = {@JoinColumn(name="book_id")}
    		)
    @JsonIgnoreProperties("userslist")
	private List<Books> bookslist=new ArrayList<Books>();

	@Override
	public String toString() {
		return "Users [userid=" + userid + ", password=" + password + ", name=" + name + ", role=" + role
				+ ", bookslist=" + bookslist + "]";
	}

	public Integer getUserid() {
		return userid;
	}
	
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Books> getBookslist() {
		return bookslist;
	}
	public void setBookslist(List<Books> bookslist) {
		this.bookslist = bookslist;
	}
	 // @JsonIgnoreProperties("userslist")
}
