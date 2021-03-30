package kr.or.ddit.servlet01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Abstract 자손이 결정이 되어야만 객체를 생성할 수 있다.
//abstract class : (추상 클래스)인터페이스의 역할도 하면서 뭔가 구현체도 가지고 있는 자바의 돌연변이 같은 클래스
public abstract class AbstractUserTmplServlet extends HttpServlet {
	protected ServletContext application;
	
	// protected : 동일 패키지에 속하는 클래스와 하위 클래스 관계의 클래스에 의해 접근이 가능하다. 


	// ServletContext :서블릿 컨테이너와 통신하기 위해서 사용되는 메소드를 지원하는 인터페이스
	// ServletContext는 범위를 application으로 한다면, ServletConfig는 servlet 내부로 한정
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = config.getServletContext();
	}
	
	@Override //서버가 요청 
	// service : template method 호출만 하고있음.
	// 
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.service(req, resp); 순서를 없앤다.
		// 0. mime 결정
		setContentType(resp);
		// 1. tmpl 읽기
		StringBuffer tmplSrc = readTmpl(req);
		// 2. 데이터 만들기
		makeData(req);
		// 3. tmpl을 데이터로 치환
		StringBuffer html = replaceData(tmplSrc, req); //치환이 필요한 구멍과 그 구멍을 채워줄 데이터
		// 4. 응답을 전송 try ~ width resource
		try(
			PrintWriter out = resp.getWriter();
		){
			out.print(html);
		}
	}

	protected abstract void setContentType(HttpServletResponse resp);
		
	//  StringBuffer : 문자열을 추가하거나 변경 할 때 주로 사용하는 자료형
	
	private StringBuffer replaceData(StringBuffer tmplSrc, HttpServletRequest req) {
		 Pattern regex = Pattern.compile("%([a-zA-Z0-9_]+)%"); 
		 Matcher matcher = regex.matcher(tmplSrc);
		 StringBuffer html = new StringBuffer(); // 
		 while(matcher.find()) {
			 String name = matcher.group(1); //정규표현식은 문자열
			 Object value = req.getAttribute(name); //치환해야 하는 값
			 if(value != null){
				 matcher.appendReplacement(html, value.toString()); //일일히 치환하지 않아도 된다.
			 }
		 }
		 matcher.appendTail(html); 	//find 이후 부분까지 matcher 하기 위해.
		 return html;
	}
	protected abstract void makeData(HttpServletRequest req);
	// 작업의 세부적인 내용은 언제든지 변경 가능하나 순서는 변경되지 않는다 -> 템플릿 메소드 패턴

	private StringBuffer readTmpl(HttpServletRequest req) throws IOException {
		String tmplPath = req.getServletPath();
		InputStream is = application.getResourceAsStream(tmplPath); //inputStream은 1byte단위
		InputStreamReader isr = new InputStreamReader(is); //inputStream과 bufferReader의 중간 젠더 역할
		BufferedReader reader = new BufferedReader(isr); //BufferReader는 2byte단위
		
		String temp = null;
		StringBuffer tmplSrc = new StringBuffer(); //한줄씩 읽어오기 때문에 concat 연산자를 쓰지 말자
		while((temp = reader.readLine()) != null) {
			tmplSrc.append(String.format("%s\n", temp));
		}
		return tmplSrc;
	}
}