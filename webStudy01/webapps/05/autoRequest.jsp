<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- <meta http-equiv="Refresh" content="10;url=http://www.naver.com"> 10초 뒤에 네이버로 이동  -->

<title>05/autoRequest.jsp</title>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<h4>Auto Request Header</h4>
	1) Refresh 헤더를 이용한 방식.
	<br>
	<br>

	<%--
		response.setIntHeader("Refresh",1);
	/* REfresh의 단점 : 동기형식으로 계속 보내기때문에 다른 작업을 할 수 없다. */
	--%>

	2) html 의 meta 태그 이용
	<br>
	<br>
	<!-- refresh 가 바디에 있지만 헤더에서 역할을 하게 되는것 -->
	3) JS 의 스케쥴링 함수 이용 : setInterval, setTimeout
	<br>
	<br>
	<h4>
		현재 서버의 시각 : <span id="serverTimer"></span>
	</h4>
	<h4>
		현재 클라이언트의 시각 : <span id="clientTimer"></span>
	</h4>
	<h4>
		<span id="countdown"></span>초 뒤에 네이버로 감.
	</h4>
	<!-- 
매 1초마다 비동기 요청을 발생시키고,
클라이언트에게 사용 언어를 입력 받을 것.
클라이언트에게 가져오고싶은 시간대를 입력 받을 것
 -->
	<select name="loc">


		<%
			for (Locale tmp : Locale.getAvailableLocales()) {
				String dL = tmp.getDisplayLanguage(tmp);
				String dc = tmp.getDisplayCountry(tmp);
				if (dL.isEmpty() && dc.isEmpty())
					continue;
		%>
		<option value="<%=tmp.toLanguageTag()%>"><%=String.format("%s[%s]", dL, dc)%></option>

		<%
			}
		%>
	</select>

	<select name="timeZone">
		<%
			for (String tmpId : TimeZone.getAvailableIDs()) {
				TimeZone tmpZone = TimeZone.getTimeZone(tmpId);
		%>
		<option value="<%=tmpId%>"><%=tmpZone.getDisplayName()%></option>
		<%
			}
		%>

	</select>

	<script type="text/javascript">
	var span = document.querySelector("#countdown");
	var locSel = $("[name='loc']");
	var tzSel = $("[name='timeZone']");
	var serverTimer = $("#serverTimer");
	var clientTimer = $("#clientTimer");
	
	const INITTIME = 10;
	var timer = INITTIME;	
	var jobId = setInterval(() => {
		span.innerHTML = --timer;
		if(timer==0)clearInterval(jobId);		
	}, 1000);
	
	// 비동기 방식의 ajax에서는 Refresh는 작동하지않는다. 동기방식임
	
	setInterval(function(){
		var data = {
			loc : locSel.val()
			, zone : tzSel.val()
		}
		var df =  new Intl.DateTimeFormat(
				new Intl.Locale(data["loc"]),{
			dataStyle : "long"
			, timeStyle : "long"
		}
		
		);
		clientTimer.text(df.format(new Date()));	// 내부적으로 toString 함수 가 호출되서 출력 된다.
		$.ajax({
			url : "<%=request.getContextPath()%>/04/serverTime",
			data: data,
			dataType : "text",
			success : function(resp) {
					serverTimer.text(resp);
			},
			error : function(xhr, error, msg) {
				// xhr : XMLHttpRequest (XHR)은 AJAX 요청을 생성하는 JavaScript API입니다. XHR의 메서드로 브라우저와 서버간의 네트워크 요청을 전송할 수 있습니다

				console.log(xhr);
				console.log(error);
				console.log(msg);

			}

		});
		
	}, 1000);
	
	/* setTimeout(function(){
		location.reload();
	}, 1000); */
</script>












</body>
</html>