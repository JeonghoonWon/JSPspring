<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/session.jsp</title>
</head>
<body>
	<h4> HttpSession </h4>
	
	<pre>
		: 한 세션에 대한 정보를 가진 객체 
		세션 이란?
		통로 : 클라이언트와 서버 사이의 유일한 연결 통로(DataBase)
		기간(시간) : 하나의 클라이언트가 하나의 에이전트를 이용해서 
					서버사이드 어플을 사용하기 시작한 순간(최초의 요청)부터
					사용 종료 이벤트가 발생할대까지의 기간.
		session 은 사람이 아닌 browser 를 대상으로 만들어진다.
		session lifecycle
		생성 : 최초의 요청임이 식별되는 요청이 발생했을때.
			-> 세션 하나에 식별자로 ID 가 부여됨.
			<%= new Date(session.getCreationTime()) %>
			<%= session.getId() %>
			<%= new Date(session.getLastAccessedTime()) %>  <!-- long type 은  MM/Sec  -->
			<%= session.getMaxInactiveInterval() %>s <!-- int type 은 sec -->
			
		종료 
			1) 명시적인 로그아웃
			2) 브라우저 종료
			3) 쿠키 삭제
			4) timeout (기본적으로 tomcat에서 30분) 일정 시간 이내에 새로운 요청이 발생하지 않을때
			
		쿠키의 트래킹 모드(tracking mode)	
		1) Cookie : JSESSIONID 이름의 쿠키를 통해 세션 아이디가 재전송되는 구조.
		2) URL : jsessionid 라는 이름의 세션 파라미터를 요청 라인을 통해 재전송하는 구조. url에 세션아이디가 노출 되기 때문에 보안에 취약하다.
		<a href="sessionDesc.jsp;jsessionid=<%=session.getId() %>">세션 파라미터를 통해 세션 유지</a>   
		3) SSL : Secure Socket Layer 를 클라이언트와 서버 사이에 암호화 되어있는 식별자가 재전송되는 구조. SSL 을 사용하기 위해선 인증서가 필요하다. .OPENSSL 사용 가능
		
		
	
	</pre>


</body>
</html>