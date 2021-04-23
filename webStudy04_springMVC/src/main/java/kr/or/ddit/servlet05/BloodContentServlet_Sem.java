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
import javax.servlet.http.HttpSession;

/*
loadOnStartup 의 특징
 - 톰캣 컨테이너가 실행되면서 미리 서블릿을 실행
 - 지정한 숫자가 0보다 크면 톰캣 컨테이너가 실행되면서 서블릿이 초기화 된다.
 - 지정한 숫자는 우선순위를 의미하며, 작은숫자부터 먼저 초기화 된다.
*/
@WebServlet(value="/blood/getContent.do",loadOnStartup=1)
public class BloodContentServlet_Sem extends HttpServlet {
	Map<String, String> bloodMap;
	
	@Override
	// init 생성될때 모든 클라이언트가 공통으로 사용할 수 있는 어플리케이션 스코프 생성 .
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		bloodMap =new LinkedHashMap<>();
	 	bloodMap.put("a","A형");
	 	bloodMap.put("b","b형");
	 	bloodMap.put("ab","AB형");
	 	bloodMap.put("o","O형");
	 	config.getServletContext().setAttribute("bloodMap", bloodMap);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String blood = req.getParameter("blood");
		int status = validate(blood);
		if(status !=200) {
			resp.sendError(status);
			return;
			
		}
		
		String view = "/WEB-INF/views/blood/"+blood+".jsp";
		
		//req.getRequestDispatcher(view).include(req,resp);
		req.getRequestDispatcher(view).forward(req,resp);
		//resp.sendRedirect(req.getContextPath() + view);
		
	}
	// 1명의 클라이언트가 요청한 것이기 때문에 bloodForm.jsp 랑 Servlet 정보 
	private int validate(String blood) {
		int status = 200;
		if(blood == null || blood.isEmpty()) {
			status = 400;
		}else {
			if(!bloodMap.containsKey(blood)) {
				status = 400;
			}
		}
		return status;
	}
	

}