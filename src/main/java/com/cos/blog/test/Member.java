package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//getter, setter 동시에 선언시 @Data 사용
//@AllArgsConstructor -- 모든 생성자 사용시
//@RequiredArgsConstructor  -- final 생성자 사용시


@Data
@NoArgsConstructor
public class Member {
	private  int id;
	private  String username;
	private String password;
	private  String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
}
