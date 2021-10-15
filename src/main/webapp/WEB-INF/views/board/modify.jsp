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
        let frm = $("#frm");

        // 수정,삭제, 리스트를 click()이벤트 메서드를 동시에 한꺼번에 작업한 이유?
        $("button").on("click", function(e){
            e.preventDefault(); // <button type="submit">전송기능이 취소

            let operation = $(this).data("oper");
            console.log(operation);

            if(operation === "remove"){
                frm.attr("action", "/board/remove");
            }else if(operation === "list"){
                frm.attr("action", "/board/list").attr("method", "get");

                let pageNumTag = $("input[name='pageNum']").clone();
                let amountTag = $("input[name='amount']").clone();
                let typeTag  = $("input[name='type']").clone();
                let keywordTag = $("input[name='keyword']").clone();

                frm.empty(); // form태그의 모든내용 제거

                frm.append(pageNumTag);
                frm.append(amountTag);
                frm.append(keywordTag);
                frm.append(typeTag);
            }

            frm.submit();
        })


    });

</script>
</head>
<body>
 <h3>글수정 폼</h3>
 <form id="frm" action="/board/modify" method="post">
  <div>
  	<!--페이징, 검색정보 숨김-->
      <!-- 동일한 파라미터로 인한 오류발생 될수도 있다. bno -->
      <input type="hidden" name="pageNum" value="${cri.pageNum}">
      <input type="hidden" name="amount" value="${cri.amount}">
      <input type="hidden" name="type" value="${cri.type}">
      <input type="hidden" name="keyword" value="${cri.keyword}">
    
    번호 : <input type="text" name="bno" value='<c:out value="${board.bno }" />' readonly="readonly">
  </div>
  <div>
  	제목 : <input type="text" name="title" value='<c:out value="${board.title }" />'>
  </div>
  <div>
  	내용 : <textarea rows="3" cols="50" name="content"><c:out value="${board.content }" /></textarea>
  </div>
  <div>
  	작성자 : <input type="text" name="writer" value='<c:out value="${board.writer }" />' readonly="readonly">
  </div>
  <div>
  	등록일 : <input type="text" name="regdate" value='<fmt:formatDate value="${board.regdate }" pattern="yyyy/MM/dd"/>' readonly="readonly">
  </div>
  <div>
  	수정일 : <input type="text" name="updatedate" value='<fmt:formatDate value="${board.updatedate }" pattern="yyyy/MM/dd"/>' readonly="readonly">
  </div>
 <div>
  	<button type="submit" data-oper="modify" class="btn btn-default">수정하기</button>
    <button type="submit" data-oper="remove" class="btn btn-default">삭제하기</button>
    <button type="submit" data-oper="list" class="btn btn-default">리스트</button>
  </div>
 </form>
 
</body>
</html>