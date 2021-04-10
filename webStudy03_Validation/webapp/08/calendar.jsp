<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.util.Calendar"%>
<%@page import="static java.util.Calendar.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String yearParam = request.getParameter("year");
	String monthParam = request.getParameter("month");
	String loc = request.getParameter("loc");
	String zoneParam = request.getParameter("timeZone");

	Locale locale = request.getLocale();
	TimeZone zone = TimeZone.getDefault();
	if(loc!=null && !loc.isEmpty()){
		locale = Locale.forLanguageTag(loc);
	}
	if(zoneParam!=null && !zoneParam.isEmpty()){
		zone = TimeZone.getTimeZone(zoneParam);
	}
	
	final Calendar TODAY = getInstance(zone, locale);
	
	Calendar calendar = getInstance(locale);
	
	if(yearParam!=null && yearParam.matches("\\d{4}")){
		calendar.set(YEAR, Integer.parseInt(yearParam));
	}
	if(monthParam!=null && monthParam.matches("\\d|1[0-1]")){
		calendar.set(MONTH, Integer.parseInt(monthParam));
	}
	
	calendar.set(DAY_OF_MONTH, 1);
	int dayOfWeek = calendar.get(DAY_OF_WEEK);
	int offset = dayOfWeek - 1;
	int maxDate = calendar.getActualMaximum(DAY_OF_MONTH);
	
	DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
	
	calendar.add(MONTH, -1);
	int beforeYear = calendar.get(YEAR);
	int beforeMonth = calendar.get(MONTH);
	
	calendar.add(MONTH, 2);
	int nextYear = calendar.get(YEAR);
	int nextMonth = calendar.get(MONTH);
	calendar.add(MONTH, -1);
	
	int year = calendar.get(YEAR);
	int month = calendar.get(MONTH);
	
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.sunday{
		background-color: red;
	}
	.saturday{
		background-color: blue;
	}
	.current{
		background-color: green;
	}
</style>
</head>
<body>
<h4><%=String.format(locale, "%tc", TODAY) %></h4>
<h4>
	<a onclick="return clickHandler(event);" href="#" 
		data-year="<%=beforeYear%>" data-month="<%=beforeMonth%>">◀◀◀</a>
	<%=String.format("%1$tY, %1$tB", calendar) %>
	<a onclick="return clickHandler(event);" href="#"
		data-year="<%=nextYear%>" data-month="<%=nextMonth%>">▶▶▶</a>
</h4>
<form name="calForm">
	<input type="number" name="year" placeholder="년도" 
		value="<%=year %>" 
		onchange="this.form.submit();"	
	/>
	<select name="month" onchange="this.form.submit();">
		<%
			String[] months = dfs.getMonths();
			for(int i=0; i<months.length-1; i++){
				%>
				<option <%=i==month?"selected":"" %> value="<%=i%>">
					<%=months[i] %>
				</option>
				<%
			}
		%>
	</select>
	<select name="loc" onchange="this.form.submit();">
	<%
		for(Locale tmp : Locale.getAvailableLocales()){
			String dL = tmp.getDisplayLanguage(tmp);
			String dc = tmp.getDisplayCountry(tmp);
			if(dL.isEmpty() && dc.isEmpty()) continue;
			String selected = tmp.equals(locale) ? "selected" : "" ;
			%>
			<option <%=selected %> value="<%=tmp.toLanguageTag()%>"><%=String.format("%s[%s]", dL, dc) %></option>
			<%
		}
	%>
</select>
<select name="timeZone" onchange="this.form.submit();">
	<%
		for(String tmpId : TimeZone.getAvailableIDs()){
			TimeZone tmpZone = TimeZone.getTimeZone(tmpId);
			%>
			<option <%=tmpZone.equals(zone)?"selected":"" %> value="<%=tmpId %>"><%=tmpZone.getDisplayName() %></option>
			<%
		}
	%>
</select>
</form>
<table>
	<thead>
	<tr>
	<%
		String[] weekDays = dfs.getWeekdays();
		for(int i=SUNDAY; i<=SATURDAY; i++){
			%>
			<th><%=weekDays[i] %></th>
			<%
		}
	%>
	</tr>
	</thead>
	<tbody>
	<%
		
		calendar.add(DATE, -offset);
		for(int row=1; row<=6; row++){
	%>
		<tr>
		<% 
			for(int col=1; col<=7; col++){
				int date = calendar.get(DATE);
				String clz = col==SUNDAY?"sunday" :
								col==SATURDAY?"saturday" :
									(year==TODAY.get(YEAR) && 
									month==TODAY.get(MONTH) && 
									TODAY.get(DATE)==calendar.get(DATE))?"current":"nomal";
		%>
				<td class="<%=clz %>"><%=date %></td>
		<%
				calendar.add(DATE, 1);
			}
		%>	
		</tr>
	<%
		}
	%>
	</tbody>
</table>
<script type="text/javascript">
	let calForm = document.calForm;
	if(!calForm) calForm = document.querySelector("[name='calForm']");
	function clickHandler(event){
		event.preventDefault();
		let dataset = event.target.dataset;
		calForm.year.value = dataset.year; 
		calForm.month.value = dataset.month;
		calForm.submit();
		return false;
	}
</script>
</body>
</html>















