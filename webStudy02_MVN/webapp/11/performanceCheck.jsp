<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>성능 체크</h4>
<pre>
	소요(반응)시간 = processing time + latency time
	<a href="oneConnOneProcess.jsp">한번 연결하고 한번 처리한 소요시간</a> : 8ms
	<a href="100Conn100Process.jsp">100번 연결하고 100번 처리한 소요시간</a> : 568ms
	<a href="oneConn100Process.jsp">100번 연결하고 100번 처리한 소요시간</a> : 11ms
	<!-- latency time이 소요 시간에 대부분을 차지한다. 소요시간을 줄이려면 latency time을 조절한다. -->
	Pooling 후
	<a href="oneConnOneProcess.jsp">한번 연결하고 한번 처리한 소요시간 : 0ms</a>
	<a href="100Conn100Process.jsp">백번 연결하고 백번 처리한 소요시간 : 17ms</a>
	<a href="oneConn100Process.jsp">한번 연결하고 백번 처리한 소요시간 : 10ms</a>
	
</pre>


</body>
</html>