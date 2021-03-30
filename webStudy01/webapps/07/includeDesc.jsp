<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>07/includeDesc.jsp</title>
</head>
<body>
	<h4>내포(include)의 종류</h4>
<pre>
	언제 include 되느냐, 무엇을 include 하느냐.
	1. 동적 include : runtime 에 실행 결과를 내포함.
			: RequestDispatcher.include
			: pageContext.include
			: include 액션 태그를 사용.
			<jsp:include page="/04/localeDesc.jsp"/>
			커스텀 태그 : 개발자가 필요에 의해 새로 정의한 태그
			&lt;prefix:tagname attribute /&gt;
	
	application이 실행
	2. 정적 include : runtime이전, comfile 이 되기 전 소스가 파싱될때, 소스 자체를 내포함.
		<%-- <%@ include file="/04/localeDesc.jsp" %>  --%>
		<%--=locale --%>

정적과 동적을 구분하기 위해선 최종 소스르 봐야한다.
커스텀코드는 자바 코드를 작성하는 또하나의 방법이다.
이 코드가 실행 되어야만 

</pre>


</body>
</html>