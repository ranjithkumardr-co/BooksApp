package com.hcl.bms.Dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.bms.beans.Books;


@Repository
public interface IBooksRepository extends JpaRepository<Books, Integer>{

	
	@Transactional
	@Modifying
	@Query(value="delete from user_books where book_id=?1",nativeQuery = true)
	int deletereference(Integer bookid);
}
