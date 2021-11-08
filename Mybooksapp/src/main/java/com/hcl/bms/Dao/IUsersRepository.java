package com.hcl.bms.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.bms.beans.Users;


@Repository
public interface IUsersRepository extends JpaRepository<Users, Integer> {

	
	Users findByName(String name);
	

}
