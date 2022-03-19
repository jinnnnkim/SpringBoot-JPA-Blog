package com.cos.blog.test;



import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController //html 파일이 아니라 data를 리턴해주는 controller = RestController
public class DummyControllerTest {

	@Autowired//의존성 주입
	private UserRepository userRepository;
	
	
	//save 함수는 id를 전달하지 않으면 insert를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해요.
	//email, password
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		
		try {
			userRepository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다. 다시 시도해주세요.";
		}
		
		
		return "삭제가 되었습니다. id: "+id;
	}
	
	@Transactional //함수 종료시 자동 commit됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {//json 데이터를 요청 => Java Object(MessgeConverter의 Jackson라이브러리가 변환해서 받아줌!-@RequestBody)
		System.out.println("id :"+id);
		System.out.println("password :"+requestUser.getPassword());
		System.out.println("email :"+requestUser.getEmail());
		//Update문에 save 사용 잘 안함. - null
		//이럴경우 람다식으로 처리
		User user = userRepository.findById(id).orElseThrow(()->{//영속화
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
	
		//userRepository.save(user);
		
		//더티 체킹	@Transactional
		return user;	
	}
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
		
	}
	
	//페이징 처리
	//페이지당 2건에 데이터를 리턴받아 볼 예정
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault (size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return pagingUser;
		
	}
	
	
	//{id} 주소로 파라미처를 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/1
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//user/4을 찾으면 내가 데이터 베이스에서 못찾아오게 되면 user가 null이 될것 아냐?
		//그럼 return null이 리턴 되잖아 그럼 프로그램에 문제가 있지 않을까
		//Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return
		/*
		 * User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
		 * 
		 * @Override public User get() { // TODO Auto-generated method stub return new
		 * User(); }
		 * 
		 * }) ;
		 */
		
		/* 람다식
		 * User user = userRepository.findById(id).orElseThrow(() -> { //익명 처리 가능
		 * return new IllegalArgumentException("해당 유저는 없습니다.");  });
		 * return user;
		 * }
		 */
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id :"+id);
			}	
		});
		//요청 : 웹브라우저에서 진행함
		//user 객체는  자바 오브젝트임
		//웹 브라우저가 이해할 수 있는 데이터로 변환 => json(Gson 라이브러리)
		//스프링부트 =MessageConverter라는 애가 응답시에 자동 작동함
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
		return user;
		
	}
	
	
	
	//http://localhost:8000/blog/dummy/join
	//http의 body에 username, password, email 데이터를 가지고 요청함
	@PostMapping("/dummy/join")
	public String join(User user) {//key=value 약속된 규칙
		
		System.out.println("username:" +user.getUsername());
		System.out.println("password:" +user.getPassword());
		System.out.println("email:" +user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료 되었습니다.";
	}
}
