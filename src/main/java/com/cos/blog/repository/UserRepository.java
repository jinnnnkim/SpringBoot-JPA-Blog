package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//DAO
//자동으로 been 등록됨
//@Repository 생략 가능
public interface UserRepository  extends JpaRepository<User, Integer>{
	
	//select * from user where username =1?;
	Optional<User> findByUsername(String username);

}


//JPA Naming  쿼리 전략 방법 1.
//findByUsernameAndPassword = select * from user where username =? and password =?;
//User findByUsernameAndPassword(String username, String password);

//JPA Naming  쿼리 전략 방법 2.
/*
 * @Query(value = "select * from user where username =?1 and password =?2", nativeQuery = true)
 *  User login(String username, String password);
 */