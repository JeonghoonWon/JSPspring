<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<script type="text/javascript" src="${cPath }/js/ckeditor/ckeditor.js"></script>

<c:if test="${not empty message }">
	<script type="text/javascript">
		alert("${message}");
	</script>
	<c:remove var="message" scope="session"/>
</c:if>


<!-- post를 통해 만들어진 바디를 part로 나눠서 보내겠다는 뜻
 / 아무런 데이터를 보내지 않아도 데이터는 보내진다. -->
</head>
<body>
	<form id ="boardForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="bo_no" value="${board.bo_no }" /> 
		<input type="hidden" name="bo_type" value="${board.bo_type }"> 
		<input type="hidden" name="bo_parent" value="${board.bo_parent }" />
		<table class="table table-bordered">
			<tr>
				<th>제목</th>
					<td>
						<div class="input-group">
							<input class="form-control col-9 mr-3" type="text" name="bo_title"
								required value="${board.bo_title }" />
							<div class="form-check col-2">
								<!-- 비밀글 선택  -->
								<input class="form-check-input" type="checkbox" id="bo_sec"
									name="bo_sec" value="Y" ${board.bo_sec eq 'Y' ? 'checked':'' } />
								<label class="form-check-label" for="bo_sec"> 비밀글 </label>
							</div>
							<span class="error">${errors.bo_title }</span>
						</div>
					</td>
			</tr>
			<c:if test="${board.bo_type eq 'BOARD' }">
				<tr>
					<th>작성자</th>
					<td><input class="form-control" type="text" name="bo_writer"
						required value="${board.bo_writer }" /> <span class="error">${errors.bo_writer }</span>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input class="form-control" type="text" name="bo_pass"
						required /> <span class="error">${errors.bo_pass }</span></td>
				</tr>
						<!-- 기존 파일이 있는 경우   -->
				<tr>
					<th>기존파일</th>
					<td>
						<c:if test="${not empty board.attatchList}">
							<!-- board.attatchList 를 for 문 돌린다.  -->
							<c:forEach items="${board.attatchList }" var = "attatch">
								<span data-attno="${attatch.att_no }">
									${attatch.att_filename }
									<span class ="delBtn btn btn-danger">-</span>
								</span>
							</c:forEach>
						</c:if>
					</td>
				</tr>
				
				
				<tr>
					<th>첨부파일</th>
					<td>
						<div class="form-inline fileArea">
							<p>
								<input class="form-control" type="file" name="bo_files">
								<span class="plusBtn btn btn-secondary">+</span>
							</p>
						</div>
					</td>
				</tr>
			</c:if>
			<tr>
				<th>내용</th>

				<td><textarea class="form-control" rows="5" cols="100"
						name="bo_content" id="bo_content">${board.bo_content }</textarea>
					<!-- id 값 : ckeditor 를 위해.  --> <span class="error">${errors.bo_content }</span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<!-- bo_type은 insert할떄 사용하지는 않음 / boardView에 존재하는것 -->
					<div class="d-flex justify-content-center">
						<button type="submit" class="btn btn-success mr-2">저장</button>
						<button type="reset" class="btn btn-warning mr-2">취소</button>
						<button type="button" class="goBtn btn btn-primary mr-2"
							data-gopage="<c:url value='/board/boardList.do'/>">목록으로</button>
					</div>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		CKEDITOR.replace("bo_content",{
				filebrowserImageUploadUrl : '${cPath}/board/boardImage.do?type=Images'
						});
		$(".fileArea").on("click", ".plusBtn", function() {
			let source = $(this).parents("p:first");
			let clone = source.clone();
			clone.find("input").val("");
			$(this).parents(".fileArea:first").append(clone);
		});
		
		let boardForm = $("#boardForm");
		$(".delBtn").on("click", function() {
			let fileSpan = $(this).parents("span:first");
			let delAttNo = fileSpan.data("attno");
			let newInput = $("<input>").attr({
								"type":"text"
								,"name":"delAttNos"
							}).val(delAttNo);
			
			boardForm.append(newInput);
			fileSpan.hide();
		});
		
		
		$(".goBtn").on("click", function() {
			let url = $(this).data("gopage");
			if (url)
				location.href = url;
		});
	</script>
	<jsp:include page="/includee/postScript.jsp" />
</body>
</html>