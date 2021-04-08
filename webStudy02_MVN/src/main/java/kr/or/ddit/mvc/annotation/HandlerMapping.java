package kr.or.ddit.mvc.annotation;

import java.lang.annotation.Annotation;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.utils.ReflectionUtils;

public class HandlerMapping implements IHandlerMapping {
	private static final Logger logger 
			= LoggerFactory.getLogger(HandlerMapping.class);
	private Map<RequestMappingCondition, RequestMappingInfo>handlerMap;
	
	// 생성자
	
	public HandlerMapping(String...basePackages) {
		handlerMap = new LinkedHashMap<>();
		if(basePackages==null && basePackages.length == 0) {
			return;
		}
		Map<Class<?>, Annotation> controllerClasses 
			= ReflectionUtils.getClassesWithAnnotationAtBasePackages(Controller.class, basePackages);
		for(Entry<Class<?>,Annotation> entry : controllerClasses.entrySet()) {
				Class<?> controllerClass = entry.getKey();
				Object commandHandler = null;
				try{
				commandHandler =  controllerClass.newInstance();
				}catch (Exception e) {
					// ex) 컨트롤러를 만들고 기본 생성자를 안만들었을 경우
					logger.error("컨트롤러 객체 생성 문제 발생",e);
					continue;
				}
				// 메서드 찾기
				// 핸들러 메서드를 찾을 수 있는 조건 정하기
				Map<Method,Annotation> handlerMethods 
					= ReflectionUtils.getMethodsWithAnnotationAtClass(
							controllerClass,
							RequestMapping.class, 
							String.class, 
							HttpServletRequest.class, HttpServletResponse.class); // 메서드 파라미터 타입
				if(handlerMethods.size() == 0) continue; // for문을 계속 돌리기 때문에 continue;
				Iterator<Method> it 
					=  handlerMethods.keySet().iterator();
				while (it.hasNext()) {
					Method handlerMethod = (Method) it.next();
					RequestMapping requestMapping 
						=  (RequestMapping) handlerMethods.get(handlerMethod);
					RequestMappingCondition mappingCondition
						= new RequestMappingCondition(requestMapping);
					RequestMappingInfo mappingInfo 
						= new RequestMappingInfo(mappingCondition, commandHandler, handlerMethod); 
					handlerMap.put(mappingCondition,mappingInfo ); // 하나의 헨들러에 대한 정보가 완성
					logger.info("{}",mappingInfo); // "{}" : 메시지 아규먼트
					
					
				}
			}
		}
	
	@Override
	public RequestMappingInfo findCommandHandler(HttpServletRequest req) {
		String uri =  req.getRequestURI(); // contextpath 도 포함되어있다. 잘라내줘야 비교 가능
		
		// contextpath 잘라내기
		uri = uri.substring(req.getContextPath().length()).split(";")[0];
		RequestMethod method 
			= RequestMethod.valueOf(req.getMethod().toUpperCase());
		RequestMappingCondition key 
			= new RequestMappingCondition(uri, method);
		return handlerMap.get(key); // 

	
	}

}
