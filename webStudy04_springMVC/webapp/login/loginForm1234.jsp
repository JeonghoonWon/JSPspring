<%@page import="java.nio.channels.SeekableByteChannel"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">

	.error{
	
		color:red;
		font-weight: bold;
	}


</style>

<title>Insert title here</title>
</head>
<body>

<% 

		String failedId = request.getParameter("mem_id");
		String message  =(String) request.getAttribute("message");
		
		
		if(message == null){
			 message  =(String) session.getAttribute("message");
				//flash Attribute
			 
			 session.removeAttribute("message");
		}
		
		
		if(message !=null && !message.isEmpty()){
			%>
			<div class="error" ><%=message %></div>
			
			<%
			
		}
		
		
%>




<form action="<%=request.getContextPath() %>/login/loginCheck123.do" method="post">

	<input type="text" name="mem_id" placeholder="아이디"   value="<%=Objects.toString(failedId,"") %>"  />
																<!--Objects.toString(failedId,"")
																		 failedIds널이면 ""으로변경
																	 	 -->
	<input type="text" name="mem_pw"  placeholder="비밀번호" value=""   />
	<input type="checkbox" name = "saveId" value="saveId">아이디 기억하기
	<!--
		체크하고  로그인을 했을때  일주일간 살아 있다 
		체크안하면 쿠키 삭제하고 1회성 로그인하기 !!
 	
	 
	 -->
	<input type="submit" value="로그인"/>
	

</form>



</body>
</html>