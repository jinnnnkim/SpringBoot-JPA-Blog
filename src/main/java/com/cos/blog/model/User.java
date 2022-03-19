package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder//빌더 패턴

//ORM -> Java(다른 언어) Object -> 테이블로 매핑해주는 기술
@Entity//User 클래스가 Mysql에 테이블이 자동으로 생성됨
//@DynamicInsert//null값 허용, insert시에 null인 필드를 제외함
public class User {
	
	@Id//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//넘버링 전략
	//프로젝트에서 연결된 DB의 넘버링 전략을 따른다.
	private int id; //오라클-Sequence/Myssql-auto_increment
	
	@Column(nullable = false, length = 100, unique = true)//null이될수 없으면 길이는 30
	private String username;
	
	@Column(nullable = false,length = 100)//길이를 많이 주는 이유는 추후 해쉬(비밀번호 암호화)를 하기위함
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("user")
	//DB는 RoleType이라는게 없다
	@Enumerated(EnumType.STRING)
	private RoleType role;//Enum을 사용하는게 좋다.(값 변경 불가) - 도메인을 사용할 수 있음. //USER,ADMIN
	
	private String oauth;//kakao, google
	
	@CreationTimestamp//시간 자동 입력
	private Timestamp createDate;
}
