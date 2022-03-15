package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML파일) = @Controller
//사용자가 요청하면 데이터를 응답해주는 컨트롤러
@RestController
public class HttpController {
	
	private static final String TAG ="HttpControllerTest";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//build - 순서 없음
		Member m= Member.builder().username("one").password("1111").email("one@nate.com").build();
		System.out.println(TAG+"getter:"+m.getUsername());
		m.setUsername("jin");
		System.out.println(TAG+"getter:"+m.getUsername());
	
		return "lombok test완료";
		
	}
	
	
	//http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {
		
		return "get 요청:" +m.getId() + ", " + m.getUsername() + ", " +m.getPassword()+ ", "+ m.getEmail();
	}
	
	//http 405 =  해당 메소드가 허용되지 않는다는 오류 -> 인터넷 브라우저 요청은  무조건 get요청밖에 할 수  없다!!
	//http://localhost:8080/http/post (insert)
	@PostMapping("/http/post") //text/plain -raw, application.json
	public String postTest(@RequestBody Member m) {//MessageConverter(스프링부트)
		return "post 요청:" +m.getId() + ", " + m.getUsername() + ", " +m.getPassword()+ ", "+ m.getEmail();
	}
	
	//http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 :" +m.getId() + ", " + m.getUsername() + ", " +m.getPassword()+ ", "+ m.getEmail();
	}
	
	//http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}

}
