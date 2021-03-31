<%@page import="kr.or.ddit.vo.MemberVO"%>

<%@page import="kr.or.ddit.utils.CookieUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


</head>
<body>



<h1>안녕하세요 환영페이지 입니다.</h1>

	<!-- 쿠키값으로 id정보 받아오기 -->


	<% 

	MemberVO authMember = (MemberVO) session.getAttribute("authId");
	
	
	CookieUtils  utils =new CookieUtils(request); 
	String  idCookie = null;
	if(utils.exists("idCookie")){
		
		idCookie = utils.getCookieValue("idCookie");
		
	}	
	
	
	 if(authMember !=null){
		
		%>
		
		
		
		<form name="LogoutForm"  method="post"  action="<%=request.getContextPath()%>/login/logout02.do">
		<%=authMember.getMem_name()%>님이 로그인 되었습니다!!
		<a href="#" >이메일</a>
		<a href="#" onclick="clickHandler(event);">로그아웃</a>
		<script type="text/javascript">
			function clickHandler(event){
				event.preventDefault();
				
				document.LogoutForm.submit();
				
				return false;
				
				
			}
		
		
		</script>
		</form>
	
		<% 
		
	}else{
		
		%>
		<a href="<%=request.getContextPath()%>/login/loginForm02.jsp">로그인페이지로</a>
		<a href="<%=request.getContextPath() %>/member/memberInsert123.do">회원가입</a>
		<% 
		
	}
%>


</body>
</html>