<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/sourceView.jsp</title>
</head>
<body>
<c:set var="importURL" value="${param.importURL }" />
<c:set var="source" value ="${param.source }" />

<form>
	<input type="url" name = "importURL" 
			placeholder="https://www.naver.com" value="${importURL }" />
	
	<label> 
	<input type="checkbox" name="source" value="true" 
		${not empty source and source ? "checked":"" }
	/> 소스보기 </label>
	<input type="submit" value="가져오기" />
</form>

<div>
<c:if test="${not empty param.importURL }">
	<c:import url="${param.importURL }" var="url" />
	<c:choose>
		<c:when test="${param.source eq true }">
			<c:out value ="${url }" />
		</c:when>
		<c:otherwise>
			<c:out value ="${url }" escapeXml="false" />
		</c:otherwise>
	</c:choose>
	
	
	
	</c:if>
</div>
</body>
</html>