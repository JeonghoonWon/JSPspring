<%@page import="java.util.Date"%>
<%@ page import="java.util.TimeZone"%>
<%@ page import= "java.util.Locale"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.ddit.or.kr" prefix="my" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/worldTime.jsp</title>
</head>
<body>
	<pre>
		1. java의 모든 timezone 을 select 로 선택 가능 하도록
		2. 선택한 시간대에 맞춰 시간  출력
		3. java의 모든 locale 을 select 로 선택 가능 하도록 
		4. 선택한 locale 에 맞춰 출력 메시지 형식 결정
	</pre>

	<h4>selcet timezone</h4>

<form>
	<select name="loc">
		<c:forEach items="${Locale.getAvailableLocales() }" var="tmp">
			<option value="${tmp.toLanguageTag() }">
				${tmp.displayLanguage}[${tmp.displayCountry }]</option>
		</c:forEach>
	</select> 
	
 
	<select name="zone">
	
		<c:forEach items="${my:getAvailableIDs() }" var="tz">
			<option value="${tz }">${my:getTimeZone(tz).displayName }</option>
		</c:forEach>
	</select>
	
	</form>
	
	<%=new Date() %>
	
	<c:set var="now" value="${my:getNow() }"></c:set>

	<fmt:setLocale value="${not empty param.loc ? param.loc : pageContext.request.locale }"/>
	<c:choose>
		<c:when test="${empty param.zone }">
			<c:set var="timeZone" value="${my:getDefaultTimeZone() }"/>
		</c:when>	
		<c:otherwise>
			<c:set var="timeZone" value="${my:getTimeZone(param.zone) }"/>
		</c:otherwise>
	</c:choose>
	<fmt:formatDate value="${now }" type="both" timeZone="${timeZone }"/>
	
	<script type ="text/javascript" >
		function changeHandler(event) { // event를 파라미터로 받을 수 있다.
			// select box 에 선택된 값
			let select =  event.target; //this
			select.form.submit();
		}
		let selects = document.getElementsByTagName("select");
		for(let i = 0; i<selects.length; i++){
			selects[i].onchange=changeHandler;
		}
		
		document.forms[0].loc.value="${param.loc }";	// loc 를 내임값으로 가지고있는 select가 있으면 loc를 넣어라.
		document.forms[0].zone.value="${param.zone }";	// zone 를 내임값으로 가지고있는 select가 있으면 zone 을 넣어라.
		</script>
	
	

	









	<!-- 	<select onchange="location.href='?zone='+this.value;"> -->
	<%--     	<c:forEach var="zone" items="<%=TimeZone.getAvailableIDs() %>"> --%>
	<%--         	<option value="${zone}">${zone}</option> --%>
	<%--     	</c:forEach> --%>
	<!-- 	</select> -->

	<%-- 		<c:if test="${empty param.zone }"> --%>
	<%-- 				<c:set var="zone" value="<%=TimeZone.getDefault() %>"></c:set> --%>
	<%-- 			</c:if> --%>
	<%-- 			<c:if test="${not empty param.zone }"> --%>
	<%-- 				<c:set var="zone" value="${param.zone}" /> --%>
	<%-- 			</c:if> --%>

	<%-- 	<fmt:setTimeZone value="${zone}"/> --%>

	<%-- 		<fmt:formatDate value="<%=new Date() %>" type="both" dateStyle="short" var="datestr" /> --%>
	<%-- 			${datestr } --%>
	<%-- 			<fmt:parseDate value="${datestr }" type="both" dateStyle="short" var="dateObj"></fmt:parseDate> --%>
	<%-- 			${dateObj.time } --%>

	<!-- 	<select onchange="location.href='?lang='+this.value;"> -->
	<%-- <c:forEach items="${Locale.getAvailableLocales() }" var="tmp"> --%>
	<%-- 		<option value="${tmp.toLanguageTag() }"> --%>
	<%-- 		${tmp.displayLanguage}${tmp.displayCountry } --%>
	<!-- 		</option> -->
	<%-- </c:forEach> --%>
	<!-- 	</select> -->

	<%-- 	<c:if test="${empty param.lang }"> --%>
	<%-- 				<c:set var="locale" value="${pageContext.request.locale }"></c:set> --%>
	<%-- 			</c:if> --%>
	<%-- 			<c:if test="${not empty param.lang }"> --%>
	<%-- 				<c:set var="locale" value="${param.lang}" /> --%>
	<%-- 			</c:if> --%>
	<%-- 			<fmt:setLocale value="${locale }"/> --%>
	<%-- 				<fmt:bundle basename ="kr.or.ddit.messages.message"> --%>
	<%-- 				<fmt:message key="bow" /> --%>
	<%-- 			</fmt:bundle> --%>

</body>
</html>