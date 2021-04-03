<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<% /* session 으로 넘어오는 비번오류 메시지를 받기위함.  */
	String message = (String)session.getAttribute("message");
	session.removeAttribute("message");
	if(message !=null && !message.isEmpty()){
		%>
		<script type="text/javascript">
			alert("<%=message%>");
			
		</script>
		<%
	}
%>
</head>
<body>

<form method="post">
	<input type ="text" name = "mem_pass" required />
	<input type = "submit" value = "인증" />
</form>

</body>
</html>