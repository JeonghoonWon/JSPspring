
package kr.or.ddit.servlet01;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.io.*;


@WebServlet("/01/video.do")
public class videoServlet extends HttpServlet{
		// HttpServlet 을 상속 받는 클래스는 doget()메소드를 오버라이딩 할 수 있기 때문에 
		// 이 클래스는 서블리으로 동작 가능

	
		/*
	 	1. 서블릿 기본
	 		- 자바 코드로 작성
	 		- 자바 코드를 컴파일해서 클래스 파일을 생성
	 		- 클래스 파일을 /WEB-INF/classes 디렉토리에 패키지 알맞게 위치
	 		- web.xml 파일에 서블릿 클래스 설정(필요한 경우)
	 		- tomcat 등의 컨테이너 재실행
	 		- 웹 브라우저에서 확인
	 	*/
	protected void doGet(HttpServletRequest req,
                     HttpServletResponse resp)
              throws ServletException,
                     IOException{
		String imageFilename = req.getParameter("video");
		if(imageFilename==null||imageFilename.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);	//상태를 알려준다. 프로그램이 인지할수있도록 숫자로 내보내서 클라이언트가 인지 할 수 있도록 한다. 잘못된 접근 방법(400)
			
			return;
			// 더 아래로 접근하지 못하도록 return 시킨다.
			
		}
		
		String folder = "d:/contents";
		File imageFile = new File(folder, imageFilename);
		// File(String dir, String name) : dir와 name 문자열을 연결한 문자열로
		// 경로를 생성하여 File 객체를 생성한다.
		if(!imageFile.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);	
		//404 error 400번때 error 는 client의 문제 / 500번때 error 는 개발자의 문제
			return;
			
		}
		
		String mime =  getServletContext().getMimeType(imageFilename);
		if(mime==null || !mime.startsWith("video/")) {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			return;
		}
		resp.setContentType(mime);                 
		FileInputStream fis = new FileInputStream(imageFile);
		OutputStream os = resp.getOutputStream();
		byte [] buffer = new byte[1024];
        int pointer = -1;
		while((pointer = fis.read(buffer))!= -1) {
            os.write(buffer, 0, pointer);

		}
		
	}
	
}