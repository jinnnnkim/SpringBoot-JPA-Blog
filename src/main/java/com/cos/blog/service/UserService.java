package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록 해줌. => Ioc해줌
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// pwd 암호화
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		});
		return user;
	}

	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();// 1234원문
		String encPasswrod = encoder.encode(rawPassword);// 해쉬
		user.setPassword(encPasswrod);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public void 회원수정(User user) {
		// 수정시, 영속성 컨텍스트 User 오브젝트를 영속화시키고 영속화된 User 오브젝트를 수정
		// select를해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위함
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주기 때문!!
		User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원찾기 실패");
		});

		// Validate 체크 => oauth 필드에 값이 없으면 수정 가능
		if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		// 회원 수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 자동으로 됨
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹되어 update문을 날려줌
	}
}
