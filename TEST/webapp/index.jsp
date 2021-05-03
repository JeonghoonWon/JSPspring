<%@page import="kr.or.ddit.Constants"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- custom tag 를 사용하겠다는 지시자. taglib / prefix 는 c 로 들어감. -->
    <%@taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>Welcome Page~</h4>
<pre>
	누적 방문자 수 : <%=application.getAttribute(Constants.SESSIONCOUNTATTRNAME) %>
</pre>
	<ul>
   <!-- EL을 쓰는 목적은 값을 출력할려고 사용한다. scope안에 있는 것들만 사용가능. if문이나 for문을 사용하지 않음 
   /custom tag를 사용하려면 라이브러리가 필요하다. EL은 속성명만 사용하면 쉽게 꺼낼수 있다.-->
   <c:forEach items="${userList}" var="user">
   <!-- 향상된 for문이고, java로 따지면 user : userList var는 블럭변수의 역할을 한다. -->   
      <li>${user.mem_name }</li><!-- 내부적으로 getter로 사용된것 -->
   </c:forEach>
</ul>
<%
	MemberVO authMember = (MemberVO) session.getAttribute("authMember");
	if(authMember!=null){
		%>
		<form name="logoutForm" method="post" action="<%=request.getContextPath() %>/login/logout.do"></form>
		<a href="<%=request.getContextPath() %>/mypage.do"><%=authMember.getMem_name() %></a>님 [<%=authMember.getMem_role() %>]
		<a href="#" onclick="clickHandler(event);">로그아웃</a>
		<script type="text/javascript">
			function clickHandler(event){
				event.preventDefault();
				document.logoutForm.submit();
				return false;
			}
		</script>
		<%
	}else{
		%>
		<a href="<%=request.getContextPath() %>/login/loginForm.jsp">로그인</a>
		<a href="<%=request.getContextPath() %>/member/memberInsert.do">회원 가입</a>
		<%
	}
%>
</body>
</html>










