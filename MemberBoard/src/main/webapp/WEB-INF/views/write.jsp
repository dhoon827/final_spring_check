<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>write.jsp</h1>
<form action="boardwrite" method="post" enctype="multipart/form-data">
		제목<br> <input type="text" name="btitle" id="btitle" size="50px"><br>
		<p></p>
		내용<br>
		<textarea cols="50" rows="5" id="bcontents" name="bcontents"></textarea>
		<p></p>
		첨부파일<br> <input type="file" name="bfile" id="bfile" ><br>
		<p></p>
		<br> <input type="hidden" name="bwriter" id="bwriter" size="50px" value="${loginId}" readonly><br>
		<p></p>
		<input type="submit" value="글쓰기">
		
</form>
</body>
</html>