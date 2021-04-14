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
import kr.or.ddit.mvc.annotation.resolvers.RequestParamArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ServletSpecArgumentResolver;

public class HandlerAdapter implements IHandlerAdapter {
	private List<IHandlerMethodArgumentResolver>  argumentResolvers;
	
	// 생성자
	public HandlerAdapter(){
		argumentResolvers = new ArrayList<>();
		argumentResolvers.add(new ServletSpecArgumentResolver());
		argumentResolvers.add(new ModelAttributeArgumentResolver());
		argumentResolvers.add(new RequestParamArgumentResolver());
	}
	
	private IHandlerMethodArgumentResolver findArgumentResolver(Parameter parameter) {
		IHandlerMethodArgumentResolver finded = null;
		// 리스트에서 
		for(IHandlerMethodArgumentResolver resolver : argumentResolvers) {
			if(resolver.isSupported(parameter)) {
				// parameter가 있으면 if 안으로 들어가도록
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
		Method handlerMethod 
			= mappingInfo.getHandlerMethod();
		// 파라미터의 갯수를 가장 먼처 찾아야 함.
		int parameterCount = handlerMethod.getParameterCount();
		Parameter[] parameters = handlerMethod.getParameters();
		try {
			if(parameterCount == 0 ) {
				return (String) handlerMethod.invoke(controllerObj); 
			}
			Object[] parameterValues = new Object[parameterCount]; // 파라미터를 넣을 Object배열을 만들고, parameterCount로 길이 지정
			
			for(int idx = 0; idx < parameterCount; idx++) {
				
				Parameter parameter = parameters[idx];
				 Class<?> parameterType = parameter.getType();
				IHandlerMethodArgumentResolver resolver 
							= findArgumentResolver(parameter);
				if(resolver==null)
					// 서버에서 500 에러를 보낼 수 있도록 ServletException 처리
					throw new ServletException(
								String.format("%s 타입의 핸들러 메서드 아규먼트는 처리할 수 없음.", parameterType.getName())
							);
				parameterValues[idx] = resolver.argumentResolve(parameter, req, resp);
				
				// 어떤 엘리먼트중 처리할 수 있는 파라미터 아규먼트를 찾아내야 함.
			}
			return (String) handlerMethod.invoke(controllerObj,parameterValues);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ServletException(e);			
		}catch (BadRequestException e) {
			resp.sendError(400,e.getMessage());
			return null;
		}
	
	}

}
