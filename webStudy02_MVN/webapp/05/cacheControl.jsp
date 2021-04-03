<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/cacheControl.jsp</title>
</head>
<body>
	<h4>캐시 제어</h4>
	
<pre>

	캐시 제어를 위한 응답 헤더
	1) Cache-Control(Http 1.1)	: [public(공용. 라우터에 남음), private(개인)]:캐시를 남긴다. no-cache(no-store)
				이것도 만료 시점을 정할 수 있음 : max-age(초단위) 얼마동안 살려둘것인가를 정할 수 있음. 
	2) Pragma(Http 1.0) : [public(공용. 라우터에 남음), private(개인)]:캐시를 남긴다. no-cache(no-store)
	
	3) Expires : 캐시 데이터의 만료 시점을 정한다.(date) 

		max-age 와 Expires 가 같이 사용되면 max-age 가 우선 사용됨.
	<%
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store"); 
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires",0); 
		// addHeader를 이용해서 추가해준다.
		// 클라이언트가 어떤 브라우저를 통해 들어올지 모르기때문에(웹 표준화)
		
		
		
	%>

</pre>
	<img src = "<%=application.getContextPath() %>/images/Coca-Cola-logo.png" /> <!-- 상위로 올라가서(../)이미지 폴더로 이동(images)  -->
	


</body>
</html>