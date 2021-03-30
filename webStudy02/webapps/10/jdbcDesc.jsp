<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/jdbcDesc.jsp</title>
<style>
table {font
	
}
</style>
</head>
<body>
	<h4>JDBC(Java DataBase Connectivity)</h4>
	<pre>
	Facade 패턴 활용
	
	1. Driver 를 찾아서, 빌드 패스에 추가
	2. Driver 로딩(oracle.jdbc.driver.OracleDriver)
	3. Connection 생성
	4. 쿼리 객체 생성
		Statement
		Preparedstatement (선컴파일된 쿼리 객체)
		Callablestatement (Prosedure/Function 일련의 코드 집합을 호출)
	5. 쿼리 실행 : DML(select/insert,update,delete)
		ResultSet executeQuery
		executeUpdate
	6. 자원 해제(close)
	
	<%
		
		String[] headers = null;
		List<Map<String, Object>> dataList = new ArrayList<>();
	
		try (
			Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();) {
			
			String sql = " select property_name, property_value, description " + " from database_properties ";
			
			ResultSet rs = stmt.executeQuery(sql); // 결과의 집합이기 때문에 인덱스가 없다.
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			headers = new String[count];
			for (int i = 1; i <= count; i++) {
				headers[i - 1] = rsmd.getColumnName(i);
			}

			
			while (rs.next()) {
				Map<String, Object> record = new HashMap<>();
				dataList.add(record);
				for (String colName : headers) {
					record.put(colName, rs.getString(colName));
				}
			}
		}
	%>
	 </pre>

	<table border="1">
		<thead>
			<tr>
				<%
					for (String header : headers) {
				%>
					<th><%=header%></th>
				<%
					}
				%>
			</tr>
		</thead>
		<tbody>
			<%
				for (Map<String, Object> record : dataList) {
			%>
			<tr>
				<%
					for (Entry<String, Object> entry : record.entrySet()) {
				%>
					<td><%=entry.getValue()%></td>
				<%
					}
				%>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>




</body>
</html>