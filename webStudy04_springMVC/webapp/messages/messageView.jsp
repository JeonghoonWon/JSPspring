<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>message view</title>
</head>
<body>
블라인드 처리 사유 : <%=session.getAttribute("reason") %>
<!-- reason 더이상  필요없으니  지우기  -->
<%
	session.removeAttribute("reason");
%>
</body>
</html>