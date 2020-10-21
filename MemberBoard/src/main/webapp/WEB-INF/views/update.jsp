<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>

	function update(){
		updateform.submit();
	}
</script>
</head>
<body>
<h1>update.jsp</h1>
		<form action="boardupdateprocess" name="updateform" method="post">
		제목<br> <input type="text" name="btitle" id="btitle" size="50px" value="${boardUpdate.btitle}"><br>
		<p></p>
		내용<br>
		<textarea cols="50" rows="5" id="bcontents" name="bcontents">${boardUpdate.bcontents}</textarea>
		<p></p>
		<br> <input type="hidden" name="bwriter" id="bwriter" size="50px" value="${boardUpdate.bwriter}" readonly><br>
		<p></p>
		<br> <input type="hidden" name="bnumber" id="bnumber" size="50px" value="${boardUpdate.bnumber}" readonly><br>
		<p></p>
		<button onclick="update()">수정하기</button>
		<button onclick="boardlist">글 목록</button>
</form>
</body>
</html>