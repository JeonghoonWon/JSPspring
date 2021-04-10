<%@page import="kr.or.ddit.enumpkg.MimeType"%>
<%@page import="kr.or.ddit.enumpkg.OperatorType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Calculator</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		var functionMap = {
			json : function(resp){
				resultArea.text(resp.expression);
			},
			xml:function(resp){	//dom객체				
				resultArea.text($(resp).find("expression").text());
			},
			html:function(resp){ // sourse
				resultArea.html(resp);
			},
			plain:function(resp){ //text/plain
				resultArea.text(resp);
			}
		}
		
		var mimeKind = $("#mimeKind")
		var resultArea = $("#resultArea");
		// Jquery 는 함수의 체인 구조를 가지고 있다.
		// this 가 return 으로 돌아오기때문에
		var carForm = $("#calForm").on("submit",function(event){
			var dataType = mimeKind.val();
			if(!dataType){
				return true;
			}
			
			event.preventDefault(); 
			var action = this.action;
			var method = this.method;
			var data = $(this).serialize();	// 쿼리 스트링 이 생성
			var options = {
				url : action,
				method : method,
				data : data,
			// Accept Header : text/plain [html, javascript],application/json[xml]
			// dataType 이 하는 것
			// 1. accept Header
			// 2. content type response type 
			
			// ajax 는 기본적으로 가져오는 값을 문자열(text)형식이다.
				
				error : function(xhr, error, msg) {
					// xhr : XMLHttpRequest (XHR)은 AJAX 요청을 생성하는 JavaScript API입니다. XHR의 메서드로 브라우저와 서버간의 네트워크 요청을 전송할 수 있습니다

					console.log(xhr);
					console.log(error);
					console.log(msg);

				}

			}
			console.log(data);
			options.dataType = dataType;
			options.success = functionMap[options.dataType];		//functionMap.json : 데이터가 아닌 식별자를 가지고 오는것 .
			$.ajax(options);
			
			
			
			return false;	// submit 의 이벤트를 막아준다. 
			// 위에서 오류가 나면 return 까지 오지 않기 때문에 
			// event.preventDefault(); 를 써줘서 이벤트 발생을 막아준다.
			// java 는 인터프리터 형식이기 때문에
			
		});
	});
	

</script>
</head>
<body>

<!-- 
1. left, right 의 피연산자와 operator 이름의 연산자를 포함한 필수 파라미터 입력.
2. 연산의 종류 : 4칙 연산 지원
3. 입력 데이터는 실수형.
4. 파라미터 전송 : /03/calculator 의 방향으로 전송(전송 데이터는 비노출).
5. 연산의 결과 : ex) 3 * 4 = 12 와 같은 형태로 제공.

//////////////////////////////////////////////////////////////////

1. form action method action 의 방향은 /03/calculator  method = post
2. 실수형태로만 입력 받도록. from에는 3개 선택 할 수 있도록
3. .format 쓰기 
4. 확장자가 없으므로 uri  콜백 메서드 잇어야 함 필수 파라미터 받을수 있게.
5. 모든 검정 끝난 후 연산 결과 응답으로
 -->
 <!-- form 밖에 존재. 데이터를 전송할 목적 이 아님. -->
<select id = "mimeKind">
	<!-- 	
	<option>JSON</option>
	<option>XML</option>
	 -->
	 <option value>dataType 선택</option>
	 <%
	 	for(MimeType tmp : MimeType.values()){
	 		%>
	 		<option value="<%=tmp.name().toLowerCase()%>"><%=tmp.name() %></option>
	 		<%
	 		
	 	}
	 %>
</select>
 
 
 <form id="calForm" action = "<%= request.getContextPath() %>/03/calculator" method="post">	
 <!-- 클라이언트가 사용하는 경로 / 브라우져엔 contextPath 가 저장되지않기때문에 request.getContextPath() 사용해줘야함  -->
 	<input type = "number" name = "left" />
 	<%
 		for(OperatorType tmp :OperatorType.values()){
 			%>	
 			<label><input type = "radio" name="operator" value="<%=tmp.name()%>"/>	<%=tmp.getSign() %></label>
 			<%
 		}
 	%>
 	
 	
 	<input type = "number" name = "right" />
 	<button type = "submit">=</button> <!--   -->
 
 </form>
 <div id = "resultArea"></div>
 
</body>
</html>