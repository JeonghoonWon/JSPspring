<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>Welcome Page!!</h4>
<%
	String authId = (String)session.getAttribute("authId");
	if(authId != null && !authId.isEmpty()){
		// 로그인 성공
		%>
		<form name ="logoutForm" method = "post" action ="<%=request.getContextPath() %>/login/logout.do"></form>
		<!-- 인증처리 과정에선 무조건 post 방식으로 요청해야 함. post 방식으로 처리하기 위해선 form 이 필요 -->
		<%= authId %> 님 로그인 상태
		<a href="#" onclick ="clickHandler(event);" >로그아웃</a>
		<script type="text/javascript">
			function clickHandler(event){
				event.preventDefault();
				document.logoutForm.submit();
				return false;
			}
			
		</script>
		
		
		<%
	}else {
		%>
		<%=session.getAttribute("message") %>
		<a href="<%=request.getContextPath() %>/login/loginForm.jsp "></a>
		<%
		}
		%>


</body>
</html>