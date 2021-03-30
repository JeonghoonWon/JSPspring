<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>BTS</title>
<jsp:include page = "/includee/preScript.jsp" />
</head>
<body>
<h1>BTS_SEM</h1>
<form method="post">
	<input type = "text" name = "test" />
	<%
		Map<String, List<String>> btsMap = (Map) application.getAttribute("btsMap");
	%>
	<select name="member" onchange="$(this.form).submit();">
	<%
		for(Entry<String,List<String>> entry : btsMap.entrySet()){
			String id = entry.getKey();
			String name = entry.getValue().get(0);
			%>
			<option value="<%=id %>"><%=name %></option>
			<%
		}
	%>
	</select>
</form>
<div id = "resultArea"></div>

<script type="text/javascript">
// 	$.test();
	console.log();
 		let resultArea = $("#resultArea");
	// 동기를 비동기로 바꿀때 플러그인으로 만들어둔 것 사용 가능.
	$("form").test2().formToAjax({
		dataType:"html"
		,success:function(resp){
			resultArea.html(resp);
		}
		
	});
</script>

</body>
</html>








