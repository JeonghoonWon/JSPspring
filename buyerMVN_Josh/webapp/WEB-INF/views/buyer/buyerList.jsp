<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Buyer List</title>
<jsp:include page="/includee/preScript.jsp" />

</head>
<body>
<h4>buyer list</h4>
	<form id = "searchForm">
	
		<select name = "buyer_lgu">
			<option value>거래처분류</option>	
				<%
		List<Map<String,Object>> lprodList = (List) request.getAttribute("lprodList");
		for(Map<String,Object> lprod : lprodList){
		%>
			<option value ="<%=lprod.get("lprod_gu") %>" >
				<%=lprod.get("lprod_nm") %>
			</option>
			
			<%
		}
			%>

				
		</select>
		
		
		<input type ="text" name ="buyer_name" />
		<input type ="text" name="page" />
		<input type ="submit" value = "검색" />
		<input type ="button" value ="신규등록" id ="newBtn" />
	
	</form>

	<table border = "1">
		<thead>
			<tr>
				<th>NO.</th>
				<th>거래처코드</th>
				<th>거래처명</th>
				<th>거래처분류명</th>
				<th>거래처은행</th>
				<th>거래처계좌번호</th>
				<th>거래처예금주</th>
				<th>거래처우편번호</th>
				<th>거래처주소1</th>
				<th>거래처주소2</th>
				<th>거래처전화번호</th>
				<th>거래처팩스</th>
				<th>거래처이메일</th>
				<th>거래처담당자</th>
				<th>거래처추가번호</th>
			</tr>
		<thead>
		
	<tbody id = "listBody">
	
	
	</tbody>
	
	<tfoot>
			<tr>
				<td colspan="15" id ="pagingArea">
				</td>
			</tr>
	</tfoot>
	
	</table> 

<script type="text/javascript" src="<%=request.getContextPath() %>/js/buyer/buyerList.js"></script>	
</body>
</html>