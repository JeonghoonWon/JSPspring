<%@page import="kr.or.ddit.utils.CookieUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<style type="text/css">
.layer {
   position: absolute;
   top: 50%;
   left: 50%;
   width: auto;
   height: 100px;
   margin: -50px 0 0 -50px;
}
</style>
</head>
<body class="layer">
<h3>관리자 로그인</h3>
<h5>Sing in to start your session</h5>
<form action="<%=request.getContextPath() %>/login/loginCheck.do" method="post">
   <input type="text" name="employee_id" value="${cookie.checkID.value }" placeholder="아이디를 입력하세요." /><br>
   <input type="password" name="employee_pwd" placeholder="패스워드를 입력하세요." /><br>
   <c:choose>
      <c:when test="${cookie.checkID.name eq 'checkID' }">
         <input type="checkbox" name="checkID" checked>Remember Me
      </c:when>
      <c:otherwise>
         <input type="checkbox" name="checkID">Remember Me
      </c:otherwise>      
   </c:choose>
   <input class="btn btn-primary mr-2" type="submit" value="로그인" /> <br>
      <c:url value="/" var="findIdURL">
   </c:url>
   <a href="${findIdURL}">아이디/패스워드 찾기</a>
</form>
</body>
</html>