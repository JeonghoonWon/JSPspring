/**
 * 
 */
 	let listBody = $("#listBody").on("click","tr",function(){
		let board = $(this).data("board");
		
	location.href=$.getContextPath()+"board/boardView.do?what="+board.bo_no
	});	
 	let pagingArea = $("#pagingArea")
 	
 	let searchForm = $("#searchForm").on("change", ":input[name]", function(){
	   //this.submit();=>this는 :input[name]이 된다.
	   searchForm.submit();
	 //ajaxFrom은 내부적으로 ajax의 함수를 사용한다.
	}).ajaxForm({
			dataType:"json" 
			,beforeSubmit:function(){
				searchForm.find("[name='page']").val("")
			}
			//"html/xml/text/json/script" 중 어떤게 가장 효율적일까?
			// 데이터만 받아오기때문에 json/xml 이 좋은데 xml 은 무겁기 때문에 json 사용한다.
			, success : function(resp){
				listBody.empty();
				pagingArea.empty();
				let trTags=[];
				if(resp.dataList){
				$(resp.dataList).each(function(idx, board){
					let tr =  $("<tr>").append(
									  $("<td>").text(board.bo_type)		
									, $("<td>").text(board.bo_no)		
									, $("<td>").text(board.bo_title)		
									, $("<td>").text(board.bo_writer)		
									, $("<td>").text(board.bo_date)
									, $("<td>").text(board.bo_hit)		
									, $("<td>").text(board.bo_rec)		
	
								).data("board",board).css("cursor","pointer");
					trTags.push(tr);
				});					
				}else{
					trTags.push( 
						$("<tr>").html(
							$("<td>").attr("colspan","8")
						)
					);
					
				}
				listBody.html(trTags);
				pagingArea.html(resp.pagingHTML);
			}, error : function(xhr, resp, error){
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