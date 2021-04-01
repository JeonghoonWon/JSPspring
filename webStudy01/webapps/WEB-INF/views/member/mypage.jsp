<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
	<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request" />
	<h4>
		<%=member.getMem_name()%>
		님의 마이페이지
	</h4>
	<table>
		<tr>
			<th>회원아이디</th>
			<td><%=member.getMem_id()%></td>
		</tr>
		<tr>
			<th>회원비밀번호</th>
			<td><%=member.getMem_pass()%></td>
		</tr>
		<tr>
			<th>회원이름</th>
			<td><%=member.getMem_name()%></td>
		</tr>
		<tr>
			<th>주민번호1</th>
			<td><%=member.getMem_regno1()%></td>
		</tr>
		<tr>
			<th>주민번호2</th>
			<td><%=member.getMem_regno2()%></td>
		</tr>
		<tr>
			<th>생일</th>
			<td><%=member.getMem_bir()%></td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td><%=member.getMem_zip()%></td>
		</tr>
		<tr>
			<th>회원집주소1</th>
			<td><%=member.getMem_add1()%></td>
		</tr>
		<tr>
			<th>회원집주소2</th>
			<td><%=member.getMem_add2()%></td>
		</tr>
		<tr>
			<th>회원집번호</th>
			<td><%=member.getMem_hometel()%></td>
		</tr>
		<tr>
			<th>회원회사번호</th>
			<td><%=member.getMem_comtel()%></td>
		</tr>
		<tr>
			<th>회원전화번호</th>
			<td><%=member.getMem_hp()%></td>
		</tr>
		<tr>
			<th>회원메일</th>
			<td><%=member.getMem_mail()%></td>
		</tr>
		<tr>
			<th>회원직업</th>
			<td><%=member.getMem_job()%></td>
		</tr>
		<tr>
			<th>회원취미</th>
			<td><%=member.getMem_like()%></td>
		</tr>
		<tr>
			<th>회원</th>
			<td><%=member.getMem_memorial()%></td>
		</tr>
		<tr>
			<th>회원기념일</th>
			<td><%=member.getMem_memorialday()%></td>
		</tr>
		<tr>
			<th>회원마일리지</th>
			<td><%=member.getMem_mileage()%></td>
		</tr>
		<tr>
			<th>회원삭제</th>
			<td><%=member.getMem_delete()%></td>
		</tr>
		
			<td colspan="2">
				<input type = "button" value = "수정" class ="controlBtn" id = "updateBtn">
				
				<button type = "button" class ="controlBtn" id="deleteBtn">탈퇴</button>
			</td>
			<tr>
	</table>
	<script type="text/javascript">
		$(".controlBtn").on("click", function(){
			let btnId = $(this).prop("id");
			if(btnId =="updateBtn"){
				//alert("수정")
				location.href="<%=request.getContextPath() %>/member/memberUpdate.do";
			}else if(btnId =="deleteBtn"){
				alert("탈퇴")
			}
			
			
			///member/memberUpdate.do
		});
		
	</script>
	
</body>
</html>