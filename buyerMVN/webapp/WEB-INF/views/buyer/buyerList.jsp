<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<c:forEach items="${lprodList}" var="lprod">
				<c:set var="selected" value='${lprod.lprod_gu eq buyer.buyer_lgu?"selected":""}'></c:set>
				<option value="${lprod.lprod_gu}" ${selected}>
					${lprod.lprod_nm}
				</option>
			</c:forEach>
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