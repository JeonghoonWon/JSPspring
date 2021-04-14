package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ClassUtils;

/**
 * @ModelAttribute 어노테이션으로 설정된 핸들러 메소드 아규먼트 처리할 처리자
 *
 */
public class ModelAttributeArgumentResolver implements IHandlerMethodArgumentResolver {

	@Override
	public boolean isSupported(Parameter parameter) {
		//1.아규먼트가 있는지
		//2.데이터의 형태가 double, String, int등의 기본형타입이 아니여야하고, Mapper도 아니어야한다.
		Class<?> parameterType = parameter.getType();
		ModelAttribute annotation = parameter.getAnnotation(ModelAttribute.class);
		//annotation이 붙어있는지 확인할려면
		boolean supported = annotation!=null 
						&& !(
								String.class.equals(parameterType)
								|| ClassUtils.isPrimitiveOrWrapper(parameterType)//기본형이든 Integer의 classType도 확인이 가능
							);
		return supported;
	}

	@Override
	public Object argumentResolve(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		ModelAttribute annotation = parameter.getAnnotation(ModelAttribute.class);
		try {
			Object parameterValue = parameterType.newInstance();
			String attributeName = annotation.value();
			//=>MemberVO member = new MemberVO();와 같은의미
			
			req.setAttribute(attributeName, parameterValue);//redirect가 되지 않는 이상 req에 그대로 저장되어 있다.
			BeanUtils.populate(parameterValue, req.getParameterMap());
			return parameterValue;
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			throw new ServletException(e);
		}
	}

}
