package kr.or.ddit.mvc;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.ddit.mvc.annotation.HandlerAdapter;
import kr.or.ddit.mvc.annotation.HandlerMapping;
import kr.or.ddit.mvc.annotation.IHandlerAdapter;
import kr.or.ddit.mvc.annotation.IHandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingInfo;

//전체적인 구조만을 가지고 있다.
public class FrontController extends HttpServlet{//유일한 서블릿이 됨
	private IHandlerMapping handlerMapping;
	private IHandlerAdapter handlerAdapter;
	private IViewResolver viewResolver;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String packageParam = config.getInitParameter("basePackages");
		String[] basePackages = packageParam.split("\\s+");//\\s=>공백하나, +=>하나이상
		handlerMapping = new HandlerMapping(basePackages);
		handlerAdapter = new HandlerAdapter();
		viewResolver = new ViewResolver();
		viewResolver.setPrefix(config.getInitParameter("prefix"));
		viewResolver.setSuffix(config.getInitParameter("suffix"));
	}
	
	//get이든 post이든 다 받아야하기때문에 service로 만듬
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		RequestMappingInfo mappingInfo 
			= handlerMapping.findCommandHandler(req);
		
		if(mappingInfo==null) {
			resp.sendError(404, "현재 요청을 처리할 수 있는 핸들러가 없음");
			return;
		}
		String viewName = handlerAdapter.invokeHandler(mappingInfo, req, resp);
		
		//null이면 redirect랑 forward를 하지 못함
		//개발자가 잘못 만들거나 이미 응답이 나간경우에 null값이 발생
		if(viewName==null) {
			if(!resp.isCommitted()) {
				//작업이 종료되지 않으면 => 응답이 끝나지 않았으면
				//즉 개발자가 잘못 만든경우
				resp.sendError(500, "논리적인 뷰네임은 널일수 없음.");
			}
			//응답데이터가 json으로 나갔기때문에 할일이 없는 경우 그냥 종료된다.
		}else {
			viewResolver.viewResolve(viewName, req, resp);
		}
	}
}