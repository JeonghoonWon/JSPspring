<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>Cookie</h4>
	<pre>
		: Http 의 stateless 특성을 보완하기 위해 최소한의 상태 정보를 저장하는 개념.
		1. session : 상태 정보를 서버사이드에서 유지.
		2. cookie : 상태 정보를 클라이언트 사이드에서 유지.
		
		**쿠키 사용 방법
		<%
			Cookie sampleCookie = new Cookie("sampleCookie","CookieValue");	
			response.addCookie(sampleCookie);
			
			String koreanValue = URLEncoder.encode("한글쿠키", "UTF-8");
			Cookie koreanCookie = new Cookie("koreanCookie",koreanValue);
			response.addCookie(koreanCookie);
			
			Cookie allDomain = new Cookie("allDomainCookie", "all domain");
			allDomain.setDomain(".wjh.com");  // www 생략 가능.
			response.addCookie(allDomain);
			
		%>
		
		-server-
		1. 쿠키 생성	
		2. 응답에 쿠키를 포함하여 전송(header)
		
		-client-
		3. 쿠키가 브라우저가 가진 저장소에 저장.(한 사람이 사용하는 브라우저가 여러개 )
		4. 저장되어있던 쿠키가 다음번 요청에 실려 재전송(header 를 통해서 재전송)
		
		-server-
		5.요청에 포함되어있는 쿠키를 꺼내서 상태 복원 : <a href="cookieView.jsp">쿠키 확인하기 (동일경로)</a>
		
		
		** 쿠키 속성의 종류
		1. name(required) : 공백, 특수문자 사용할 수 없다. java 변수 생성과 동일한 형식
		2. value(required) : 특수문자를 포함하는 경우, RFC2396 규약에 따라 url encoding 방식을 사용함.
		3. domain(host) :domain의 기본 구조를 알아야 한다.
		4. path
		5. MaxAge(expires)
		6. httpOnly
		7. secure
	</pre>

</body>
</html>