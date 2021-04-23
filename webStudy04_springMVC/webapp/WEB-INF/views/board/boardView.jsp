<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<c:if test="${not empty message }">
	<script type="text/javascript">
		alert("${message}");
	</script>
	<c:remove var="message" scope="session" />
</c:if>

</head>
<body>
	<table class="table table-bordered">
		<tr>
			<th>게시판종류</th>
			<td>${board.bo_type}</td>
		</tr>
		<tr>
			<th>글번호</th>
			<td>${board.bo_no}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${board.bo_title}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${board.bo_writer}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${board.bo_date}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${board.bo_hit}</td>
		</tr>
		<tr>
			<th>추천수</th>
			<td>${board.bo_rec}</td>
		</tr>
		<tr>
			<th>신고수</th>
			<td>${board.bo_rep}</td>
		</tr>
		<tr>
		<tr>
			<th>첨부파일</th>
			<td><c:if test="${not empty board.attatchList }">
					<c:forEach items="${board.attatchList }" var="attatch">
						<!-- 다운로드 시 필수파라미터로   -->
						<c:url value="/board/download.do" var="downloadURL">
							<c:param name="what" value="${attatch.att_no }" />
						</c:url>
						<a href="${downloadURL }"><span>${attatch.att_filename }</span></a>
					</c:forEach>
				</c:if></td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${board.bo_content}</td>
		</tr>
		<tr>
			<td colspan="2"><c:url value="/board/boardList.do" var="listURL" />
				<a class="btn btn-outline-primary" href="${listURL }">목록으로</a> <a
				class="btn btn-outline-secondary"
				href="${cPath }/board/noticeList.do">공지글 목록</a> <c:url
					value="/board/boardInsert.do" var="insertURL">
					<c:param name="parent" value="${board.bo_no }" />
				</c:url> 
				 <c:url value="/board/boardUpdate.do" var="updateURL">
					<c:param name="what" value="${board.bo_no }" />
				</c:url>
				<c:if test="${board.bo_delete eq 'N'}">
					<a class="btn btn-outline-success" href="${insertURL}">답글쓰기</a>
					<a class="btn btn-outline-warning" href="${updateURL }">수정하기</a> 
					<!-- Button trigger modal -->
					<button type="button" class="btn btn-outline-danger"
						data-toggle="modal" data-target="#passModal">
						삭제하기</button>
				</c:if>
					
				<!-- <a class="btn btn-outline-danger"  href="#">삭제하기</a> -->

				 <!-- Modal -->
				<div class="modal fade" id="passModal" tabindex="-1"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">게시글 삭제</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<input type="text" placeholder="비밀번호 입력" name="bo_pass" />
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">닫기</button>
								<button id="deleteBtn" type="button" class="btn btn-danger">삭제</button>
							</div>
						</div>
					</div>
				</div></td>
		</tr>
	</table>


		
		<!-- 삭제시 post 방식을 이용 form 사용. modal 에서 입력 받는 bo_pass와 board가 가지고 있는 bo_no 넘겨준다. -->
		<form id="deleteForm" action="${cPath }/board/boardDelete.do"
			method="post">
			<input type="text" name="bo_pass" /> 
			<input type="text" name="bo_no" value="${board.bo_no }" />
			<c:if test="${not empty board.attatchList}">
				<c:forEach items="${board.attatchList }" var="attatch">
					<input type="text" name="delAttNos" value="${attatch.att_no }" />
				</c:forEach>
			</c:if>
		</form>

	<script type="text/javascript">
		$(".goBtn").on("click", function() {
			let url = $(this).data("gopage");
			if (url)
				location.href = url;
		});
		
		/*  스크립트 사용, deleteForm을 아이디로 갖는 form 을 let 으로 변수 선언. */
		let deleteForm = $("#deleteForm");
		// 함수 실행, deleteBtn 버튼을 클릭하면, 
		$("#deleteBtn").on("click", function() {
			// passModal을 아이디로 가진 곳에서 name이 bo_pass인 input을 찾고 거기서의 벨류 값을 password 에 넣겠다.
			let password = $("#passModal").find("[name=bo_pass]").val();
			
			// 변수선언한 deleteForm에서의 bo_pass 의 벨류값에 password 를 넣어준다.
			deleteForm.find("[name='bo_pass']").val(password);
			// deleteForm submit 해준다.
			deleteForm.submit();

			// 입력 되는 password 와 bo_pass 가 같을 경우 
		})
	</script>
	<jsp:include page="/includee/postScript.jsp" />
</body>
</html>