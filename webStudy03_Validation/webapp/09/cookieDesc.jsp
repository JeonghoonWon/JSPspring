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
			
//			Cookie allDomain = new Cookie("allDomainCookie", "all domain");
//			allDomain.setDomain(".wjh.com");  // www 생략 가능. tomcat 8.0 이상에선 사용 할 수 없다.
//			response.addCookie(allDomain);	// 

			Cookie allPathCookie = new Cookie("allPathCookie", "All~Path~");
			allPathCookie.setPath("/");  
			// 이 주소는 client,browser 가 사용하는 것이기 때문에 절대경로를 지정해주는 contextpath 가 필요하다. 그런데 contextpath 를 사용하면  "/url " 까지 나오기 때문에 슬러쉬를   ("/") 입력해준다 
			response.addCookie(allPathCookie);
			
			Cookie longLiveCookie = new Cookie("longLiveCookie", "Long~~");
			longLiveCookie.setMaxAge(60*60*24*3); // int type : 초 단위   60*60*24*3 : 3일
			longLiveCookie.setPath(request.getContextPath());	// name 과 path option 이 같아야만 기존 cookie 를 지울 수 있다. name 만으로는 불가능 하다. 
			response.addCookie(longLiveCookie);
			
		%>
		
		-server-
		1. 쿠키 생성	
		2. 응답에 쿠키를 포함하여 전송(header)
		
		-client-
		3. 쿠키가 브라우저가 가진 저장소에 저장.(한 사람이 사용하는 브라우저가 여러개 )
		4. 저장되어있던 쿠키가 다음번 요청에 실려 재전송(header 를 통해서 재전송)
		
		-server-
		5.요청에 포함되어있는 쿠키를 꺼내서 상태 복원 : 
		<a href="cookieView.jsp">쿠키 확인하기 (동일경로)</a>
		<a href="../08/cookieView.jsp">쿠키 확인하기 (다른경로)</a>
		
		
		** 쿠키 속성의 종류
		1. name(required) : 공백, 특수문자 사용할 수 없다. java 변수 생성과 동일한 형식
		2. value(required) : 특수문자를 포함하는 경우, RFC2396 규약에 따라 url encoding 방식을 사용함.
		3. domain(host) :domain의 기본 구조를 알아야 한다. 쿠키의 재전송을 결정하는 조건
						 생략시, 쿠키 생성 도메인이 기본값으로 반영된다.
						 ex) .naver.com : host 에 상관없이 재전송 가능.
		4. path : 쿠키의 재전송을 결정하는 조건.
			 다음번 요청이 설정된 경로 이하로 발생할때만 재전송.  
			 생략시, 쿠키 생성시의 경로가 기본값으로 반영됨.
				   
		5. MaxAge(expires) : 쿠키의 만료 시점 (second)
			생략시, 기본값으로 세션과 동일한 만료 시점이 사용.
			0 : 쿠키 삭제(쿠키의 모든 조건이 동일한 경우)
			-1 : 브라우져가 종료 될 때 쿠키를 삭제 하겠다.
			
		6. httpOnly : http 프로토콜에만 재전송
		7. secure : https 프로토콜에만 재전송 (다른서버가 없는경우는 확인이 불가능하다. 다음주에 확인(3/26 기준)) 
	</pre>

</body>
</html>