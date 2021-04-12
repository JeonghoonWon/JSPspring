package kr.or.ddit.mvc;

import java.io.IOException;
import java.util.IdentityHashMap;

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

public class FrontController extends HttpServlet {
	private IHandlerMapping handlerMapping;
	private IHandlerAdapter handlerAdapter;
	private IViewResolver viewResolver;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
			super.init(config);
			String packageParam 
				= config.getInitParameter("basePackages");
			String[] basePackages = packageParam.split("\\s+");
			handlerMapping = new HandlerMapping(basePackages);
			handlerAdapter = new HandlerAdapter();
			viewResolver = new ViewResolver();
			viewResolver.setPrefix(config.getInitParameter("prefix"));
			viewResolver.setSuffix(config.getInitParameter("suffix"));
			
	
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//req.setCharacterEncoding("UTF-8"); frontController 에서 수행하던  encoding 을 filter 에서 진행하게끔 처리해보자
		
		RequestMappingInfo mappingInfo 
	   		= handlerMapping.findCommandHandler(req); 
	   if(mappingInfo==null) {
		   resp.sendError(404,"현재 요청을 처리할 수 있는 핸들러가 없음.");
		   return;
	   }
	   
	   String viewName 
	   		= handlerAdapter.invokeHandler(mappingInfo, req, resp);
	   
	   if(viewName==null) {
		   if(!resp.isCommitted()) { // back-controller 에서 작업이 완료된 경우
			   resp.sendError(500,"논리적인 view name 은 null일 수 없음");
		   }
	   }else {
		   viewResolver.viewResolve(viewName, req,resp);
	   }
	}
	
}
