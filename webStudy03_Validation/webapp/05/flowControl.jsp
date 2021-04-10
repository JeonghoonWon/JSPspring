<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/flowControl.jsp</title>
</head>
<body>
<h4>웹 어플리케이션에서 흐름 제어 방법(페이지 이동)</h4>

<pre>
	1. 서버내에서의 위임구조, RequestDispatcher 사용
		- 원번 요청(request)을 도착점에서 사용 할 수 있는 구조.
		1) forward
		2) include
	<%--
			// 서버 안에서만 돌고 자기 정보를 알기 때문에 /webStudy01/webapps 필요없다. 
			// context path 필요 없음.
			String dest = "/04/localeDesc.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(dest);	
			rd.include(request, response); // A // A 주소로 나가기때문에 client 는 B 의 존재 모른다.
			// rd.include(request, response); // A+B // A 주소로 나가기때문에 client 는 B 의 존재 모른다.
			/* 목적지 필요 */
			
	--%>
	
		
	2. Redirect : Body 가 없이, 상태코드(302) 로 구성된 line 과
				Location 헤더를 가지고 응답이 전송됨.
				(중간에 응답이 나가면 ) HTTP 의 Stateless 특성에 따라 원본 요청에 대한 정보는 사라짐.
				
			<%	// client 가 사용해야 하는 주소. 
				// client 는 주소를 모르기 떄문에 getContextPath 필요
				String location = request.getContextPath() + "/04/localeDesc.jsp";
				response.sendRedirect(location);
			%>					 
	
	
		똑같은 목적지 주소를 사용하더라도 서버안에서만 도는것과 클라이언트와 함께 실행되는 것에 따라 
		getContextPath 필요 하거나 필요 없다.

</pre>

</body>
</html>