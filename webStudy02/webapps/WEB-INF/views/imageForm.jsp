 <%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.FilenameFilter"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<haad>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
		//$(document).ready(function(){
			
		 //});
		$(function(){
			const SRCPTRN = "%A?%N=%V";
			console.log($("form")[0]);
			const action = $("form")[0].action;
//			console.log($("#image"));
			let select = $("#image").on("change", function(event){
				$("#imageArea").empty();
//				console.log(this.value);
//				console.log($(this).val());
//				this.form.submit();
				// img 태그 생성
				var name = this.name;
				var values = $(this).val();
				var imgs = [];
				$(values).each(function(idx, value){
					var src = SRCPTRN.replace("%A", action)
									 .replace("%N", name)
									 .replace("%V", value);
					var img = $("<img>").attr("src", src);
					imgs.push(img);
				});
				// imageArea에 img 태그를 innerHTML로 삽입
				$("#imageArea").append(imgs);
				$.ajax({
					url : "<%=request.getContextPath()%>/07/cookieGenerate.do"
					, method : "post"
					, contentType : "applcation/json;sharset=UTF-8"
					, data : JSON.stringify(values) 
				
				});
		
			}); // change handler end
			<%
		 		String imageName = (String)request.getAttribute("imageCookie");
				if(imageName !=null){
			%>
<%-- 				alert(JSON.parse('<%=imageName%>')); --%>
				select.val(JSON.parse('<%=imageName%>'));
				select.trigger("change");
			<%
				}
			%>
		}); //ready end
	</script>
</haad>
<body>


<h4><%=new Date() %></h4>
<form action="<%=request.getContextPath()%>/01/image.do" method="post"> <!-- 상대경로 표기방식 -> 현재위치에서 판단 -->
<input name="_method" value="put" type="hidden" />
	<%
		String[] children = (String[])request.getAttribute("children");
		StringBuffer options = new StringBuffer();
		
		for(String child : children){
			options.append(String.format("<option>%s</option>", child));
		}
	%>
	<select name="image" id="image" multiple>
		<%=options %>
	</select>
	<input type="submit" value="전송" style="background-color : red;"/>
</form>
<div id="imageArea">
	<!-- 쿠키 이미지 출력 -->
	<%
		Cookie[] cookies = request.getCookies();
		Cookie searched = null;
		if(cookies != null){
			for(Cookie tmp : cookies){
				String value = URLDecoder.decode(tmp.getValue(), "utf-8");
				
			}
		}
	%>
</div>

</body>
</html>

<!-- tmpl은 진짜 객체가 아니고 imageFormSevlet이 진짜 객체이다. -->
<!-- imageFormServlet의 @WebServlet("/01/imageForm.tmpl")가 없으면 tmpl이 직접 요청을 받아들인다. -->
