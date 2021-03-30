<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>08/parameterDesc.jsp</title>
<jsp:include page="/includee/preScript.jsp"></jsp:include>
</head>
<body>
<form enctype="application/x-www-form-urlencoded" id = "paramForm" action="<%=request.getContextPath() %>/06/parameters" method = "post">
	<!-- enctype="application/x-www-form-urlencoded" : post 방식일때만 적용이 된다.-->
	<pre>
	<!-- pre 태그를 활용하면 일일이 <br>태그 삽입 해주지않아도 개행효과 낼 수 있음 -->
		<input type = "hidden" name = "param1" value = "hiddenParam"/>
		<!--<input type=“hidden”>은 사용자에게는 보이지 않는 숨겨진 입력 필드를 정의  -->
		<input type = "text" name = "param1" placeholder = "param1" />
		<input type = "text" name = "param1" placeholder = "param1" />
		<input type = "text" name = "param2" placeholder = "param2" /> <!-- placeholder : text field 내의 짧은 도움말 -->
		<select name = "param2">
			<option>option1</option>
			<option>option2</option>
			<option>option3</option>
		</select>
		
		<select name = "param3" multiple>
			<option>option1</option>
			<option>option2</option>
			<option>option3</option>
		</select>
		<input type ="checkbox" name = "param4" value = "1">CHECK1
		<input type ="checkbox" name = "param4" value = "2">CHECK2
		<input type ="checkbox" name = "param4" value = "3">CHECK3
		<input type ="radio" name = "param5" value ="RADIO1">RADIO1
		<input type ="radio" name = "param5" value ="RADIO2">RADIO2
		<!-- 동그라미 선택란  -->
		<input type ="button" value = "버튼" />
		<input type ="submit" value = "전송" />
		<input type ="reset" value = "취소" />
	</pre>	
</form>
<script type = "text/javascript">	//동기를 비동기로 바꾸기위한 것. 
	$.fn.serializeJson=function(){
	// form 태그에 있는 데이터를 읽어서 마샬링할때 좋?...
		let tagName = this.prop("tagName").toLowerCase()	
		if(this.length != 1 || tagName !="form")
			throw "form 에 대해서만 호출 가능한 함수";
		let array = this.serializeArray();
// 		console.log(array);
		let obj = {}
// 		obj.param1 = ["값1","값2"]	// 식별자가 된다.
		$(array).each(function(){
			console.log(this);
			let name = this.name;
			let value = this.value;
			if(obj[name] && Array.isArray(obj[name])){
				value = obj[name].concat(value);
			}else if(obj[name] ||(typeof obj[name] =="string" && obj[name].trim()=="")){
				
				let tempArray = [obj[name]];
				tempArray.push(value);
				value = tempArray;
			} // if~else end
			
			obj[name] = value;
			console.log(obj);
		}); //each end
			return obj;
	// 확인을 위해 Client Debugging 필요
	}
	$("#paramForm").serializeJson();
	
//동기에서 비동기로 바뀌더라도 넘어가는 데이터값은 똑같다. 

	$("#paramForm").on("submit",function(event){
		event.preventDefault();
		let url = this.action;
		let method = this.method;
//		let enctype = this.enctype;
// 		let data = $(this).serialize(); 	 //객체를 문자열화 
//		let data = $(this).serializeArray(); // 배열화 
		let enctype = "application/json;charset=UTF-8"
		let data = $(this).serializeJson();
		data = JSON.stringify(data);
		console.log(data);
		$.ajax({
			url : url
			, method : method
			, contentType: enctype
			, data : data
		});
		
		return false;
		
	});
</script>

</body>
</html>