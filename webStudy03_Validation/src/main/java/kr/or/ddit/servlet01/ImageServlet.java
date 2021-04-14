package kr.or.ddit.servlet01;

import javax.servlet.http.*;

import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.io.*;
import java.net.URLEncoder;

//@WebServlet("/01/image.do") //클라이언트가 정보를 선택하고 넘겨준다.
@Controller
public class ImageServlet {

	@RequestMapping("/01/image.do")
	public String doGet(@RequestParam("image") String imageFilename, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String folder = req.getServletContext().getInitParameter("contentFolder");

		// 파라미터는 넘어온 상태 하지만 넘어온 파라미터가 존재하는가?
		File imageFile = new File(folder, imageFilename);
		if (!imageFile.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		String mime = req.getServletContext().getMimeType(imageFilename);
		if(mime == null || !mime.startsWith("image/")) {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			return null;
		}

		resp.setContentType(mime);
		try (

				FileInputStream fis = new FileInputStream(imageFile);
				OutputStream os = resp.getOutputStream();) {

			byte[] buffer = new byte[1024];
			int pointer = -1; // fis.read를 읽어와서 pointer에 할당한다.
			while ((pointer = fis.read(buffer)) != -1) {
				os.write(buffer, 0, pointer);
				// 9.5의 파일을 10번 읽는다면 10번째의 반절은 현재 데이터 그 반절은 전에 데이터
				// 때문에 어디까지 읽어야 한다는것을 지정한다.
			}

		}
		return null;
	}

}