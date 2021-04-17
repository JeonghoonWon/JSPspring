package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.mvc.filter.wrapper.MultipartFile;
import kr.or.ddit.mvc.filter.wrapper.MultipartHttpServletRequest;

public class RequestPartArgumentResolver implements IHandlerMethodArgumentResolver {

	@Override
	public boolean isSupported(Parameter parameter) {
		// requestPart라는 annotation을 갖고 있는것
		// 파라미터의 타입이 multipartfile 을 갖고 있는것.
		
//		requestpart annotation 의 존재 여부 
//		파라미터의 타입이 multipartfile 인 경우 
//		두가지 조건을 만족하면 true 아니면 false
		
		// 파라미터 타입 찾기
		Class<?> parameterType = parameter.getType();
		RequestPart annotation = parameter.getAnnotation(RequestPart.class);
		return annotation != null && 
				(MultipartFile.class.equals(parameterType) 
						|| (parameterType.isArray() && MultipartFile.class.equals(parameterType.getComponentType()))); 
							// 배열이면서 배열의 콤포넌트가 멀피파트 파일이라면 멀티파트 파일까지 지원가능
	
	}

	@Override
	public Object argumentResolve(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!(req instanceof MultipartHttpServletRequest)) {
			//wrapper가 아닌 상태.
			throw new ServletException("현재 요청은 multipart 요청이 아님.");
			
		}
		
		RequestPart annotation =parameter.getAnnotation(RequestPart.class);
		
		String partName = annotation.value();
		boolean required = annotation.required();

		MultipartHttpServletRequest wrapper = (MultipartHttpServletRequest) req;
		
		List<MultipartFile> files = wrapper.getFiles(partName);
		
		if(required && files == null) {
			// 필수 파라미터가 누락된 상황.
			throw new BadRequestException(partName + "에 해당하는 파일이 업로드되지 않았음.");
		}
		
		Class<?> parameterType = parameter.getType();
		
		Object retValue = null;
		
		if(files!=null && files.size() > 0) {
			if(parameterType.isArray()) {
				MultipartFile[] array = new MultipartFile[files.size()]; 
				// files.size() 리스트의 사이즈 만큼 어레이의 크기 만들어준다.
				retValue = files.toArray(array);
			
			}else {
				retValue = files.get(0);
			}
		}
		
		return retValue;
		
		// 파라미터의 타입 찾기
//		
//		Class<?> parameterType = parameter.getType();
//		
//		// annotation 찾기
//		RequestPart annotation =parameter.getAnnotation(RequestPart.class);
//		
//		String reqParamName = annotation.value();
//		
//		Part reqPart = req.getPart(reqParamName);
//		MultipartFile mpf = new MultipartFile(reqPart);
//		
//		boolean required = annotation.required();
//		if (required && reqPart == null) {
//			throw new BadRequestException("필수 파라미터 누락");	
//			
//		}
//		
//		return mpf;
//		
//		
//		requestpart annotation 의 존재 여부 
//		파라미터의 타입이 multipartfile 인 경우 
//		두가지 조건을 만족하면 true 아니면 false
//		실제 아규먼트 만드는곳
//		Object argumentResolve
//		1. multipartfile 꺼내기
//		2. 어떤 이름의 파일을 꺼낼지 / requestpart annotation 에 설정 되어있음.
//		그 이름에 해당하는 파일 이름을 꺼내려면
//		warpper 여야함
//		원본인경우 중간에 뭔가 잘못된것
//		일반적인 request에 사용한것 예외를 발생 
	
		// req가 wrapper의 형태로 가지고  있어야 파일을 들고 있는 경우
		
		
	}

}
