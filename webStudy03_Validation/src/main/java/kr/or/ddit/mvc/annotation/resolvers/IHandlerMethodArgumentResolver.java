package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IHandlerMethodArgumentResolver {
	// 1.8 버전부터 파라미터 가능???
	public boolean isSupported(Parameter parameter);
	// 요청 파라미터를 하나 만들어 내는것
	public Object argumentResolve(Parameter parameter, 
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}
