<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="memberlogin" method="post">
<h1>LOGIN</h1>
		아이디<br> <input type="text" name="mid" id="mid" placeholder="아이디를 입력하세요" maxlength="20" size="50px">
		<p></p>
		비밀번호<br> <input type="text" name="mpassword" id="mpassword" maxlength="20" placeholder="비밀번호를 입력하세요" size="50px"><br>
		<p></p>
		<input type="submit" value="로그인">
		
		<input type="button" onclick="location.href='memberjoinform'" value="회원가입">
		
		<h3>카카오 로그인</h3>
		<a href="kakaologin">
			<img src="${pageContext.request.contextPath}/resources/uploadFile/kakao_login_medium_narrow.png">
		</a><br>
		
		<h3>네이버 로그인</h3>
		<a href="naverlogin">
			<img src="${pageContext.request.contextPath}/resources/uploadFile/네이버 아이디로 로그인_완성형_Green.PNG" style="width: 183px; hight: 45px;">
		</a><br>
</form>
</body>
</html>
