<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
 <%
 
 	
 %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<select id = "blood">
		<%
			Map<String,String> bloodMap  = (Map) application.getAttribute("bloodMap2");
			for(Entry<String,String> entry : bloodMap.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				%>
				<option value="<%=key%>"><%=value %></option>
				<%
			}
		
			
		%>
	
	
	</select>
	
	<script type="text/javascript">
		var select = document.querySelector("#blood");
		select.onchange = function(event) {
			// let : 변수 선언 키워드. 정확히 이 블럭 안에서만 사용할 수 있도록 제한을 둔다.
			let blood = event.target.value;
			location.href = "<%=request.getContextPath()%>/blood/getContent123.do?blood="+blood;
			// 위 주소로 보내겠다. get 방식
		}
	</script>



</body>
</html>