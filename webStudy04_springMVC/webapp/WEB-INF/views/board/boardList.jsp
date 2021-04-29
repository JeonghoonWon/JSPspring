<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> <!-- spring tag 라고 부름  --> 
<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title> -->
<%-- <jsp:include page="/includee/preScript.jsp" /> --%>
<style type="text/css">
	.thumbnail{
		width : 50px;
		height: 50px;
	} 
</style>

<spring:message code="bow"/>
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
			<th>게시글종류</th>
			<th>글번호</th>
			<th>썸네일</th>
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
						<img class="thumbnail" src="${board.thumbnail }"/>
					</td>
					<td>
						<c:url value="/board/boardView.do" var="viewURL">
							<c:param name="what" value="${board.bo_no }" />
						</c:url>
						<c:choose>
							<c:when test="${board.bo_sec eq 'Y' }">
								<a class="secret" href="${viewURL }">
									${board.bo_title }
								</a>
							</c:when>
							<c:otherwise>
								<a class="nonsecret" href="${viewURL }"  data-toggle="popover" title="Popover title" >
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
					조건에 맞는 게시글이 없음.
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="8">
				<!-- action 을 사용하지않으면 클라이언트가 가지고있는 주소를 사용하는데 검색에 실패할 경우 , 값이 두번 넘어가서 다음번 조회가 불가능해진다.
					되도록이면 action 을 주는게 좋다. -->
				<form id="searchForm" action="${cPath }/board/boardList.do">
						<!-- get, post 이외의 method 가 필요한 경우 form 의 method=post 로 정해두고, hidden , name="_method" value="delete/put/ 등으로 변경해서 넘겨준다. -->
						<%--
					SearchVO searchVO = pagingVO.getSimpleSearch();				
				--%>
					<input type="hidden" name="searchType" value="${pagingVO.searchMap.searchType }"/>
						<!--OGNL 과 표기법이 비슷하다.  -->
					<input type="hidden" name="searchWord" value="${pagingVO.searchMap.searchWord }"/>
					<input type="hidden" name="minDate" value="${pagingVO.searchMap.minDate }"/>
					<input type="hidden" name="maxDate" value="${pagingVO.searchMap.maxDate }"/>
					<input type="hidden" name="page" />
				</form>
				<div id="searchUI" class="form-inline d-flex justify-content-center">
					<select name="searchType" class="form-control mr-2">
						<option value>전체</option>
						<option value="title">제목</option>
						<option value="writer">작성자</option>
						<option value="content">내용</option>
						<option value="type">게시판종류</option>
					</select>
					<input class="form-control mr-2" type="text" name="searchWord" value="${pagingVO.searchMap.searchWord }"/>
					<input class="form-control mr-2" type="date" name="minDate" value="${pagingVO.searchMap.minDate }" />
					<input class="form-control mr-2" type="date" name="maxDate" value="${pagingVO.searchMap.maxDate }"/>
					<input class="btn btn-primary mr-2" id="searchBtn" type="button" value="검색" />
					<input class="goBtn btn btn-success mr-2" type="button" value="새글쓰기" 
						data-gopage="<c:url value='/board/boardInsert.do'/>"
					/>
					<input class="goBtn btn btn-info" type="button" value="공지글쓰기"
						data-gopage="${cPath}/board/noticeInsert.do"
					/>
					<!-- submit -> button으로 변경  -->
						<!-- 버튼을 누르면 searchForm 으로 정보가 넘어가게 처리해야 한다.  -->
				</div>
				<div id="pagingArea" class="d-flex justify-content-center">
					${pagingVO.pagingHTMLBS }
				</div>
			</td>
		</tr> 
	</tfoot>
</table>
<script type="text/javascript">
	$(".goBtn").on("click", function(){
		let url = $(this).data("gopage");
		if(url)
			location.href = url;
	});
	
	let searchUI = $("#searchUI");
	searchUI.find("[name='searchType']").val("${pagingVO.searchMap.searchType }");
	$("#searchBtn").on("click", function(){
		let inputs = searchUI.find(":input[name]");
		$(inputs).each(function(idx, input){
			let name = $(this).attr("name");
			let sameInput = searchForm.find("[name='"+name+"']");
			$(sameInput).val($(this).val());
		});
		searchForm.submit();
	});
	
	let pagingArea = $("#pagingArea").on("click", "a", function(event){
		event.preventDefault();
		let page = $(this).data("page");
		if(page){
			searchForm.find("[name='page']").val(page);
			searchForm.submit();
		}
		return false;
	});
	
	let listBody = $("#listBody");
	
	let searchForm = $("#searchForm").ajaxForm({
		dataType:"json"
		, beforeSubmit:function(){
			searchForm.find("[name='page']").val("");	
		}, success:function(resp){
			listBody.empty();
			pagingArea.empty();
			let trTags = [];
			if(resp.dataList){
				let viewURLPtrn = "${cPath}/board/boardView.do?what=%d";
				$(resp.dataList).each(function(idx, board){
					let viewURL = viewURLPtrn.replace("%d", board.bo_no);
					let aTag = $("<a>").html(board.bo_title)
									   .attr("href", viewURL);
					if(board.bo_seq == 'Y'){
						aTag.addClass('secret');
					}else{
						aTag.addClass('nonsecret');
						aTag.data("toggle", 'popover');
						aTag.attr("title", board.bo_title);
					}
					let tr = $("<tr>").append(
								  $("<td>").html(board.bo_type == 'NOTICE'?'공지':'일반')
								, $("<td>").html(board.bo_no)
								, $("<td>").html(
									$("<img>").addClass("thumbnail")
											  .attr("src", board.thumbnail)
								)
								, $("<td>").html(aTag)
								, $("<td>").html(board.bo_writer)
								, $("<td>").html(board.bo_date)
								, $("<td>").html(board.bo_hit)
								, $("<td>").html(board.bo_rec)
							).data("board", board).css("cursor", "pointer");
					trTags.push(tr);
				});
			}else{
				trTags.push( 
					$("<tr>").html(
						$("<td>").attr("colspan", "8")		
					)
				);
			}
			listBody.html(trTags);
			pagingArea.html(resp.pagingHTMLBS);
		}, error:function(xhr, resp, error){
			console.log(xhr);
		}
	});
	searchForm.submit();
	
	$("#pagingArea").on("click", "a", function(event){
		event.preventDefault();
		let page = $(this).data("page");
		if(page){
			searchForm.find("[name='page']").val(page);
			searchForm.submit();
		}
		return false;
	});
	
	$(function () {
		$("#listBody").on("mouseenter", "a.nonsecret", function(){
			$(this).popover({
				html:true
				, content:function(){
					let url = this.href;
					let retValue = null;
					$.ajax({
						url : url,
						dataType : "text",
						success : function(resp) {
							console.log(1);
							retValue = resp;
						},
						async : false,
						cache : true,
						error : function(xhr, error, msg) {
							console.log(xhr);
							console.log(error);
							console.log(msg);
						}
					});
					console.log(2);
					return retValue;
				}
			}).popover("show")
		}).on("mouseout", "a.nonsecret", function(){
			$(this).popover("hide");
		});
	});
</script>











