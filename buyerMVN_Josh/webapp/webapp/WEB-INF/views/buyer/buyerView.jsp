<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래처 상세 정보</title>
<jsp:include page="/includee/preScript.jsp"/>
<jsp:useBean id="buyer" class="kr.or.ddit.vo.BuyerVO" scope="request"/>
</head>
<body>
	<h4><%=buyer.getBuyer_name()%> 거래처 상세 정보</h4>
	<table>
		<tr>
			<th>거래처아이디</th>
			<td><%=buyer.getBuyer_id()%></td>
		</tr>
		<tr>
			<th>거래처명</th>
			<td><%=buyer.getBuyer_name()%></td>
		</tr>
		<tr>
			<th>거래처분류명</th>
			<td><%=buyer.getLprod_nm()%></td>
		</tr>
		<tr>
			<th>거래처은행</th>
			<td><%=buyer.getBuyer_bank()%></td>
		</tr>
		<tr>
			<th>거래처은행계좌</th>
			<td><%=buyer.getBuyer_bankno()%></td>
		</tr>
		<tr>
			<th>거래처예금주</th>
			<td><%=buyer.getBuyer_bankname()%></td>
		</tr>
		<tr>
			<th>거래처우편번호</th>
			<td><%=buyer.getBuyer_zip()%></td>
		</tr>
		<tr>
			<th>거래처기본주소</th>
			<td><%=buyer.getBuyer_add1()%></td>
		</tr>
		<tr>
			<th>거래처상세주소</th>
			<td><%=buyer.getBuyer_add2()%></td>
		</tr>
		<tr>
			<th>거래처회사번호</th>
			<td><%=buyer.getBuyer_comtel()%></td>
		</tr>
		<tr>
			<th>거래처팩스번호</th>
			<td><%=buyer.getBuyer_fax()%></td>
		</tr>
		<tr>
			<th>거래처메일</th>
			<td><%=buyer.getBuyer_mail()%></td>
		</tr>
		<tr>
			<th>거래처담당자</th>
			<td><%=buyer.getBuyer_charger()%></td>
		</tr>
		<tr>
			<th>거래처비고</th>
			<td><%=buyer.getBuyer_telext()%></td>
		</tr>
		<tr>
			<th>거래처삭제여부</th>
			<td><%=buyer.getBuyer_delete()%></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" id="buyerList">리스트로 돌아가기</button>
				<button type="button" id="update">수정</button>
				<button type="button" id="delete">삭제</button>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		$("#buyerList").on("click", function(){
			location.href="<%=request.getContextPath()%>/buyer/buyerList.do";
		})
		$("#update").on("click", function(){
			location.href="<%=request.getContextPath()%>/buyer/buyerUpdate.do?buyer=<%=buyer.getBuyer_id()%>";
		})
		$("#delete").on("click", function(){
			var result = confirm("정말로 삭제하시겠습니까??")
			if(result){
				location.href="<%=request.getContextPath()%>/buyer/buyerDelete.do?buyer=<%=buyer.getBuyer_id()%>";
			}
		})
	</script>
</body>
</html>