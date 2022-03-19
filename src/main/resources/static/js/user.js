let index = {
	init: function() {
		//제이쿼리 사용
		$("#btn-save").on("click", () => {//function(){}, ()=> {} this를 바인딩하기 위함!
			this.save();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
	},

	save: function() {
		//alert('user의 save함수 호출됨');
		
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//데이터 연결 확인시 이 형식으로 함!!
		//console.log(data);
		
		//ajax  통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!
		//ajax호출시 default가 비동기 호출
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환함.->dataType 선언안해도 된다는 말!
		$.ajax({
			//회원가입 수행 요청
			type: "POST", //insert
			url:"/auth/joinProc",
			data: JSON.stringify(data), //http body  데이터
			contentType: "application/json; charset=utf-8", //body데이터가 어뗜 타입인지(mime)
			dataType:"json"//요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경해줌
		}).done(function(resp){
			if(resp.status == 500) {
				alert("회원가입에 실패하였습니다.");
			}else {
				alert("회원가입이 완료되었습니다.");
				location.href="/";
			}
			
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	//회원수정
	update: function() {
		
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({
			type: "PUT",
			url:"/user",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType:"json"
		}).done(function(resp){
			alert("회원수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	
	}
}
index.init();