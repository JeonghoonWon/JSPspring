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
 * @ModelAttribute 어노테이션으로 설정된 핸들러 메소드 아규먼트를 처리할 처리자.
 *
 */
public class ModelAttributeArgumentResolver implements IHandlerMethodArgumentResolver {

	@Override
	public boolean isSupported(Parameter parameter) {
		// parameter type 찾기
		Class<?> parameterType = parameter.getType();
		
		// 1. annotation 이 parameter 에 붙어있는지 확인
		// 2. 객체를 확인 . argument 가 있더라도 String, int 등등 기본형이면 안된다. 
		ModelAttribute annotation 
				= parameter.getAnnotation(ModelAttribute.class);
		boolean supported = annotation != null
				&& !(
						String.class.equals(parameterType)
						|| ClassUtils.isPrimitiveOrWrapper(parameterType)
						// || parameterType.isPrimitive() // 기본형 Int만 check 가능
					);
		return supported;
	}

	@Override
	public Object argumentResolve(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 내가 만들 VO 를 알아야 하기 때문에 parameter check 가 필요하다.
		// parameter type 찾기
			Class<?> parameterType = parameter.getType();
		// annotation 찾기
			ModelAttribute annotation = parameter.getAnnotation(ModelAttribute.class);
			try {
				Object parameterValue =parameterType.newInstance(); 
				String attributeName = annotation.value(); // annotation 의 값을 찾아줌.
				// =	MemberVO member = new MemberVO(); 와 같은 뜻
		
		req.setAttribute(attributeName, parameterValue);	// 
		// member.setMem_id(req.getParameter("mem_id"));
			BeanUtils.populate(parameterValue,req.getParameterMap());
			return parameterValue;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
