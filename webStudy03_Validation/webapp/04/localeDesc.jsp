<%@page import="java.util.Calendar"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>04.localeDesc.jsp</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h4>Locale</h4>
	<pre>
		: 언어나 지역이나 문화적 특성들을 캡슐화한 객체
		locale text : 언어코드 _국가 (지역)코드
		<%= request.getHeader("accept-language") %>
		<%
			Locale locale = request.getLocale();
			out.println(locale.getDisplayLanguage());	// 보여줄수있는 언어를 나타냄.
			out.println(locale.getDisplayCountry());	// 국가정보
			
			
		%>	
	</pre>
	<select>
		<option disabled selected>국가선택</option>
<%
Locale[] locales = Locale.getAvailableLocales();
for(Locale tmp : locales) {
	%>
	
	<option><%=tmp.getDisplayLanguage() %> </option>
	
	<%
}
%>
</select>

<% 
String locParam = request.getParameter("loc");
Locale selectedLocale = request.getLocale();
if(locParam !=null && !locParam.isEmpty()){
	 selectedLocale = Locale.forLanguageTag(locParam);
}
Calendar cal = Calendar.getInstance();
out.println(String.format(selectedLocale,"%tc",cal));
 %>
 

 <form>
 <select name="loc">
 

 	<%
 		for(Locale tmp : Locale.getAvailableLocales()){
 			String dL = tmp.getDisplayLanguage(tmp);
 			String dc = tmp.getDisplayCountry(tmp);
 			if(dL.isEmpty() && dc.isEmpty()) continue;
 			String selected = tmp.equals(selectedLocale) ? "selected":"";
 			%>
 				<option <%=selected%> value="<%=tmp.toLanguageTag() %>"><%=String.format("%s[%s]",dL,dc) %></option>

 			<%
 		}
 	%>
 </select>
 </form>
 <script type="text/javascript">
 	$("[name='loc']").on("change",function(event){
 		this.form.submit();
 		
 		
 	});
 	
 
 </script>
 

</body>
</html>