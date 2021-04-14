<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<%
	String message = (String) request.getAttribute("message");
	if (StringUtils.isNotBlank(message)) {
%>
	<script type="text/javascript">
			alert("<%=message%>")
	</script>
<%
	}
%>
</head>
<body>
<jsp:useBean id="buyer" class="kr.or.ddit.vo.BuyerVO" scope="request" />
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request" />
<form method="post">
	<table>
		<input type="hidden" name="buyer_id" value="<%=buyer.getBuyer_id()%>"/>
		<tr>
			<th>거래처명</th>
			<td>
				<input type="text" name="buyer_name"
				value="<%=Objects.toString(buyer.getBuyer_name(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_name"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처분류명</th>
			<td>
			<%
				List<Map<String, Object>> lprodList = (List) request.getAttribute("lprodList");
				if(lprodList!=null){
			%>
				<select name="buyer_lgu" required>
						<option value>상품분류</option>
						<%
							
								for (Map<String, Object> lprod : lprodList) {
							%>
							<option value="<%=lprod.get("lprod_gu")%>" >
								<%=lprod.get("lprod_nm")%>
							</option>
							<%
							}
						%>
				</select>
			<%
				}else{
			%>
				<input type="hidden"  name="buyer_lgu" value="<%=buyer.getBuyer_lgu()%>"/>
				<span><%=buyer.getLprod_nm() %></span>
			<%
				}
			%>
			</td>
		</tr>
		<tr>
			<th>거래처은행</th>
			<td>
				<input type="text" name="buyer_bank"
				value="<%=Objects.toString(buyer.getBuyer_bank(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_bank"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처은행계좌</th>
			<td>
				<input type="text" name="buyer_bankno"
					value="<%=Objects.toString(buyer.getBuyer_bankno(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_bankno"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처예금주</th>
			<td>
				<input type="text" name="buyer_bankname"
				value="<%=Objects.toString(buyer.getBuyer_bankname(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_bankname"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처우편번호</th>
			<td>
				<input type="text" name="buyer_zip"
					value="<%=Objects.toString(buyer.getBuyer_zip(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_zip"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처기본주소</th>
			<td>
				<input type="text" name="buyer_add1"
				value="<%=Objects.toString(buyer.getBuyer_add1(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_add1"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처상세주소</th>
			<td>
				<input type="text" name="buyer_add2"
				value="<%=Objects.toString(buyer.getBuyer_add2(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_add2"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처회사번호</th>
			<td>
				<input type="text" name="buyer_comtel" required
				value="<%=Objects.toString(buyer.getBuyer_comtel(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_comtel"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처팩스번호</th>
			<td>
				<input type="text" name="buyer_fax" required
				value="<%=Objects.toString(buyer.getBuyer_fax(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_fax"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처메일</th>
			<td>
				<input type="text" name="buyer_mail" required
				value="<%=Objects.toString(buyer.getBuyer_mail(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_mail"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처담당자</th>
			<td>
				<input type="text" name="buyer_charger"
				value="<%=Objects.toString(buyer.getBuyer_charger(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_charger"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<th>거래처비고</th>
			<td>
				<input type="text" name="buyer_telext"
				value="<%=Objects.toString(buyer.getBuyer_telext(), "")%>" />
				<span class="error">"<%=Objects.toString(errors.get("buyer_telext"), "")%>"</span>
			</td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="저장"/></td>
		</tr>
	</table>
</form>
</body>
</html>	