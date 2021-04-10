<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Gugudan JSP</title>
<style>
	th,td{
		border : 1px solid black;
	}
	table {
		border-collapse: collapse;
		text-align: center;
	}
	.red{
		background-color: red;
	}
	.green{
		background-color: green;
	}
</style>
</head>
<body>
<%!
	private String gugudanText(int dan, int mul, String clz){
		return String.format("<td class='%s'>%d*%d=%d</td>",clz,dan,mul,(dan*mul)); 		
}
%>
<!-- 2단 부터 9단 까지의 구구단을 table 태그를 이용하여 출력  -->
<!-- 하나의 row에 하나의 단이 출력 되도록  -->
<%
	
	String minDanstr = request.getParameter("minDan");
	String maxDanstr = request.getParameter("maxDan");
	
	int minDan = 2;
	int maxDan = 9;
	/* 입력 데이터 검증 과정 */
	if(minDanstr!=null && minDanstr.matches("[2-9]")){
	minDan =Integer.parseInt(minDanstr);
		
	}
	if(maxDanstr!=null && maxDanstr.matches("[2-9]")){
	maxDan =Integer.parseInt(maxDanstr);
	}
	/* 문자열 일정한 형식으로 출력 : String.format()  
	   문자열 형변환 : parsing 
	  
	   
	   */
		
%>


<form>
	<input type = "number" placeholder="최소단" name ="minDan" min="2" max = "9" value="<%=minDan %>">
	<select name= "maxDan" required>
		
		<option value>최대단</option>
		<%
			String OPTPTRN = "<option %2$s value='%1$d'>%1$d단</option>";
			StringBuffer options = new StringBuffer();
			for(int dan=2; dan<=9; dan++){
				
				options.append(
					String.format(OPTPTRN, dan, dan==maxDan?"selected":"")		
				);
			}
			out.println(options);
		%>
		
	</select>
	<input type = "submit" value="전송" />
	
</form>

<table>
	<% 	
		int rowcnt = 1;
	
		for(int dan = minDan; dan<= maxDan; dan++){
			String clz = "nomal";
			if(rowcnt++ == 3){
				clz = "red";
			}
			out.println(String.format("<tr class='%s'>",clz));
			for(int mul=1;mul<=9;mul++){
				if(mul == 4){
					clz = "green";
				}else clz = "nomal";
				out.println(
						gugudanText(dan,mul,clz)
				);
			
			}
			out.println("<tr>");
		}
	%>


</table>


</body>
</html>