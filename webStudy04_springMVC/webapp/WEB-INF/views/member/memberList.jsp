<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"></jsp:include>
</head>
<body>
<h4>회원 목록 조회</h4>
<table>
	<thead>
		<tr>
			<th>NO.</th>
			<th>회원아이디</th>
			<th>회원명</th>
			<th>이메일</th>
			<th>휴대폰</th>
			<th>마일리지</th>
			<th>탈퇴여부</th>
			<th>지역</th>
		</tr>
	</thead>
	<tbody>
		
		<c:choose>
			<c:when test="${not empty pagingVO.dataList }">
				<c:forEach items="${pagingVO.dataList }" var="member">
					<tr>
						<td>${member.rnum }</td>
						<td>${member.mem_id }</td>
						<td>
						<c:url value="/member/memberView.do" var="viewURL">
							<c:param name="who" value="${member.mem_id}" />
						</c:url>						
						<a href="${viewURL }">${member.mem_name}</a> 
						
						</td>
						<td>${member.mem_mail }</td>
						<td>${member.mem_hp }</td>
						<td>${member.mem_mileage }</td>
						<td>
							${"Y" eq member.mem_delete ? "탈퇴": ""}
						</td>
						<td>${member.mem_add1 }</td>
					</tr>
					
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
			<td colspan="8">
				등록된 회원 없음
			</td>
			</tr>
			</c:otherwise>
		</c:choose>
	
	
	</tbody>
	
	<tfoot>
		<tr>
			<td colspan="6">
			<form id = "searchForm">
				<%--
					SearchVO searchVO = pagingVO.getSimpleSearch();				
				--%>
				<input type = "text" name ="searchType" value = "${pagingVO.simpleSearch.searchType}"/> <!--OGNL 과 표기법이 비슷하다.  -->
				<input type = "text" name ="searchWord" value = "${pagingVO.simpleSearch.searchWord}"/>
				<input type = "text" name ="page" />
				
			</form>
			<div id ="searchUI">
				<select name ="searchType">
					<option value>전체</option>
					<option value = "name">이름</option>
					<option value = "address">지역</option>
				</select>
				<input type = "text" name = "searchWord" value = "${pagingVO.simpleSearch.searchWord}"/>
				<input id="searchBtn"type = "button" value = "검색" />  <!-- submit -> button으로 변경  -->
				 <!-- 버튼을 누르면 searchForm 으로 정보가 넘어가게 처리해야 한다.  -->
			</div>
				<div id="pagingArea">
				${pagingVO.pagingHTML }
				</div>
			</td>
		</tr>
	</tfoot>
	
</table>
<script type="text/javascript">
	let searchForm = $("#searchForm");
	let searchUI = $("#searchUI");
	searchUI.find("[name='searchType']").val("${pagingVO.simpleSearch.searchType}");
	$("#searchBtn").on("click",function(){
		let inputs = $("#searchUI").find(":input[name]") /* name 을 속성으로 가지고 있는 입력 하는 것들을 다 가지고 오겠다.  */
		$(inputs).each(function(idx, input){
			let name = $(this).attr("name");
			let sameInput = $("#searchForm").find("[name='"+name+"']");
			$(sameInput).val($(this).val());
			});
		searchForm.submit();
	});
	
	$("#pagingArea").on("click", "a", function(event){
		event.preventDefault();
		let page = $(this).data("page");
		if(page){
		searchForm.find("[name='page']").val(page);
		searchForm.submit();
		}
		return false;
	});
</script>
</body>
</html>