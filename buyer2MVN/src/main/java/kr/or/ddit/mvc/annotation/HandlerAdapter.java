package kr.or.ddit.mvc.annotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.resolvers.BadRequestException;
import kr.or.ddit.mvc.annotation.resolvers.IHandlerMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttributeArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ReqeustParamArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ServletSpecArgumentResolver;

public class HandlerAdapter implements IHandlerAdapter {
	private List<IHandlerMethodArgumentResolver> argumentResolvers;
	public HandlerAdapter(){
		argumentResolvers = new ArrayList<>();
		argumentResolvers.add(new ServletSpecArgumentResolver());
		argumentResolvers.add(new ModelAttributeArgumentResolver());
		argumentResolvers.add(new ReqeustParamArgumentResolver());
	}
	
	private IHandlerMethodArgumentResolver findArgumentResolver(Parameter parameter) {
		IHandlerMethodArgumentResolver finded = null;
		for(IHandlerMethodArgumentResolver resolver : argumentResolvers) {
			if(resolver.isSupported(parameter)) {
				// 찾은 경우
				finded = resolver;
				break;
			}
		}
		return finded;
	}
	
	@Override
	public String invokeHandler(RequestMappingInfo mappingInfo, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Object controllerObj 
			= mappingInfo.getCommandHandler();
		Method handlerMethod = mappingInfo.getHandlerMethod();//method를 가져온다.
		//파라미터의 개수가 몇개 있는지를 확인해 봐야한다.
		int parameterCount = handlerMethod.getParameterCount();
		Parameter[] parameters = handlerMethod.getParameters();//parameter에 대한 정보를 리스트로 넘김
		try {
			//파라미터가 없을때 실행
			if(parameterCount==0) {
				return (String) handlerMethod.invoke(controllerObj);
			}
			
			Object[] parameterValues = new Object[parameterCount]; 
			for(int idx = 0; idx< parameterCount; idx++) {
				
				Parameter parameter = parameters[idx];
				Class<?> parameterType = parameter.getType();
				IHandlerMethodArgumentResolver resolver = findArgumentResolver(parameter);
				
				if(resolver==null)
					throw new ServletException(
						String.format("%s 타입의 핸들러 메소드 아규먼트는 처리할 수 없음", parameterType.getName())
					);
				
				parameterValues[idx] = resolver.argumentResolve(parameter, req, resp);
			}
			return (String) handlerMethod.invoke(controllerObj, parameterValues);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ServletException(e);
		} catch (BadRequestException e) {
			resp.sendError(400, e.getMessage());
			return null;
		}
	}
}