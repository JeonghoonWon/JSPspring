package kr.or.ddit.servlet01;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/01/videoForm.tmpl")
public class VideoFormServlet extends AbstractUserTmplServlet {
	@Override
	protected void setContentType(HttpServletResponse resp) {
		resp.setContentType("text/html;charset=UTF-8");
		
	}

	@Override
	protected void makeData(HttpServletRequest req) {
		System.out.println("서블릿이 요청 받았음");
		
		String folder = "d:/contents";
		File contents = new File(folder);
		String[] children = contents.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				String mime = application.getMimeType(name);
				return mime != null && mime.startsWith("video/"); //true를 반환하여 나온다.
				
			}
		});
			
		Date today = new Date();
		req.setAttribute("today", today);
		
		StringBuffer options = new StringBuffer();
		
		for(String child : children){
			options.append(String.format("<option>%s</option>", child));
		}
		req.setAttribute("options", options);
		
	}
	
	// 작업의 순서
	// 1. tmpl 파일을 읽어온다.
	// 2. 만들어야 할 데이터를 작성한다. (동적이라면 미리 만들 수 없다.)
	// 3. tmpl에 필요한 데이터를 치환한다.
	// 4. 출력
}