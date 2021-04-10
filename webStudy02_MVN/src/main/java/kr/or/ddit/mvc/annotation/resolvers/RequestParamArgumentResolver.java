package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @RequestParam 어노테이션으로 설정된 핸들러 메서드 아규먼트를 처리할 처리자
 *
 */
public class RequestParamArgumentResolver implements IHandlerMethodArgumentResolver {

	@Override
	public boolean isSupported(Parameter parameter) {
		// parameter type 찾기
		Class<?> parameterType = parameter.getType();
		// parameter type 찾기
		RequestParam annotation = parameter.getAnnotation(RequestParam.class);

		// 값 하나를 받을 수 있는지 확인한다.
		boolean supported = annotation != null
				&& (String.class.equals(parameterType) || ClassUtils.isPrimitiveOrWrapper(parameterType));
		return supported;
	}

	@Override
	public Object argumentResolve(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// parameter type 찾기
		Class<?> parameterType = parameter.getType();

		// annotation 찾기
		RequestParam annotation = parameter.getAnnotation(RequestParam.class);
		String reqParamName = annotation.value(); // =String prod_id = req.getParameter("what");

		// 핸들러 어댑터라 보내주는 값
		String reqParamValue = req.getParameter(reqParamName); // String reqParamName = annotation.value();

		// reqParamValue의 유무를 먼저 확인. 필수 파라미터가 어떤건지 확인
		boolean required = annotation.required();
		if (required && StringUtils.isBlank(reqParamValue)) {
			throw new BadRequestException("필수 파라미터 누락");	
			
		} else if (!required && StringUtils.isBlank(reqParamValue)) {
			reqParamValue = annotation.defaultValue();
		}
		// 어떤 타입
		Object parameterValue = null;
		try {
			if(byte.class.equals(parameterType) || Byte.class.equals(parameterType)) {
				parameterValue = Byte.parseByte(reqParamValue);
			}else if(short.class.equals(parameterType)|| Short.class.equals(parameterType)) {
				parameterValue = Short.parseShort(reqParamValue);
			}else if(int.class.equals(parameterType)|| Integer.class.equals(parameterType)) {
				parameterValue = Integer.parseInt(reqParamValue);
			}else if(long.class.equals(parameterType)|| Long.class.equals(parameterType)) {
				parameterValue = Long.parseLong(reqParamValue);
			}else if(float.class.equals(parameterType)|| Float.class.equals(parameterType)) {
				parameterValue = Float.parseFloat(reqParamValue);
			}else if(double.class.equals(parameterType)|| Double.class.equals(parameterType)) {
				parameterValue = Double.parseDouble(reqParamValue);
			}else if(boolean.class.equals(parameterType)|| Boolean.class.equals(parameterType)) {
				parameterValue = Boolean.parseBoolean(reqParamValue);
			}else if(char.class.equals(parameterType)|| Character.class.equals(parameterType)) {
				parameterValue = reqParamValue.charAt(0);
			}else {
				// String 인 경우
				parameterValue = reqParamValue;
			}

			return parameterValue;
		} catch (Exception e) {
			throw new BadRequestException(e);
		}

	}
}
