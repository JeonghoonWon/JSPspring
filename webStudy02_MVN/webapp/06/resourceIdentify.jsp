<%@page import="java.nio.file.StandardCopyOption"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Path"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>자원의 식별</h4>
	
	<pre>
		자원의 종류
		1. File System Resource : d:\contents\coffee1.jpg
		2. Class Path Resource : 
		kr\or\ddit\servlet01\ImageFormServlet.class
		<!-- file system resource 와 class path resource 의차이 : 경로를 적는 방식  -->
		3. Web Resource :
		/images/Coca-Cola-logo.png
		<!-- 변경 가능한 부분 : http://localhost/webStudy01 -->
		<%
			String imageURL = "/images/Coca-Cola-logo.png";
			String folderURL = "/06";
			File folder = new File(application.getRealPath(folderURL));
			File saveFile = new File(folder,"Coca-Cola-logo.png");
			Path savePath = Paths.get(saveFile.getAbsolutePath());
			out.println(savePath);
			
			/* URL url = application.getResource(imageURL);
			out.println(url); */
		try(
				InputStream is = application.getResourceAsStream(imageURL);
			//	FileOutputStream fos = new FileOutputStream(saveFile);
				
				// 아직 종료되지않았는데 아래 Files.copy 가 되기 때문에 500 error  //	FileOutputStream fos = new FileOutputStream(saveFile); 주석처리하고 실행
			){
				Files.copy(is, savePath, StandardCopyOption.REPLACE_EXISTING);
			} 
		%>
	
	</pre>
	<img src="<%=request.getContextPath() + folderURL+"/"+saveFile.getName() %>" />
</body>
</html>