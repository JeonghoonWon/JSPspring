<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/pageContextDesc.jsp</title>
</head>
<body>
	<h4>PageContext</h4>
	<pre>
		: 하나의 JSP 에 대한 모든 정보를 가진 객체.
		: 가장 먼저 생성되는 기본객체로 나머지 기본객체에 대한 참조를 소유함.
		
		<%=request==pageContext.getRequest() %> <!-- true : 같은 주소. 같은 객체라는 뜻 -->
		<%= pageContext.getRequest() instanceof HttpServletRequest %> <!-- true -->
		<%=((HttpServletRequest)pageContext.getRequest()).getRequestURI() %> <!-- /webStudy01/06/pageContextDesc.jsp  -->
		<%=session==pageContext.getSession() %> <!-- true -->
		<%=application==pageContext.getServletContext() %> <!--true -->
		
		${pageContext.request.contextPath }
		
		pageContext 로 할 수 있는것들
		1. 영역(scope) 제어
		2. 에러 데이터 확보 (throw execption)
		3. 페이지 이동(dispatche(forward,include)방식의 이동)
		<%
			String relativeUrlPath = "/04/localeDesc.jsp";
//			pageContext.forward(relativeUrlPath);
			pageContext.include(relativeUrlPath);  
			// 페이지를 모듈화 할 수 있다. 어느 지점에 include 되는 질  알 수 있기 떄문이다.
			// 하지만 이렇게 모듈화를 시키면 예외처리를 정확하게 하지 못하는 경우도 발생한다.
			
//			request.getRequestDispatcher(relativeUrlPath).include(request,response); /* 버퍼가 존재. autoflush */
		%>
		include 이후의 텍스트
				
	</pre>


</body>
</html>