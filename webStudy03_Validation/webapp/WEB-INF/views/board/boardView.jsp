<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/boardView.jsp</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
	
	<h4>${board.bo_no}</h4>
	<table class="table table-bordered">
			<tr>
			<th>게시판종류</th>
			<td>${board.bo_type}</td>
		</tr>
		<tr>
			<th>글번호</th>
			<td>${board.bo_no}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${board.bo_title}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${board.bo_writer}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${board.bo_date}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${board.bo_hit}</td>
		</tr>
		<tr>
			<th>추천수</th>
			<td>${board.bo_rec}</td>
		</tr>
		<tr>
			<th>신고수</th>
			<td>${board.bo_rep}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${board.bo_content}</td>
		</tr>
	</table>
	<jsp:include page="/includee/postScript.jsp" />
</body>
</html>