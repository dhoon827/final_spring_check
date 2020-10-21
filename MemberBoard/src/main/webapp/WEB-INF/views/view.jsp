<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>
	$(document).ready(function(){
		$("#commentWriteBtn").click(function(){
			var cwriter = $("#cwriter").val();
			var ccontents = $("#ccontents").val();
			var cbnumber = "${boardView.bnumber}";
			$.ajax({
				type : "post",
				url : "comment/commentwrite",
				data : {
					"cwriter" : cwriter,
					"ccontents" : ccontents,
					"cbnumber" : cbnumber},
				dataType : "json",
				success : function(result){
					console.log("댓글성공");
					console.log(result);
		
		/* 			for(var i in result){
						"작성자 : "+result[i].cwriter;
						"내용 : "+result[i].ccontents;
					}
					$("#commentArea").html(output);
					$("#cwriter").val("");
					$("#ccontents").val("");
				}, */
				
					var output = "<table border='1'";
					output += "<tr><th>작성자</th>";
					output += "<th>내용</th></tr>";
					for(var i in result){
						output += "<tr>";
						output += "<td>"+result[i].cwriter+"</td>";
						output += "<td>"+result[i].ccontents+"</td>";
						output += "</tr>";
					}
					output += "</table>";
					$("#commentArea").html(output);
					$("#cwriter").val("");
					$("#ccontents").val("");
				},
				error : function(){
					console.log("댓글실패")
				}
			});
		});
	});
	

</script>
</head>
<body>
<h1>view.jsp</h1>
		글번호 : ${boardView.bnumber}<br>
		작가 : ${boardView.bwriter}<br>
		글제목 : ${boardView.btitle}<br>
		글내용 : ${boardView.bcontents}<br>
		날짜 : ${boardView.bdate}<br>
		조회수 : ${boardView.bhits}<br>
		첨부파일<br>
		<img src=".*/uploadFile/${boardView.bfilename}">
		<br>
		<a href="boardlistpaging">글 목록 조회</a>
		<c:if test="${sessionScope.loginId == boardView.bwriter}">
		<a href="boardupdate?bnumber=${boardView.bnumber}">글 내용 수정</a>
			<c:if test="${sessionScope.loginId == boardView.bwriter || sessionScope.loginId == 'admin'}">
				<button onclick="location.href='boarddelete?bnumber=${boardView.bnumber}'">삭제</button>
			</c:if>
		</c:if>
	
		<div id="commentWrite">
			작성자 : <input type="text" id="cwriter"><br>
			내용 : <input type="text" id="ccontents"><br>
			<button id="commentWriteBtn">댓글입력</button>
		</div>
		<div id="commentArea">
		<table border="1">
	<tr>
		<th>작성자</th> <th>내용</th></tr>
	<c:forEach var="board" items="${commentList}">
		<tr>
			<td>${board.cwriter}</td>
			<td>${board.ccontents}</td>
	</c:forEach>
	</table>
<%-- 	<c:forEach var="board" items="${commentList}">
		작성자 : ${board.cwriter}
		내용 : ${board.ccontents}
		<br>
	</c:forEach> --%>
		</div>
</body>
</html>