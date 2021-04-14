/**
 * 
 */
let listBody = $("#listBody").on("click", "tr" ,function(){
	let buyer = $(this).data("buyer");
	location.href=$.getContextPath()+"/buyer/buyerView.do?buyer="+buyer.buyer_id;
});

let pagingArea = $("#pagingArea");

let searchForm = $("#searchForm").on("change", ":input[name]", function(){
	searchForm.submit();
}).ajaxForm({
	dataType:"json"
	, beforeSubmit:function(){
		searchForm.find("[name='page']").val("");	
	}, success:function(resp){
		listBody.empty();
		pagingArea.empty();
		let trTags = [];
		if(resp.dataList){
			$(resp.dataList).each(function(idx, buyer){
				let tr = $("<tr>").append(
							$("<td>").text(buyer.rnum)
							, $("<td>").text(buyer.buyer_id)
							, $("<td>").text(buyer.buyer_name)
							, $("<td>").text(buyer.lprod_nm)
							, $("<td>").text(buyer.buyer_bank)
							, $("<td>").text(buyer.buyer_bankno)
							, $("<td>").text(buyer.buyer_bankname)
							, $("<td>").text(buyer.buyer_zip)
							, $("<td>").text(buyer.buyer_add1)
							, $("<td>").text(buyer.buyer_add2)
							, $("<td>").text(buyer.buyer_comtel)
							, $("<td>").text(buyer.buyer_fax)
							, $("<td>").text(buyer.buyer_mail)
							, $("<td>").text(buyer.buyer_charger)
							, $("<td>").text(buyer.buyer_telext)
							, $("<td>").text(buyer.buyer_delete)
						).data("buyer", buyer).css("cursor", "pointer");
				trTags.push(tr);
			});
		}else{
			trTags.push( 
				$("<tr>").html(
					$("<td>").attr("colspan", "16")		
				)
			);
		}
		listBody.html(trTags);
		pagingArea.html( resp.pagingHTML );
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