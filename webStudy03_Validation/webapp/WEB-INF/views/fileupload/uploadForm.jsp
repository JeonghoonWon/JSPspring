<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" enctype="multipart/form-data">
	<!-- 파일이 업로드 되는 UI에서 반드시 맞춰줘야 하는것 두가지
	전송되는 영역이 경우에 따라 request line, request body
	파일이 업로드될때 request line 을 통해 불가능 
	line 을 구성하는 것은 url이기 때문에
	1. from method 를 post 로 해준다. body 를 통해 보내줘야 하기 때문에
	but, body 를 만든다고 해서 전송이 다 되는게 아니다.
	파일의 2진 데이터가 보내져야함.
	2. 그러기 위해서 enctype설정
		enctype="application/x-www-form-urlencoded" 에서 변경 필요
		maintype = application / subtype =urlencoded 라는 뜻 
		
		enctype="multipart/form-data"
		-> 여러개 파트로 form-data 로 전종
		멀티파트의 갯수를 정하는건 넘어가는 데이터의 갯수 (입력값)
		아래와 같은 경우 text 와 file 2개
		multipart는 하나의 영역을 여러개로 쪼개는것.
		boundary 를 구분해줘야한다. 
		
		이미지 데이터는 먼저 어디에 저장할지 정해야한다.
		미들티어와 DB티어?
		DB티어 의file 이 varchar2 이기 때문에 file 2진 데이터 저장 불가
		
		저장 할 수 있는 곳은 미들 티어
		컨트롤러에서 해야하는 일 
		 - 미들티어에 저장
		 - 메타 데이터 디비에 저장
		
		질문!!
		parameter 가 생성될 수 있을까??
		
		3.0 이상 에선 Part API 사용 가능. 
		
	  -->
		<input type = "text" name = "uploader" placeholder="업로더" />
		<input type = "file" name = "uploadFile1" accept="image/*"/> <!-- accept="image/* : 이미지만 입력 받을 수 있게 제한을 걸어둔다. -->
		<input type = "file" name = "uploadFile2" accept="image/*"/>
		<button type = "submit">업로드</button>
	</form> 
	
	<img src="<%=request.getContextPath() %><%=session.getAttribute("uploadFile1") %>">
	<img src="<%=request.getContextPath() %><%=session.getAttribute("uploadFile2") %>">
	<%
		session.removeAttribute("uploadFile1");
		session.removeAttribute("uploadFile2");
	%>
	
		<!-- 
		문제점 : 
		파일이 실제로 존재하는지 확인
		/ 무조건 이미지 파일의 형태로만 저장되는지?? 
		/ 원본파일명을 잡을때 getSubmiittedFileName()을 사용해서 하는데 servlet버전에 따라 없을수도 있음
		 -->
	
	
</body>
</html>