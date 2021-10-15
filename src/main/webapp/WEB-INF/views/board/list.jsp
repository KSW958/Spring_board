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
<script>
	$(document).ready(function(){

	 // 새글쓰기주소 이동
	 $("#regBtn").click(function(){
		 self.location = "/board/register";
	 });

	 let actionForm = $("#actionForm");
	 
	 //조회페이지 주소이동
	 // 리스트에서 제목 클릭시 해당
	 $(".move").click(
		 function(e){
			 // 참조되고 있는 a태그의 링크기능을 해제.
			 e.preventDefault();

			// actionForm 참조하는 작업
			actionForm.append("<input type='hidden' name='bno' value='" + $(this).attr("href")  +"'>" );
			actionForm.attr("action", "/board/get");
			actionForm.submit();
		 }
	 );

	 //페이지번호 클릭 작업  prev 1 2 3 4 5 next
	 $(".page-item a").on("click", function(e){

		e.preventDefault(); // <a>태그의 링크기능을 비활성화

		actionForm.find("input[name='pageNum']").val($(this).attr("href"));  // 사용자가 선택한 페이지번호 변경
		actionForm.submit();	

		console.log("click");
	 });



	});
</script>
</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Tables</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
				 Board List Page
				 <button id="regBtn" type="button" class="btn btn-xs float-right">새글 작성하기</button>
				</div>
				
				<div class="panel-body">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>수정일</th>
							</tr>
						</thead>
						<c:forEach items="${list }" var="board">
					 	 	<tr>
					 	 		<td><c:out value="${board.bno }" /></td>
					 	 		<td><a  class="move" href="${board.bno }"><c:out value="${board.title }" /></a></td>
					 	 		<td><c:out value="${board.writer }" /></td>
					 	 		<td><fmt:formatDate value="${board.regdate }" pattern="yyyy-MM-dd" /></td>
					 	 		<td><fmt:formatDate value="${board.updatedate }" pattern="yyyy-MM-dd" /></td>
					 	 	</tr>
 	 					</c:forEach>
					</table>
					<!-- 검색기능 화면출력 -->
					<div class="float-left">
						<div class="row">
							<div class="col-lg-12">
								<form id="searchForm" action="/board/list" method="get">
									<select name="type">
										<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }" />>--</option>
										<option value="T" <c:out value="${pageMaker.cri.type eq 'T' ? 'selected' : '' }" />>제목</option>
										<option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : '' }" />>내용</option>
										<option value="W" <c:out value="${pageMaker.cri.type eq 'W' ? 'selected' : '' }" />>작성자</option>
										<option value="TC" <c:out value="${pageMaker.cri.type eq 'TC' ? 'selected' : '' }" />>제목 or 내용</option>
										<option value="TW" <c:out value="${pageMaker.cri.type eq 'TW' ? 'selected' : '' }" />>제목 or 작성자</option>
										<option value="TWC" <c:out value="${pageMaker.cri.type eq 'TWC' ? 'selected' : '' }" />>제목 or 내용 or 작성자</option>
									</select>
									
									<input type="text" name="keyword" value="<c:out value="${pageMaker.cri.keyword }" />">
									<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
									<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
									<button type="submit" class="btn btn-default">검색</button>
								</form>
							</div>
	
						</div>
					</div>
			
					<!-- 페이징 화면출력  v4. pull-right 지원안됨.  float-right -->
					<div class="float-right">
						<ul class="pagination">
							<c:if test="${pageMaker.prev }">
								<li class="page-item"><a data-pagenum="${pageMaker.startPage - 1 }" class="page-link btnPagePrev" href="${pageMaker.startPage-1}">Previous</a></li>
							</c:if>
							
							<c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
								<li class="page-item ${pageMaker.cri.pageNum == num ? "active" : ""} "><a data-pagenum="${num }" class="page-link" href="${num}">${num }</a></li>
							</c:forEach>
							
							<c:if test="${pageMaker.next }">
								<li class="page-item"><a class="page-link btnPageNext" href="${pageMaker.endPage+1}">Next</a></li>
							</c:if>
						</ul>
					</div>	
				</div>
				<!--  페이지 번호(prev,next포함)클릭시 아래 form이 작동되게 처리한다. -->
				<form id="actionForm" action="/board/list" method="get">
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
					<input type="hidden" name="type" value="${pageMaker.cri.type}">
					<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
				</form>
				
			</div>
		</div>
	</div>

	<script>
		if ('${result}' == 'success')	{
			alert("수정되었습니다.");
		}
	</script>
</body>
</html>