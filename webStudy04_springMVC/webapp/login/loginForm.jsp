<%@page import="kr.or.ddit.utils.CookieUtils"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
   .error{
      color: red;
      font-weight: bold;
   }
</style>
</head>
<body>
<%
   CookieUtils utils = new CookieUtils(request);      
   String idCookie = null;
   if(utils.exists("idCookie")){
      idCookie = utils.getCookieValue("idCookie");
   }   

   String failedId = (String)session.getAttribute("failedId");
   session.removeAttribute("failedId");
   String message = (String) request.getAttribute("message");
   if(message == null){
      message = (String)session.getAttribute("message");
      // flash attribute
      session.removeAttribute("message");
   }
   if(message!=null && !message.isEmpty()){
      %>
      <div class="error"><%=message %></div>
      <% 
   }
%>
<!-- 
1. 체킹하고 아이디를 기억하면 1주일 동안 아이디를 기억
2. 체크하고 로그인하면 다시 페이지에 돌아왔을때 체크했던 아이디 나오게 하기
3. 체크표시 해제하면 아이디를 지우고, 기억되고 있는 쿠기가 잇으면 그것도 지우기
 -->
<form action="<%=request.getContextPath() %>/login/loginCheck.do" method="post">
   <input type="text" name="mem_id" placeholder="아이디" value="<%=Objects.toString(idCookie, "") %>"/>
   <input type="text" name="mem_pass" placeholder="비밀번호"/>
   <input type="checkbox" name="saveId"  value="saveId" <%=idCookie !=null?"checked":""%>/>아이디 기억하기
   <input type="submit" value="로그인"/>
</form>
</body>
</html>



