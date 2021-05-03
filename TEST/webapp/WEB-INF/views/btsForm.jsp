<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BTS</title>
</head>
<body>
<h1>BTS</h1>
<form method ="post">
	<select id = "member" name ="member">
		<%
			Map<String,String> BTSMap  = (Map) application.getAttribute("BTSMap");
			for(Entry<String,String> entry : BTSMap.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				%>
				<option value="<%=key%>"><%=value %></option>
				<%
			}
		%>
	</select>
		
	</form>
	
		<script type="text/javascript">
		// 
		var select = document.querySelector("#member"); // select id 값 
		
		select.onchange = function(event) {
			
			this.form.submit();
			// let : 변수 선언 키워드. 정확히 이 블럭 안에서만 사용할 수 있도록 제한을 둔다.
			//let member = event.target.value;
			
			
			// 위 주소로 보내겠다. get 방식   /webStudy01/webapps/WEB-INF/views/bts/bui.jsp
		}
	</script>

</body>
</html>