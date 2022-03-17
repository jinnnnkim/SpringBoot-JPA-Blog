let index = {
	init: function() {
		//제이쿼리 사용
		$("#btn-save").on("click", () => {//function(){}, ()=> {} this를 바인딩하기 위함!
			this.save();
		});
	},

	save: function() {
		//alert('user의 save함수 호출됨');
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		
		$.ajax({
			type: "POST", 
			url:"/api/board",
			data: JSON.stringify(data), //http body  데이터
			contentType: "application/json; charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	
	}
}
index.init();