<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/implicitObject.jsp</title>
</head>
<body>
	<h4> 기본 객체 (내장 객체)</h4>
	<pre>
		: jsp 컨테이너에 의해 서블릿 소스가 파싱되는 단계에서 정의도는 지역 변수들
	
		
	1. request(HttpServletRequest) : 요청
	2. response(HttpServletResponse) : 응답
	3. out(JspWriter) 출력 스트림. : 응답데이터를 기록하거나 버퍼 제어에 사용. (response.getwriter)
	4. session(HttpSession) : 하나의 세션에 대한 정보를 가진 객체.
	5. application(ServletContext) : 서버에 대한 정보와 현재 어플리케이션에 대한 정보를 가진 객체. CAC
	6. config(ServletConfig) : 하나의 서블릿에 대한 메타 정보를 가진 객체.
	7. page(Object) : jsp 페이지의 인스턴스 자체, this 키워드로 대체 사용.
	8. exception(Throwable) : error를 처리할 목적이 있는 페이지에서만 사용 (isErrorPage="true")
							발생한 에러나, 예외에 대한 정보를 가진 객체
							 
	**** 9. pageContext(PageContext) : 가장 먼저 생성되는 기본객체이면서, 
										나머지 기본 객체에 대한 참조를 소유함.
										
	
	jsp 요청을 받으면 톰켓은 싱글톤 객체 가 있느지 확인한다.
	있다면 thread 를 분류 
	_jsp 호출
	
	없다면 최초 요청 인 것.
	servlet 진행 -> 커파일 -> 싱글톤 -> thread -> _jsp 호출
			
	</pre>
	


</body>
</html>