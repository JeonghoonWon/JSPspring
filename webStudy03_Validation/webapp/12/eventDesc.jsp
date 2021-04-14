<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/eventDesc.jsp</title>
</head>
<body>
<h4>서버사이스 이벤트 처리 방법</h4>
<pre>
	** 서버사이드 이벤트 종류
	 서버의 종료, 서버 시작, session생성 등등 서버 사이드의 이벤트 종류중 하나 이다.
	lifecycle event(init/destroy) : request, session, application  6개
	attribute event(add(저장)/remove(추가된게 없어짐)/change(추가된게 변경)) : request, session, application  9개  / 총 15개. 
	

	1. 이벤트 대상(target) 식별 : button, application	서버사이드에서 잡을 수 있는건 application
	2. 처리할 이벤트(event) 식별 : click	
	3. 처리할 내용을 가진 핸들러 구현 : function , listener
	4. 이벤트 대상에게 핸들러를 부착 : onclick. web.xml(listener) 
<!-- 	4번 <listener>
  	<listener-class>kr.or.ddit.listener.CustomServletContextListener</listener-class>
   </listener> -->
	
	/* EDD개발 방법론  */
	<button type="button" id="clickBtn">클릭</button>
	<script type="text/javascript">
		let button = document.getElementById("clickBtn");
		function clickHandler(){
			alert("클릭!");
		}
		button.onclick=clickHandler; 
		
	</script>

</pre>


</body>
</html>