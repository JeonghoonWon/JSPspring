package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {@link HttpServletRequest}
 * {@link HttpServletResponse}
 * {@link HttpSession}
 * 위의 세가지 종류의 핸들러 메소드 아규먼트를 처리할 처리자.
 *
 */
public class ServletSpecArgumentResolver implements IHandlerMethodArgumentResolver {

	@Override
	public boolean isSupported(Parameter parameter) {
		//현재 파라미터가 내가 감당할 수 있는 것인지를 체크해준다.
		Class<?> parameterType = parameter.getType();
		boolean supported = 
				HttpServletRequest.class.equals(parameterType)
				|| HttpServletResponse.class.equals(parameterType)
				|| HttpSession.class.equals(parameterType);
		return supported;
	}

	//체크를 해준뒤에 감당할 수 있는 것이면 해결해줘야한다.
	@Override
	public Object argumentResolve(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		Object parameterValue = null;
		if(HttpServletRequest.class.equals(parameterType)) {
			parameterValue = req;
		}else if(HttpServletResponse.class.equals(parameterType)){
			parameterValue = resp;
		}else {
			//세션인경우 request를 통해 세션을 가져와서 넣어줌
			parameterValue = req.getSession();
		}
		return parameterValue;
	}
}