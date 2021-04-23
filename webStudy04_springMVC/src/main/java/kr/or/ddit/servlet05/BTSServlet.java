package kr.or.ddit.servlet05;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// model1 에서 model2로 이동
@WebServlet(value = "/bts", loadOnStartup=1)
public class BTSServlet extends HttpServlet {
	Map<String, String> BTSMap;
	
	@Override
	// init 생성될때 모든 클라이언트가 공통으로 사용할 수 있는 어플리케이션 스코프 생성 .
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		BTSMap =new LinkedHashMap<>();
		BTSMap.put("bui","bui");
		BTSMap.put("jhop","jhop");
		BTSMap.put("jin","jin");
		BTSMap.put("jungkuk","jungkuk");
		BTSMap.put("rm","rm");
		
		BTSMap.put("suga","suga");
	
		config.getServletContext().setAttribute("BTSMap", BTSMap);
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// forward 방식으로 btsForm 으로 이동
		// web-inf 로 이동하기 때문에 forword 방식으로 이동
		String view = "/WEB-INF/views/btsForm.jsp";
		req.getRequestDispatcher(view).forward(req, resp);
		// 선택한 멤버
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String member = req.getParameter("member");	// jsp의 name 값
		System.out.println(member);
		int status = validate(member);
		if(status !=200) {
			resp.sendError(status);
			return;
			
		}
		
		String view = "/WEB-INF/views/bts/"+member+".jsp";
		
		//req.getRequestDispatcher(view).include(req,resp);
		req.getRequestDispatcher(view).forward(req,resp);
		//resp.sendRedirect(req.getContextPath() + view);
		
	}
	// 1명의 클라이언트가 요청한 것이기 때문에 bloodForm.jsp 랑 Servlet 정보 
	private int validate(String member) {
		int status = 200;
		if(member == null || member.isEmpty()) {
			status = 400;
		}else {
			if(!BTSMap.containsKey(member)) {
				status = 400;
			}
		}
		return status;
	}
	

}