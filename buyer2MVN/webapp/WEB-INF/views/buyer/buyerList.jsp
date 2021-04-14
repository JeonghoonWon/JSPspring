<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.vo.PagingVO"%>
<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래처 리스트</title>
<jsp:include page="/includee/preScript.jsp"/>
</head>
<body>
<form id="searchForm">
	<select name="buyer_lgu">
		<option value>상품분류</option>
		<% 
			List<Map<String,Object>> lprodList 
				= (List)request.getAttribute("lprodList");
			for(Map<String,Object> lprod : lprodList){
				%>
				<option value="<%=lprod.get("lprod_gu")%>">
					<%=lprod.get("lprod_nm") %>
				</option>
				<%
			}
		%>
	</select>
	<input type="text" name="buyer_name"/>
	<input type="text" name="page"/>
	<input type="submit" value="검색" />
	<input type="button" value="신규등록" id="newBtn"/>
</form>
<table border="1">
	<thead>
		<tr>
			<th>No.</th>
			<th>거래처아이디</th>
			<th>거래처명</th>
			<th>거래처분류명</th>
			<th>거래처은행명</th> 
			<th>거래처은행계좌</th>
			<th>거래처담당자</th>
			<th>거래처우편번호</th>
			<th>거래처주소1</th>
			<th>거래처주소2</th>
			<th>거래처번호</th>
			<th>거래처팩스번호</th>
			<th>거래처메일</th>
			<th>거래처대표</th>
			<th>거래처 telext</th>
			<th>거래처 삭제여부</th>
		</tr>
	</thead>
	<tbody id="listBody">

	</tbody>
	<tfoot>
		<tr>
		<td colspan="16" id="pagingArea">
			
		</td>
	</tr>
	</tfoot>
</table>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/buyer/buyerList.js"></script>
<script type="text/javascript">
	$("#newBtn").on("click", function(){
		location.href="<%=request.getContextPath()%>/buyer/buyerInsert.do";
	})
</script>
</body>
</html>