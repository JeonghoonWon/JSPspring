/**
 * 
 */
$.test=function(){
	alert("TEST");
}
$.fn.test2=function(){
	alert("TEST2"+this.attr("method"));
	return this;
};
$.fn.formToAjax = function(param){//$("form").formToAjax식으로 사용가능
	this.on("submit", function(event){
		event.preventDefault();
		let url = this.action;
		let method = this.method;
		let inputs = $(this).find(":input");//:input이면 select, input모두 가져올수 있다. 입력태그를 가져옴
		let data = {}
		$(inputs).each(function(index, input){
			let name = $(this).attr("name");
			let value = $(this).val();
			data[name] = value;
			console.log(data);
		});
		$.ajax({
			//url이 생략되면 현재 브라우저의 값이 들어간다 => /bts_sem
			url : url,
			method : method,
			data : data,
			dataType : param.dataType,//특정 엘리먼트에 종속되지 않게 하기 위해
			success : param.success,
			error : function(xhr, error, msg) {
				console.log(xhr);
				console.log(error);
				console.log(msg);
			}
		})
		return false;
	});
	return this;
}