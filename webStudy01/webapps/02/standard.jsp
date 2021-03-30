<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>02/standard.jsp</title>
</head>
<body>
<h4><%= new Date() %></h4>
<h2>JSP(Java Server Page)</h2>
<pre>
	: 동적 웹 페이지 생성을 위한 서버사이드 스크립트 언어로 템플릿 구조를 완성하기 위해 사용되며,
		서블릿 스펙을 기반으로 동작함.
		
	구성요소
	1. 정적 텍스트
	2. scriptlet (동적) 
	<% 
		//자바코드 : 차후 서블릿 소스가 파싱될때 _jspService 메소드의 지역 코드화가 됨.
		Date today = new Date();
		// static String test = "";  : class 변수를 사용하려고 했기때문에 불가능 함.
		test2();
		
	// scriptlet 안에서 사용하는 변수는 전부 지역변수이다.
	// java 에서 블록변수 < 지역변수 < instance변수(전역변수) < class 변수(static)
	
	%> 
	scriptlet 기호 (교제 37p)
		1) 지시자(directive) &lt;%@ // 지시자명 , 속성들 %&gt; 환경에 대한 정보를 세팅할때 지시자 사용
				: 현재 jsp 페이지에 댓한 환경 정보 meta 정보 설정에 사용.
				page (기본) : page에 대한 기본 환경 정보 설정
				include (옵션) : 정적 내포에 사용 
				taglib (옵션) : 커스텀 태그 로딩에 사용 
	 	2) 표현식(expression)&lt;%= //출력하고 싶은 값이나 변수 %&gt; 
	 	3) 선언부(declaration)&lt;%!// 정적 변수나 메소드의 선언 %&gt;
	 		// 웹 어플리케이션에서 scope 사용 이유 ? 데이터 공유를 위해서
	 		<%!
	 			public String test = "정적 데이터";
	 			public void test2() {
	 				System.out.println(test);
	 				
	 			}
	 		%> 
	 	4) 주석(comment)
	 	<!--html 주석 :client  -->
	 	<script type="text/javascript">
	 		// 주석 : sourse 가 포함 되는지 안되는지에 따라 client 와 server 로 나뉜다.
	 	</script>
	 	
	 	<style>
	 		/* 주석  : client  */ 
	 		/* 브라우져 상에는 안나오지만 소스보기에서 확인이 됨. client 주석은 사용 지양하는게 좋다. 메모리 사용면, 비공개 정보가 공개 될 가능성 이 있다. */
	 	</style>
	 	
	 	<%-- 주석 : jsp script 주석--%>
	 	
	 	<%
	 	// 주석 : server
	 	/* 주석 : server */
	 	%>
	 		jsp는 다양한 언어를 사용한다.
	 		- client side comment : html, javascript, css
	 		- server side comment : jsp,java
	 		다양한 주석을 사용할 수 있으나 client 에게 공개 가 된다.
	 		jsp, java 주석은 server 주석이기때문에 이걸 사용하는게 좋다.
	 		
	 	3. 기본객체
	 	4. action 태그
	 	5. EL(표현언어)
	 	6. JSTL()	
	 		
	 	
</pre>

</body>
</html>