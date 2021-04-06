<%@page import="kr.or.ddit.vo.PagingVO"%>
<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<table border = "1">
	<thead>
		<tr>
			<th>상품코드</th>
			<th>상품분류명</th>
			<th>상품명</th>
			<th>거래처명</th>
			<th>구매가</th>
			<th>판매가</th>
			<th>마일리지</th>
		</tr>
	</thead>

<tbody>

<%
 PagingVO<ProdVO> pagingVO = (PagingVO) request.getAttribute("pagingVO");
 List<ProdVO> prodList = pagingVO.getDataList();
	if(prodList.size() > 0) {
		for(ProdVO prod : prodList){
			
		%>
		<tr>
			<td><%=prod.getProd_id() %></td>
			<td><%=prod.getLprod_nm() %></td>
			<td><%=prod.getProd_name() %></td>
			<td><%=prod.getProd_buyer() %></td>
			<td><%=prod.getProd_cost() %></td>
			<td><%=prod.getProd_price() %></td>
			<td><%=prod.getProd_mileage() %></td>
		</tr>
		<%
		}
	}else{
		
		%>
		<tr>
			<td colspan = "7">
			상품없음
			</td>
		</tr>
		
		<%
	}
%>

</tbody>

<tfoot>
		<tr>
			<td colspan="6">
				<%=pagingVO.getPagingHTML() %>
			</td>
		</tr>
	</tfoot>


</table>
</body>
</html>