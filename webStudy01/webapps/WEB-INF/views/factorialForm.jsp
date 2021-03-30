<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>팩토리얼 연산 처리</h4>

<!-- 
동기처리 연산 수행. 선택할 수 있는 UI 있어야함. 
server에서 동기화 마샬링 직렬화 진행 해야 함.
비동기 처리 연산 수행(JSON, HTML) 
-->
<%--
	Map<String,Object> target =(Map)request.getAttribute("target");

--%>

<form method ="post"> 
	<!--   -->
	<!--form action이 없기 때문에 submit을 눌러도 주소가 동일하다. 식별성이 없음. -->
	<!-- 대상 : 주소  / 행위정보 : 메서드  -->
	<input type = "number" name = "single" value="${target.op} "/>
	<input type = "submit" value= "=" />
<!-- 	EL 언어 -->
	${target["result"] }
</form>
</body>
</html>