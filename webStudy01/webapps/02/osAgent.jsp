<%@page import="kr.or.ddit.enumpkg.OSType"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>02/osAgent.jsp</title>
</head>
<body>
<h4>OS Agent</h4>
<!-- 
클라이언트의 브라우저를 확인하고,
iexplore 라면 "당신의 브라우저는 익스플로러 입니다."
chrome 이면 "당신의 브라우저는 크롬입니다."
edge 이면 "당신의 브라우저는 엣지입니다."
라는 메시지를 alert 창으로 띄울것. --> 

<%	

	
 	String agent = request.getHeader("user-agent");
 	System.out.println(agent);
 	
	// 대문자로 변경. 검사해야 하는 문자 중 소문자, 대문자 섞여있을수 있기 때문에

	String MSGPTRN = "당신의 OS는 %s 입니다.";
	String message = null;
/*			String name = browserMap.get("OTHER");
			
	for(Entry<String,String> entry : browserMap.entrySet()){
		String ketWord = entry.getKey();
		 if(agent.contains(ketWord)){
			name = entry.getValue();
			break;
		}
	} 
	
	*/
	
	message = String.format(MSGPTRN, OSType.getOsName(agent));
	// for 문 밖으로 나온 경우 익스플로러, 크롬, 엣지가 아닌 경우이다.
	// 
	
	/* if(agent.contains("EDG")){
		message = String.format(MSGPTRN,"엣지");	
	}else if(agent.contains("CHROME")){
		message = String.format(MSGPTRN,"크롬");
	}else if(agent.contains("TRIDENT")){
		message = String.format(MSGPTRN,"익스플로러");
	}else{
		message = String.format(MSGPTRN,"기타 등등");
	} */

%>

</body>
<script type="text/javascript">
	alert("<%=message %>");

/* 	var agent = navigator.userAgent.toLowerCase();

if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {

  alert("인터넷 익스플로러 브라우저 입니다.");
  

}
if (agent.indexOf("chrome") != -1) {

	  alert("크롬 브라우저입니다.");

	}
if (agent.indexOf("Edge") != -1) {

	  alert("엣지 브라우저입니다.");

	} */			
</script>
</html>