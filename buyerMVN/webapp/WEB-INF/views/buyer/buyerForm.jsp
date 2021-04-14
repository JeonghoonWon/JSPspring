<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래처 폼</title>
<style type="text/css">
.error {
	color: red;
}
</style>
<jsp:include page="/includee/preScript.jsp" />
<c:if test="${not empty message}">
	<script type="text/javascript">
			alert("${message}")
	</script>
</c:if>
</head>
<body>
<form method="post">
	<table>
		<input type="hidden" name="buyer_id" value="${buyer.buyer_id}"/>
		<tr>
			<th>거래처명</th>
			<td>
				<input type="text" name="buyer_name"
				value="${buyer.buyer_name}" />
				<span class="error">"${errors.buyer_name}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처분류명</th>
			<td>
				<select name="buyer_lgu">
					<option value>상품분류</option>
					<c:forEach items="${lprodList}" var="lprod">
						<c:set var="selected" value='${lprod.lprod_gu eq buyer.buyer_lgu?"selected":""}'></c:set>
						<option value="${lprod.lprod_gu}" ${selected}>
							${lprod.lprod_nm}
						</option>
					</c:forEach>
				</select>
				<span class="error">"${errors.buyer_lgu}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처은행</th>
			<td>
				<input type="text" name="buyer_bank"
				value="${buyer.buyer_bank}" />
				<span class="error">"${errors.buyer_bank}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처은행계좌</th>
			<td>
				<input type="text" name="buyer_bankno"
					value="${buyer.buyer_bankno}" />
				<span class="error">"${errors.buyer_bankno}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처예금주</th>
			<td>
				<input type="text" name="buyer_bankname"
				value="${buyer.buyer_bankname}" />
				<span class="error">"${errors.buyer_bankname}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처우편번호</th>
			<td>
				<input type="text" name="buyer_zip"
					value="${buyer.buyer_zip}" />
				<span class="error">"${errors.buyer_zip}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처기본주소</th>
			<td>
				<input type="text" name="buyer_add1"
				value="${buyer.buyer_add1}" />
				<span class="error">"${errors.buyer_add1}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처상세주소</th>
			<td>
				<input type="text" name="buyer_add2"
				value="${buyer.buyer_add2}" />
				<span class="error">"${errors.buyer_add2}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처회사번호</th>
			<td>
				<input type="text" name="buyer_comtel" 
				value="${buyer.buyer_comtel}" />
				<span class="error">"${errors.buyer_comtel}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처팩스번호</th>
			<td>
				<input type="text" name="buyer_fax" 
				value="${buyer.buyer_fax}" />
				<span class="error">"${errors.buyer_fax}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처메일</th>
			<td>
				<input type="text" name="buyer_mail" 
				value="${buyer.buyer_mail}" />
				<span class="error">"${errors.buyer_mail}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처담당자</th>
			<td>
				<input type="text" name="buyer_charger"
				value="${buyer.buyer_charger}" />
				<span class="error">"${errors.buyer_charger}"</span>
			</td>
		</tr>
		<tr>
			<th>거래처비고</th>
			<td>
				<input type="text" name="buyer_telext"
				value="${buyer.buyer_telext}" />
				<span class="error">"${errors.buyer_telext}"</span>
			</td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="저장"/></td>
		</tr>
	</table>
</form>
</body>
</html>	