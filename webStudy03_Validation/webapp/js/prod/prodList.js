/**
 * 
 */
	let listBody = $("#listBody").on("click","tr",function(){
		let prod = $(this).data("prod");
		// 상대경로를 사용해야 하지만 절대 경로를 써보자
		// 컨텍스트 패스 쓸 수 없기 때문에 jsp에서  지정해준다.
		location.href=$.getContextPath()+"/prod/prodView.do?what="+prod.prod_id
	});
	
	let pagingArea = $("#pagingArea")
	let prod_buyerTag = $("[name='prod_buyer']");
	$("[name='prod_lgu']").on("change", function(){
	   //어떤 분류가 선택되는지 알아야한다.
	   let selectedLgu = $(this).val();
	   if(selectedLgu){
	      prod_buyerTag.find("option").hide();//buyerTag의 모든 값들을 숨겨놓음
	      //그다음 selectedLgu랑 같은 것은 보여줌
	      prod_buyerTag.find("option."+selectedLgu).show();
	   }else{
	      prod_buyerTag.find("option").show();
	   }
	   prod_buyerTag.find("option:first").show();
	});
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
				$(resp.dataList).each(function(idx, prod){
					let tr =  $("<tr>").append(
									  $("<td>").text(prod.rnum)		
									, $("<td>").text(prod.prod_id)		
									, $("<td>").text(prod.lprod_nm)		
									, $("<td>").text(prod.prod_name)		
									, $("<td>").text(prod.buyer.buyer_name)
									, $("<td>").text(prod.prod_cost)		
									, $("<td>").text(prod.prod_price)		
									, $("<td>").text(prod.prod_mileage)		
								).data("prod",prod).css("cursor","pointer");
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
	
	$("#newBtn").on("click", function(){
		location.href = $.getContextPath() + "/prod/prodInsert.do";
	});
	