<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	
	<h4>${member.mem_name} 님의 마이페이지</h4>
	<table border="1">
		<tr>
			<th>회원아이디</th>
			<td>${member.mem_id}</td>
		</tr>

		<tr>
			<th>프로필</th>
			<td><img src="data:image/*;base64, ${member.base64Image}">
			</td>
		</tr>


		<tr>
			<th>회원이름</th>
			<td>${member.mem_name}</td>
		</tr>
		<tr>
			<th>주민번호1</th>
			<td>${member.mem_regno1}</td>
		</tr>
		<tr>
			<th>주민번호2</th>
			<td>${member.mem_regno2}</td>
		</tr>
		<tr>
			<th>생일</th>
			<td>${member.mem_bir}</td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td>${member.mem_zip}</td>
		</tr>
		<tr>
			<th>회원집주소1</th>
			<td>${member.mem_add1}</td>
		</tr>
		<tr>
			<th>회원집주소2</th>
			<td>${member.mem_add2}</td>
		</tr>
		<tr>
			<th>회원집번호</th>
			<td>${member.mem_hometel}</td>
		</tr>
		<tr>
			<th>회원회사번호</th>
			<td>${member.mem_comtel}</td>
		</tr>
		<tr>
			<th>회원전화번호</th>
			<td>${member.mem_hp}</td>
		</tr>
		<tr>
			<th>회원메일</th>
			<td>${member.mem_mail}</td>
		</tr>
		<tr>
			<th>회원직업</th>
			<td>${member.mem_job}</td>
		</tr>
		<tr>
			<th>회원취미</th>
			<td>${member.mem_like}</td>
		</tr>
		<tr>
			<th>회원</th>
			<td>${member.mem_memorial}</td>
		</tr>
		<tr>
			<th>회원기념일</th>
			<td>${member.mem_memorialday}</td>
		</tr>
		<tr>
			<th>회원마일리지</th>
			<td>${member.mem_mileage}</td>
		</tr>
		<tr>
			<th>회원삭제</th>
			<td>${member.mem_delete}</td>
		</tr>

		<td colspan="2"><input type="button" value="멤버리스트로 이동"
			class="controlBtn" id="memberListBtn">
		</td>
		<tr>
		<tr>
			<th>구매목록</th>
			<td>
				<table border="1">
					<thead>
						<tr>
							<th>상품코드</th>
							<th>상품명</th>
							<th>상품분류명</th>
							<th>거래처명</th>
							<th>구매가</th>
							<th>판매가</th>
							<th>마일리지</th>

						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty member.prodList}">
								<c:forEach items="${member.prodList}" var="prod">
									<tr>
										<td>${prod.prod_id}</td>
										<td><a
											href="${cPath}/prod/prodView.do?what=${prod.prod_id}">${prod.prod_name}</a></td>
										<td>${prod.lprod_nm}</td>
										<td>${prod.buyer.buyer_name}</td>
										<td>${prod.prod_cost}</td>
										<td>${prod.prod_price}</td>
										<td>${prod.prod_mileage}</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="7">구매기록이 없음.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
	<form id="deleteForm"
		action="<%=request.getContextPath()%>/member/memberDelete.do"
		method="post">
		<input type="hidden" name="password" />
	</form>
	<script type="text/javascript">
		let deleteForm = $("#deleteForm");
		$(".controlBtn").on("click", function(){
			let btnId = $(this).prop("id");
			if(btnId =="memberListBtn"){
				//alert("수정")
				location.href="${cPath }/member/memberList.do";

							} else if (btnId == "deleteBtn") {
								let password = prompt("비번 입력");
								//alert , prompt
								if (!password) { // 비밀번호를 입력 하지 않은 경우
									return;
								}
								deleteForm.find("[name='password']").val(
										password);
								deleteForm.submit(); // trigger 도 가능.
							}

							//	/member/memberUpdate.do

						});
	</script>
