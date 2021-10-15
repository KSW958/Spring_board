<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="../include/common.jsp" %>



<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<script id="replyListTemplate" type="text/x-handlebars-template">
<h5>Reply List</h5>
{{#each .}}
<div class="reply-row">
<b>No.<span class="reply-rno">{{rno}}</span></b><br>
<b class="reply-replyer">{{replyer}}</b> 작성일 : <b>{{prettifyDate replyDate}}</b><br>
<span class="reply-reply">{{reply}}</span>
<button class="btnModalEdit">수정</button><button class="btnModalDel">삭제</button>
<hr>
</div>
{{/each}}
</script>

<script>
	// 함수가 작성된 위치에 따라서 동작이 안되는 경우가 나오게 됨(주의사항)
	
	Handlebars.registerHelper("prettifyDate", function(timeValue){
		let dateObj = new Date(timeValue);
		let year = dateObj.getFullYear();
		let month = dateObj.getMonth() + 1;
		let date = dateObj.getDate();

		return year + "/" + month + "/" + date;
	})
	
	// 댓글목록 출력작업
	// replyArr : 댓글데이타를 받을 파라미터, target : 댓글목록이 삽입될 위치, templateObj : 템플릿을 참조할 파라미터
	let printData = function(replyArr, target, templateObj) {

		let template = Handlebars.compile(templateObj.html());
		let html = template(replyArr);
		target.empty(); // 기존 댓글목록 지우기.(화면)
		target.append(html);
	}

 // 페이징 출력작업  pagenation
 let printPaging = function(pageMaker, target){
	let str = "";

	if(pageMaker.prev){
		str += "<li class='page-item'><a class='page-link' href='" + (pageMaker.startPage - 1) + "'>[prev]</a></li>";
	}

	// 1 2 3 4 5
	for(let i=pageMaker.startPage, len = pageMaker.endPage; i <= len; i++){
		let strClass = (pageMaker.cri.pageNum == i) ? 'active' : '';
		//console.log(strClass);

		str += "<li class='page-item " + strClass + "'><a class='page-link' href='" + i + "'>" + i + "</a></li>";
	}

	if(pageMaker.next){
		str += "<li class='page-item'><a class='page-link' href='" + (pageMaker.endPage + 1) + "'>[next]</a></li>";
	}

	target.html(str);
 }
	
	
	// /replies/1048576/1
  let bno = ${board.bno };
	let replyPage = 1;  // 클릭한 댓글 페이지번호
	let pageInfo = "/replies/" + bno + "/" + replyPage;
	getPage(pageInfo);

	function getPage(pageInfo){
	  // 댓글정보와페이징정보를 json으로 받아오는 구문
		$.getJSON(pageInfo, function(data){
			//alert(data.list.length);
			printData(data.list, $("#replyListView"), $("#replyListTemplate"));
			printPaging(data.pageMaker, $("#pagination"));

			//$("#replyListView").html(txt);

		});	

	}

</script>
<script>
	$(document).ready(function(){

		let operForm = $("#operForm"); // 글번호, Critetira(검색,페이징정보)
		// 수정버튼 클릭작업
		$("button[data-oper='modify']").on("click", function(){
			operForm.attr("action", "/board/modify").submit(); // chainning
			// operForm.submit();
		});

		$("button[data-oper='list']").on("click", function(){
			operForm.attr("action", "/board/list").submit(); // chainning
			// operForm.submit();
		});

		// Modal 댓글 추가
		$("#btnReplyModal").on("click", function(){
			$('#replyModal').modal('show');

			// 추가,수정,삭제 버튼 3개를 가리킴
			$("button[data-modal='btnCommon']").hide();

			$("#btnReplySave").show();

			//$("#replyer").attr("readonly", "");

			$("#replyer").removeAttr("readonly");
			
		});

		// 댓글저장 클릭  /replies/
		$("#btnReplySave").on("click", function(){
			var replyer = $("#replyer").val();  //댓글 작성자
			var replytext = $("#replytext").val(); // 댓글 내용

			// rno, bno, reply, replyer, replyDate, updateDate

			
			/*
			 REST방식에서 지원하는 전송방식이 컨트롤러의 매핑주소로 설정이 되어있으면
       headers 설정을 통하여, 브라우저가 지원하지 못하는 방식의 요청을 해결할수가 있다.
			*/
			$.ajax({
				type:'post',
				url: '/replies/new',
				headers: {
					"Content-Type": "application/json",
					"X-HTTP-Method-Override": "POST"},  // 스프링의 컨트롤러의 매핑주소작업이 REST방식으로 되어있을 경우 사용하는 목적
				dataType:'text',  // 클라이언트에서 전송하는 데이터 포맷
				data: JSON.stringify({bno:${board.bno }, replyer: replyer, reply:replytext}),  // 전송하고자 하는 댓글데이타
				success:function(result){
					// 댓글등록작업이 성공적으로 끝나면 호출되는 구문.
					//alert(result);
					if(result == "success"){
						alert("댓글 등록됨")

						$('#replyModal').modal('hide');
						// 입력양식 지우기
						$("#replyer").val("");
						$("#replytext").val("");

						//  /replies/1048576/1 주소를 호출하여, 댓글목록과 페이징정보를 가져오는 작업
						let bno = ${board.bno };
						let replyPage = 1; // 체크할 사항
						getPage("/replies/" + bno + "/" + replyPage);  //  /replies/1048576/1
					}
				
				}
				
			});
		});


		// 댓글 페이지 번호 클릭작업. 동적태그로 구성된 태그에 대하여, 이벤트 설정하는 기능을 제공
		$(".pagination").on("click","li a" ,function(e){
			e.preventDefault();
			// console.log("댓글페이지번호 클릭");

			replyPage = $(this).attr("href");

			getPage("/replies/" + bno + "/" + replyPage);
		});

		// 댓글목록에서 수정, 삭제 버튼 클릭작업
		// 1)댓글 수정 폼
		$("#replyListView").on("click", ".btnModalEdit", function(){
			
			$('#replyModal').modal('show');

			// 추가,수정,삭제 버튼 3개를 가리킴
			$("button[data-modal='btnCommon']").hide();
			$("#btnReplyModify").show();  
			

			// 모달 대화상자에 수정내용을 보여주기
			

			$("#modalMethod").html("Reply Modify");
			

			//console.log("rno? " + $(this).parent().find(".reply-rno").html());
			$("#rno").val($(this).parent().find(".reply-rno").html());  // 댓글번호
			$("#replytext").val($(this).parent().find(".reply-reply").html()); // 댓글내용

			$("#replyer").attr("readonly", "readonly");
			$("#replyer").val($(this).parent().find(".reply-replyer").html()); //댓글작성자


			

		});


		//댓글수정하기
		$("#btnReplyModify").click(function(){
			
			// ajax 수정처리
			$.ajax({
				type:'put',
				url: '/replies/modify/' + $("#rno").val(),
				headers: {
					"Content-Type": "application/json",
					"X-HTTP-Method-Override": "PUT"},  // 스프링의 컨트롤러의 매핑주소작업이 REST방식으로 되어있을 경우 사용하는 목적
				dataType:'text',  // 클라이언트에서 전송하는 데이터 포맷
				data: JSON.stringify({reply:$("#replytext").val()}),  // 전송하고자 하는 댓글데이타
				success:function(result){
					// 댓글등록작업이 성공적으로 끝나면 호출되는 구문.
					//alert(result);
					if(result == "success"){
						alert("댓글 수정됨")

						$('#replyModal').modal('hide');
						// 입력양식 지우기
						$("#replyer").val("");
						$("#replytext").val("");

						//  /replies/1048576/1 주소를 호출하여, 댓글목록과 페이징정보를 가져오는 작업
						let bno = ${board.bno };
						
						getPage("/replies/" + bno + "/" + replyPage);  //  /replies/1048576/1
					}
				
				}
				
			});
		});
		// 2)댓글 삭제 폼
		$("#replyListView").on("click", ".btnModalDel", function(){
			
			$('#replyModal').modal('show');

			// 추가,수정,삭제 버튼 3개를 가리킴
			$("button[data-modal='btnCommon']").hide();
			$("#btnReplyDelete").show();  

			$("#modalMethod").html("Reply Delete");
			

			//console.log("rno? " + $(this).parent().find(".reply-rno").html());
			$("#rno").val($(this).parent().find(".reply-rno").html());  // 댓글번호
			$("#replytext").val($(this).parent().find(".reply-reply").html()); // 댓글내용

			$("#replyer").attr("readonly", "readonly");
			$("#replyer").val($(this).parent().find(".reply-replyer").html()); //댓글작성자

			

		});

		// 댓글삭제하기
		$("#btnReplyDelete").click(function(){

			// ajax 삭제처리
			$.ajax({
				type:'delete',
				url: '/replies/delete/' + $("#rno").val(),
				headers: {
					"Content-Type": "application/json",
					"X-HTTP-Method-Override": "DELETE"},  // 스프링의 컨트롤러의 매핑주소작업이 REST방식으로 되어있을 경우 사용하는 목적
				dataType:'text',  // 클라이언트에서 전송하는 데이터 포맷
				success:function(result){
					// 댓글등록작업이 성공적으로 끝나면 호출되는 구문.
					//alert(result);
					if(result == "success"){
						alert("댓글 삭제됨")

						$('#replyModal').modal('hide');
						// 입력양식 지우기
						$("#replyer").val("");
						$("#replytext").val("");

						//  /replies/1048576/1 주소를 호출하여, 댓글목록과 페이징정보를 가져오는 작업
						let bno = ${board.bno };
						
						getPage("/replies/" + bno + "/" + replyPage);  //  /replies/1048576/1
					}
				
				}
				
			});
		});
	});
</script>
</head>
<body>
	<div>
		글번호 : <input type="text" name="bno" value='<c:out value="${board.bno }" />' readonly="readonly"> 
	</div>
	<div>
		글제목 : <input type="text" name="title" value='<c:out value="${board.title }" />' readonly="readonly"> 
	</div>
	<div>
		글내용 : <textarea name="content" rows="3" cols="50" readonly="readonly"><c:out value="${board.content }" /></textarea>
	</div>
	<div>
		작성자 : <input type="text" name="writer" value='<c:out value="${board.writer }" />' readonly="readonly"> 
	</div>
	<div>
		<button data-oper="modify" class="btn btn-default">수정으로 이동하기</button>
		<button data-oper="list" class="btn btn-default">리스트</button>
		<button type="button" id="btnReplyModal" class="btn btn-primary" data-toggle="modal">댓글</button>
	</div>

	<form id="operForm" action="/board/modify" method="get">
		<input type="hidden" name="bno" value="${board.bno}">
		<input type="hidden" name="pageNum" value="${cri.pageNum}">
		<input type="hidden" name="amount" value="${cri.amount}">
		<input type="hidden" name="type" value="${cri.type}">
		<input type="hidden" name="keyword" value="${cri.keyword}">
	</form>

	<!-- 댓글 출력위치-->
	<div id="replyListView">

	</div>
  <!-- 페이징 출력위치 -->
	<div>
		<ul id="pagination" class="pagination"></ul>
	</div>

	<!-- modal : 댓글 폼, 댓글 수정, 댓글 삭제-->


<div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalMethod">Reply Write</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="replytext" class="col-form-label">Reply:</label>
            <input type="text" class="form-control" id="replytext">
						<input type="hidden"  id="rno">
          </div>
          <div class="form-group">
            <label for="replyer" class="col-form-label">Replyer:</label>
            <input type="text" class="form-control" id="replyer">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button data-modal="btnCommon" type="button" class="btn btn-primary" id="btnReplySave">Save</button>
				<button data-modal="btnCommon" type="button" class="btn btn-info" id="btnReplyModify">Modify</button>
				<button data-modal="btnCommon" type="button" class="btn btn-danger" id="btnReplyDelete">Delete</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>