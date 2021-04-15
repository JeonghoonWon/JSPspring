<%@page import="java.util.Date"%>
<%@ page import= "java.util.Locale"%>
<%@ page import= "java.util.TimeZone"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/jstlDesc.jsp</title>
<style>
	.green{
		background-color: green;
	}
	.blue{
		background-color: blue;
	}
</style>
</head>
<body>
<h4>JSTL(Jsp Standard Tag Library)</h4>
	<select onchange="location.href='?lang='+this.value;">
<c:forEach items="${Locale.getAvailableLocales() }" var="tmp">
		<option value="${tmp.toLanguageTag() }">
		${tmp.displayLanguage}${tmp.displayCountry }
		</option>
</c:forEach>
	</select>


<pre>
	: 커스텀 태그 라이브러리 (server side / 일반적으로 사용하는 태그와는 조금 다르다. 앞에 prefix:가 붙는거 빼곤 기존 태그와 동일)
	<prefix:tagname arrtibutes />
------------------------------------------------------------------------------------
	1. core
		1) EL 변수 (속성) 지원 : set, remove
		속성의 이름(var), 값(value), 어디에(scope기본적으로 가장작은 page scope 에 저장) 넣을지를 먼저 정해줘야한다.
		<c:set var="test" value="테스트" scope="request" />
		속성 출력 : EL
		
		\${requestScope.test } = ${requestScope.test }, 
		\${requestScope["test"] } = ${requestScope["test"] } 
		
		속성 지우기 (속성을 지울땐 scope를 지정해줘서 원하는 것만 지워야한다. 다른게 지워지지않도록.)
		<c:remove var="test" scope="request" /> 
		\${requestScope.test } = ${requestScope.test }
		\${requestScope["test"] } = ${requestScope["test"] } 
------------------------------------------------------------------------------------
		
		2) 흐름 제어 : 
			조건문 : if, choose~when~otherwise
			<c:if test="${ empty test }">
				test 없음
			</c:if>
			다중조건문 
			<c:choose>
				<c:when test="${empty test }">없다</c:when>
				<c:when test="${not empty test }">있다</c:when>
			</c:choose>
------------------------------------------------------------------------------------
			3항 연산자
			${empty test ? "없다" :"있다" }
------------------------------------------------------------------------------------
		
			반복분 : foreach (사용방법 두가지 . 일반 for문 과 향상된 for문 형태로 사용 가능), forTokens
			향상된 for문 구조			
			for(block variable : collection) : items, var(블럭 속성을 입력)
			일반 for문 구조
			for(선언절;조건절;증감절) : var, begin, end, step(>0, 1 생략 가능)
			LoopTagStatus 프로퍼티 : index(var라고 선언해둔 증가되는 값), count(몇번째인지 확인), first, last
			token : ㅁ누장의 구성요소중 의미를 부여할 수 있는 최소 단위
------------------------------------------------------------------------------------
			<c:forTokens items="아버지 가방에 들어가신다" delims=" " var="token">
				${token }
			</c:forTokens>
------------------------------------------------------------------------------------
			
			<c:forTokens items="100,200,300" delims="," var="number">
				${number * 3 }
			</c:forTokens>
------------------------------------------------------------------------------------
			
			for(int i = 1; i&lt;3; i++) 
			<c:forEach var="i" begin="1" end="3" step="1">
				${i }
			</c:forEach>
			
------------------------------------------------------------------------------------
			
			<c:forEach var="i" begin="1" end="5" step="2" varStatus="vs">
				${i } - ${vs.count }번째 반복, ${vs.first }, ${vs.last }
			</c:forEach>
			
------------------------------------------------------------------------------------

		3) URL 재처리 : url
		<c:url var="memberList" value="/member/memberList.do" >
			<c:param name="page" value="2"></c:param>		
		</c:url>
		<a href="${memberList }?page=2">회원목록의 2페이지</a>
		${memberList }
		
------------------------------------------------------------------------------------

<!-- 		4) 기타 : import, out -->
<!-- 			import를 하면 대상의 제한이 없다. => 모든 사이트의 응답데이터를 다 가져옴. -->
<%-- 			<c:import url="https://www.naver.com" var="naver" /> --%>
<!-- 			out : html tag를 출력 (소스출력) -->
<!-- 			escapeXml="false" : 소스 출력 대신 페이지가 출력된다. -->
<%-- 			<c:out value="${naver }" escapeXml="false"></c:out> --%>
------------------------------------------------------------------------------------

	2. fmt (locale 지원/ 크게 두가지 처리를 지원)
		1) 언어처리 : setLocale, message
			안녕하세요. Hello
			1. 언어 결정
			2. 각 언어에서 사용할 수 있는 메시지 번들 작성(properties)
			3. locale 결정
			<c:if test="${empty param.lang }">
				<c:set var="locale" value="${pageContext.request.locale }"></c:set>
			</c:if>
			<c:if test="${not empty param.lang }">
				<c:set var="locale" value="${param.lang}" />
			</c:if>
			<fmt:setLocale value="${locale }"/>
			4. 메시지 출력 : 번들 로딩, 메시지 사용.
			<fmt:bundle basename ="kr.or.ddit.messages.message">
				<fmt:message key="bow" />
			</fmt:bundle>
		2) 메시지 형식 처리 
			parsing : parseNumber(문자를 숫자로), parseDate(문자를 날짜데이터로)
			formatting : formatNumber(숫자를 문자로), formatDate(날짜를 일정 형식의 문자로)
<%-- 			<fmt:setLocale value ="en_US" /> --%>
			메시지를 parsing / formatting 을 하려면 언어와 지역 정보가 필요하다. 
			
------------------------------------------------------------------------------------
			
			<fmt:formatNumber value="30000" type="currency" var="money"/>
			<fmt:parseNumber value="${money }" type="currency" var="number" />
			${number* 10 }
			
------------------------------------------------------------------------------------
			
			<fmt:formatDate value="<%=new Date() %>" type="both" dateStyle="short" var="datestr" />
			${datestr }
			<fmt:parseDate value="${datestr }" type="both" dateStyle="short" var="dateObj"></fmt:parseDate>
			${dateObj.time }
			
			
------------------------------------------------------------------------------------
		
	3. fn
		${fn:indexOf("abc","a") }
		<c:set var="array" value='${fn:split("test1,test2",",") }'></c:set>
		${fn:join(array, ",") }
		
------------------------------------------------------------------------------------
		
</pre>
<!--2_9 단까지 구구단 출력 -->

<h1>구구단</h1>
<table>
<c:forEach var="dan" begin="2" end="9" varStatus="vs1" step="2">
   <tr class="${vs1.count eq 3 ? 'green':'normal'}">
      <c:forEach var="mul" begin="1" end="9" varStatus="vs2">
         <c:choose>
            <c:when test="${vs2.first or vs2.last}">
               <c:set var="clz" value="blue"/>
            </c:when>
            <c:otherwise>
               <c:set var="clz" value="normal"/>
            </c:otherwise>
         </c:choose>
         <td class="${clz}">${dan}*${mul}=${dan*mul}</td>
      </c:forEach>
   </tr>
</c:forEach>
</table>
	


</body>
</html>