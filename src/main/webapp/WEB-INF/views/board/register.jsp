<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h3>글쓰기 폼</h3>
 <form action="/board/register" method="post">
  <div>
  	제목 : <input type="text" name="title">
  </div>
  <div>
  	내용 : <textarea rows="3" cols="50" name="content"></textarea>
  </div>
  <div>
  	작성자 : <input type="text" name="writer">
  </div>
 <div>
  	<input type="submit" value="글저장">
  </div>
 </form>
</body>
</html>