<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	

<c:if test="${not empty message }">
	<script type="text/javascript">
			alert("${message }");
			
		</script>
		<c:remove var="message" scope="session"/>
		
</c:if>



</head>
<body>

<form method="post">
	<input type ="text" name = "mem_pass" required />
	<input type = "submit" value = "인증" />
</form>
