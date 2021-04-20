<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>


<head>
<meta charset="UTF-8">
<title>board/boardList.jsp</title>
<jsp:include page="/includee/preScript.jsp" />
<c:if test="${not empty message }">
	<script type="text/javascript">
		alert("${message}"); /* 여기까지 왔다는건 session 을 사용했다는것. 이제 session을 지워줘야한다. */	
	</script>
	<c:remove var="message" scope="session"/> <!-- 어디서 세션을 지워야 하는지 명시해줘야한다.  -->
</c:if>

</head>
<body>

<h4><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-emoji-smile" viewBox="0 0 16 16">
  <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
  <path d="M4.285 9.567a.5.5 0 0 1 .683.183A3.498 3.498 0 0 0 8 11.5a3.498 3.498 0 0 0 3.032-1.75.5.5 0 1 1 .866.5A4.498 4.498 0 0 1 8 12.5a4.498 4.498 0 0 1-3.898-2.25.5.5 0 0 1 .183-.683zM7 6.5C7 7.328 6.552 8 6 8s-1-.672-1-1.5S5.448 5 6 5s1 .672 1 1.5zm4 0c0 .828-.448 1.5-1 1.5s-1-.672-1-1.5S9.448 5 10 5s1 .672 1 1.5z"/>
</svg> 게시글 목록 조회  <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-emoji-smile" viewBox="0 0 16 16">
  <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
  <path d="M4.285 9.567a.5.5 0 0 1 .683.183A3.498 3.498 0 0 0 8 11.5a3.498 3.498 0 0 0 3.032-1.75.5.5 0 1 1 .866.5A4.498 4.498 0 0 1 8 12.5a4.498 4.498 0 0 1-3.898-2.25.5.5 0 0 1 .183-.683zM7 6.5C7 7.328 6.552 8 6 8s-1-.672-1-1.5S5.448 5 6 5s1 .672 1 1.5zm4 0c0 .828-.448 1.5-1 1.5s-1-.672-1-1.5S9.448 5 10 5s1 .672 1 1.5z"/>
</svg> </h4>
	<table class = "table table-dark table-striped">
		<thead>
			<tr>
				<th>글종류</th>
				<th>글번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				
				<th>조회수</th>
				<th>추천수</th>
				
			</tr>
		</thead>

		<tbody id="listBody">
		<c:choose>
			<c:when test="${not empty pagingVO.dataList }">
				<c:forEach items="${pagingVO.dataList }" var="board">
					<tr>
						<td>${board.bo_type }</td>
						<td>${board.bo_no }</td>
						<td>
							<c:url value="/board/boardView.do" var ="viewURL">
								<c:param name="what" value="${board.bo_no }" />
							</c:url>
							<c:choose>
								<c:when test="${board.bo_sec eq 'Y' }">
									<a class ="secret" href="${viewURL }">
										${board.bo_title }
									</a>
								
								
								</c:when>
								<c:otherwise>								
									<a class = "nonsecret" href="${viewURL }"  data-toggle="popover" title="Popover title">
										${board.bo_title }
									</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${board.bo_writer }</td>
						<td>${board.bo_date }</td>
						<td>${board.bo_hit }</td>
						<td>${board.bo_rec }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
		         <tr>
		            <td colspan="7">
		               등록된 회원이 없음
		            </td>
		         </tr>
      		</c:otherwise>
		</c:choose>
	</tbody>

		<tfoot>
			<tr>
				<td colspan="7" >
					<form id="searchForm">
						<%--
					SearchVO searchVO = pagingVO.getSimpleSearch();				
				--%>
						<input type="hidden" name="searchType" value = "${pagingVO.searchMap.searchType}" />
						<!--OGNL 과 표기법이 비슷하다.  -->
						<input type="hidden" name="searchWord" value = "${pagingVO.searchMap.searchWord}" /> 
						<input type ="hidden" name = "minDate" value = "${pagingVO.searchMap.minDate }" />
						<input type ="hidden" name = "maxDate" value = "${pagingVO.searchMap.maxDate }" />
						
						<input type="hidden" name="page" />

					</form>
					<div id="searchUI"  class="form-inline d-flex justify-content-center">
						<select name="searchType"  class="form-control mr-2">
							<option value>전체</option>
							<option value="title">제목</option>
							<option value="writer">작성자</option>
							<option value ="content">내용</option>
							<option value="type">게시판 종류</option>
						
						</select>
						<input class="form-control mr-2" type="text" name="searchWord" value="${pagingVO.searchMap.searchWord}" /> 
						<input class="form-control mr-2" type="date" name="minDate" value = "${pagingVO.searchMap.minDate }"/>
						<input class="form-control mr-2" type="date" name="maxDate" value = "${pagingVO.searchMap.maxDate }"/>
						
							
							<input class="btn btn-primary" id="searchBtn" type="button" value= "search" />
							
							<input class="btn btn-primary" id="newBtn" type="button" value= "insert" />
				
							<input class="btn btn-primary" type ="button" value = "공지글쓰기" onclick="location.href='${cPath }/board/noticeInsert.do';" />
							
						<!-- submit -> button으로 변경  -->
						<!-- 버튼을 누르면 searchForm 으로 정보가 넘어가게 처리해야 한다.  -->
					</div>
					<div id="pagingArea"  class="d-flex justify-content-center">
					${pagingVO.pagingHTMLBS }
					</div>

				</td>
			</tr>
		</tfoot>
	</table>
	<script type="text/javascript">
		let searchForm = $("#searchForm");
		let searchUI = $("#searchUI");
		searchUI.find("[name='searchType']").val(
				"${pagingVO.searchMap.searchType}");
		$("#searchBtn").on("click", function() {
			let inputs = $("#searchUI").find(":input[name]") /* name 을 속성으로 가지고 있는 입력 하는 것들을 다 가지고 오겠다.  */
			$(inputs).each(function(idx, input) {
				let name = $(this).attr("name");
				let sameInput = $("#searchForm").find("[name='" + name + "']");
				$(sameInput).val($(this).val());
			});
			searchForm.submit();
		});
		$("#newBtn").on("click", function(){
			location.href = $.getContextPath() + "/board/boardInsert.do";
		});
		

		$("#pagingArea").on("click", "a", function(event) {
			event.preventDefault();
			let page = $(this).data("page");
			if (page) {
				searchForm.find("[name='page']").val(page);
				searchForm.submit();
			}
			return false;
		});
		
		$(function () {
			 /*  decendent 구조? */
				$("#listBody a.nonsecret").hover(function(){
					/*$(this).popover() : 초기화 .  */
					$(this).popover({
						html:true
						, content:function(){
							let url = this.href;
							let retValue = null;
							$.ajax({
								url : url,
								dataType : "text",
								success : function(resp) {
										
										retValue =resp;
								},
								async : false, /* 비동기를 동기 요청으로 바꿀 수 있다. */
								/* 비동기는 rock 을 걸지 않는다. 순서를 제어할 수 없음.  */
								/* 순서를 제어하려면 rock 필요하다. 그래서 async : false 로 해줘야한다. */
								cache : true,
								error : function(xhr, error, msg) {
									// xhr : XMLHttpRequest (XHR)은 AJAX 요청을 생성하는 JavaScript API입니다. XHR의 메서드로 브라우저와 서버간의 네트워크 요청을 전송할 수 있습니다

									console.log(xhr);
									console.log(error);
									console.log(msg);

								}

							});
							
							return retValue;
						}
						
					}).popover("toggle");
				});
			});
		
		
	</script>
	
	<jsp:include page="/includee/postScript.jsp" />

</body>
</html>