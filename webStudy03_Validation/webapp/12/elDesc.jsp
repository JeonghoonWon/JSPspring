<%@page import="kr.or.ddit.utils.CookieUtils"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/ecDesc.jsp</title>
</head>
<body>
<h4>EL(Expression Language : 표현 언어)</h4>
<pre>
	: (속성)데이터를 표현(출력) 할 목적으로 정의된 스크립트 언어.
	
	1. 네개의 영역의 속성들을 표현할 때 사용 (****** 중요).
	2. 연산자 지원
		산술연산자 : +-*/%  ${2/3 },(결과:0.6666666666666666) ${"2"+"3" } (결과: 5), 연산자가 중심. 그래서 결과 5
					 ${"2"+3 }, (결과: 5),<%--  ${"a" + 3 } (연산 불가. 500error) --%>
					 <%  %> 연산의 결과가 문자. 연산의 중심이 되는건 피 연산자의 데이터 타입. 그래서 결과가 23
		논리연산자 : &&(and) , ||(or) , !(not), ^(xor)
					${true and true }, ${true and "true" }, \${false or 3 } <!-- \ escape  -->
		비교연산자 : >(gt), <(lt), >=(ge), <=(le),==(eq), !=(ne) 
					${not(4 le 3) }${4 gt 3 }
			
		삼항연산자 : 조건식? 참 : 거짓
		<%
			String test = "  ";/*  변수를 만든것. 속성을 만든게 아니다. */
			pageContext.setAttribute("asdf", test);
		%>
		
		단항연산자 : ++, -- el 안에선 사용 불가.  할당연산자(=) 이 없기 때문에 증감도 불가능 하다. 
					empty 어떤데이터가 비어있는지 아닌지 판단 . ${empty asdf }(true)
					empty 확인방법
					1) 속성의 존재 여부
					2) null 여부
					3) type check 
						String : length > 0 일 경우 true
						array : length > 0 ture
						collection : size > 0 true
						위 type check에 없으면 2번에서 끝남.
		
	3. (속성)자바 객체에 대한 접근 기능 지원
	<%
		MemberVO member = new MemberVO("a001","java");
		request.setAttribute("member",member);
	%>		
		${member1 } null문자는 화이트 스페이스로 변경된다. 
		${member } toString 으로 출력 되기 때문에 문자열로 출력됨.
		
		${member.getMem_id() } (id값 출력. a001)구버전에선 사용 불가. 
		${member.mem_id } 이것도 id 값 출력. a001
		
		${member["mem_id"] }, ${member.getTest() }, ${member.test } 자바 빈 규약을 역으로 적용해서 
	
	4. (속성) 집합객체에 대한 접근 기능 지원
	<%
		String[] array = new String[] {"value1","value2"};
		session.setAttribute("array",array);
		List<?> list = Arrays.asList("value1", "value2");
		application.setAttribute("sampleList", list);
		
		List list2 = Arrays.asList("tmp1","tmp2");
		request.setAttribute("sampleList", list2);
		
		Set<String> set = new HashSet<>();
		set.add("value1");
		set.add("value2");
		pageContext.setAttribute("sampleSet", set);
		
		Map<String,Object> map = new HashMap<>();
		map.put("key1", "mapValue1");
		map.put("key-2", new Date());
		session.setAttribute("sampleMap",map);
	%>
	${array[1] }, <%--=array[2] --%> ${array[2] }
	\${sampleList.get(3)},
	 ${sampleList[0] } ${sampleList[3] } 
	 ${pageScope.sampleSet }
	
	\${sampleList.get(3)} : 바로 리플랙션 되서 자바 코드로 변경 500error 예외발생 
	\${sampleList[3] } : 없는 배열의 값을 요구할 경우 화이트 스페이스로 표현 
	작은 영역에서 먼저 찾기 때문에 request 에 값이 있으니 application 의 value 대신 request 의 tmp 가 출력 된다.
	
	<c:forEach items="${pageScope.sampleSet }" var="element"
			 varStatus="vs">
	
		<c:if test="${not vs.last }" >
		${element }
		</c:if>
	</c:forEach>
	
	${sampleMap.get("key-2").getTime() }
	${sampleMap.key-2 } ,${sampleMap["key-2"]["time"] }
	\${sampleMap.key-2 } : el 은 연산기호가 우선되어서 -2 가 출력됨. key 는 null 값이 되고 null 은 0으로 판단 결과는 -2
	
	5. EL 의 기본 객체 지원
		scope : pageScope, requestScope, sessionScope, applicationScope
	 ${applicationScope.sampleList[0] }
	  \${applicationScope.sampleList[0] } :  어느 스코프에서 찾을지 알려준다. 더 빠르게 검색 가능.
		
		2. parameter : param (Map&lt;String,String&gt;) 
					   paramValues (Map&lt;String,String[]&gt;) 타입 Map 
		<a href="?param1=value1&param1=value2">파라미터전달</a>
				<%=request.getParameter("param1") %>
				이걸 다시 el 로
				${param.param1 }, ${param["param1"] } 로 param1값 뽑아낼수있다. 
				
				<%=request.getParameterValues("param1") %>
				이걸 다시 el 로
				${paramValues.param1 }, ${paramValues["param1"] }
		
		3. header : header(Map&lt;String,String&gt;) 
					headerValues(Map&lt;String,String[]&gt;) 
				user-agent값
				<%=request.getHeader("user-agent") %>
				${header.user-agent }, ${headerValues["user-agent"][0] }
		
		4. cookie : cookie (Map&lt;String,String&gt;) 
		<%=new CookieUtils(request).getCookie("JSESSIONID").getValue() %>
		${cookie.JSESSIONID.value } 	
		${cookie["JSESSIONID"]["value"] }	
		${pageContext.session.id }	
		
		5. context parameter : initParam (Map&lt;String,String&gt;)
		<%=application.getInitParameter("contentFolder") %>
		${initParam.contentFolder }
		${initParam["contentFolder"] }
		
		6.pageContext
		${pageContext.request.contextPath }
					
		</pre>

</body>
</html>