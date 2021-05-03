<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   

	
    <h4>회원리스트</h4>
    <a href="#">회원등록</a>
    <table>
    	<tr>
			<td colspan="6">

			<div id ="searchUI">
				<select name ="searchType">
					<option value>전체</option>
					<option value = "id">아이디</option>
					<option value = "email">이메일</option>
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
    
    </table>
    
    <table>
    	<thead>
    		<tr>
    			<th>순번</th>
    			<th>아이디</th>
    			<th>패스워드</th>
    			<th>이메일</th>
    			<th>전화번호</th>
    		</tr>
    	</thead>
    	
    	<tbody>
    	<c:choose>
			<c:when test="${not empty pagingVO.dataList }">
				<c:forEach items="${pagingVO.dataList }" var="employee">
					<tr>
						<td>${emp.rnum }</td>
						<td>
						<c:url value="/emp/empView.do" var="viewURL">
							<c:param name="who" value="${emp.employee_id}" />
						</c:url>						
						<a href="${viewURL }">${employee.employee_id}</a> 
						</td>
						<td>${emp.employee_pwd }</td>
						<td>${emp.employee_email }</td>
						<td>${emp.employee_phone }</td>
						
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
    	
    </table>
    
    <script type="text/javascript">
	let searchForm = $("#searchForm");
	let searchUI = $("#searchUI");
	searchUI.find("[name='searchType']").val("${pagingVO.simpleSearch.searchType}");
	$("#searchBtn").on("click",function(){
		let inputs = $("#searchUI").find(":input[name]") /* name 을 속성으로 가지고 있는 입력 하는 것들을 다 가지고 오겠다.  */
		$(inputs).each(function(idx, input){
			let name = $(this).attr("id");
			let sameInput = $("#searchForm").find("[name='"+id+"']");
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
    