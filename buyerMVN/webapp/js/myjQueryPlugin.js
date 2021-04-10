/**
 * 
 */
$.test = function(){
	alert("TEST");
}
$.fn.test2=function(){
	alert("Test2"+this.attr("method"));
	// 제이쿼리는 꼬리에 꼬리를 무는 체인형태가 가능하다. return this; 가 붙어있기때문에
	return this;
};
$.fn.formToAjax=function(param){
		this.on("submit",function(event){
		event.preventDefault();
		let url = this.action;
		let method = this.method;
		let inputs = $(this).find(":input");  /* :input -> 모든 입력 태그를 가지고 오겠다. */
		let data = {}
		$(inputs).each(function(index, input){
			let name  = $(this).attr("name");
			let value = $(this).val();
		data[name] = value;
		});
	
		$.ajax({
			url : url,
			/* url : "",  : url을 제거하면 현재 사용중인 브라우져의 주소가 사용된다.*/ 
			method : method,
			data : data,
			dataType :param.dataType,
			success : param.success,
			error : function(xhr, error, msg) {
				// xhr : XMLHttpRequest (XHR)은 AJAX 요청을 생성하는 JavaScript API입니다. XHR의 메서드로 브라우저와 서버간의 네트워크 요청을 전송할 수 있습니다
				console.log(xhr);
				console.log(error);
				console.log(msg);

		}

		});
	 
		return false;
	});	
	return this;
}