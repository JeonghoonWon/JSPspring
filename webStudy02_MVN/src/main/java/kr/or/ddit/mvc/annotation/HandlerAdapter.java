package kr.or.ddit.mvc.annotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerAdapter implements IHandlerAdapter {

	@Override
	public String invokHandler(RequestMappingInfo mappingInfo, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Object controllerObj 
			= mappingInfo.getCommandHandler();
		Method handlerMethod 
			= mappingInfo.getHandlerMethod();
		try {
			return (String) handlerMethod.invoke(controllerObj, req,resp);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ServletException(e);			
		}
	
	}

}
