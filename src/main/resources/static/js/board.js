let index = {
	init: function() {
		//제이쿼리 사용
		$("#btn-save").on("click", () => {//function(){}, ()=> {} this를 바인딩하기 위함!
			this.save();
		});
	  $("#btn-delete").on("click", () => {
			this.deleteById();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});
	},
	
	//글쓰기
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
	},
	
	//글 삭제
	deleteById: function() {	
		let id = $("#id").text();
		
		$.ajax({
			type: "DELETE", 
			url:"/api/board/"+id,
			dataType:"json"
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	//글 수정
	update: function() {
	   let id = $("#id").val();		
	   
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
	
		$.ajax({
			type: "PUT", 
			url:"/api/board/"+id,
			data: JSON.stringify(data), //http body  데이터
			contentType: "application/json; charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("글수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	//댓글쓰기
	replySave: function() {
		let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		};
		
		$.ajax({
			type: "POST", 
			url:`/api/board/${data.boardId}/reply`,	//오류 수정 -  요청 주소에 데이터 값이 존재할때 ``이걸로 요청함
			data: JSON.stringify(data), //http body  데이터
			contentType: "application/json; charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("댓글 작성이 완료되었습니다.");
			location.href=`/board/${data.boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	//댓글삭제
	replyDelete: function(boardId, replyId) {
		$.ajax({
			type: "DELETE", 
			url:`/api/board/${boardId}/reply/${replyId}`,	//오류 수정 -  요청 주소에 데이터 값이 존재할때 ``이걸로 요청함
			dataType:"json"
		}).done(function(resp){
			alert("댓글이 삭제 되었습니다.");
			location.href=`/board/${boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
}
index.init();