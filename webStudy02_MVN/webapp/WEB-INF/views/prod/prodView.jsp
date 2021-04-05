<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	ProdVO prod =(ProdVO) request.getAttribute("prod");
	
%>
<table border ="1">
		<tr><th>상품코드</th><td><%=prod.getProd_id() %></td></tr>
		<tr><th>상품명</th><td><%=prod.getProd_name() %></td></tr>
		<tr><th>상품분류</th><td><%=prod.getProd_lgu() %></td></tr>
		
		
	<tr>
		<th>거래처 정보</th>
		<td>
			<table border ="1">
				<thead>
					<tr>
						<th>거래처명</th>
						<th>담당자명</th>
						<th>연락처</th>
						<th>주소1</th>
					</tr>
					<tr>
						<td><%=prod.getBuyer().getBuyer_name() %></td>
						<td><%=prod.getBuyer().getBuyer_charger() %></td>
						<td><%=prod.getBuyer().getBuyer_comtel() %></td>
						<td><%=prod.getBuyer().getBuyer_add1() %></td>
					</tr>
				</thead>
			</table>
		</td>
	</tr>
	
		<tr><th>구매가</th><td><%=prod.getProd_cost() %></td></tr>
		<tr><th>판매가</th><td><%=prod.getProd_price() %></td></tr>
		<tr><th>할인가</th><td><%=prod.getProd_sale() %></td></tr>
		<tr><th>상품설명요약</th><td><%=prod.getProd_outline() %></td></tr>
		<tr><th>상품상세설명</th><td><%=prod.getProd_detail() %></td></tr>
		<tr><th>사진</th><td><%=prod.getProd_img() %></td></tr>
		<tr><th>총재고량</th><td><%=prod.getProd_totalstock() %></td></tr>
		<tr><th>입고날짜</th><td><%=prod.getProd_insdate() %></td></tr>
		<tr><th>입고전재고량</th><td><%=prod.getProd_properstock() %></td></tr>
		<tr><th>상품크기</th><td><%=prod.getProd_size() %></td></tr>
		<tr><th>상품색상</th><td><%=prod.getProd_color() %></td></tr>
		<tr><th>배송방법</th><td><%=prod.getProd_delivery() %></td></tr>
		<tr><th>수량</th><td><%=prod.getProd_unit() %></td></tr>
		<tr><th>입고수량</th><td><%=prod.getProd_qtyin() %></td></tr>
		<tr><th>할인수량</th><td><%=prod.getProd_qtysale() %></td></tr>
		<tr><th>마일리지</th><td><%=prod.getProd_mileage() %></td></tr>
		
</table>



</body>
</html>