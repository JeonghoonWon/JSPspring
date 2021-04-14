package kr.or.ddit.mvc.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kr.or.ddit.utils.ReflectionUtils;

public class HandlerMapping implements IHandlerMapping {
	private static final Logger logger 
		= LoggerFactory.getLogger(HandlerMapping.class);
	private Map<RequestMappingCondition, RequestMappingInfo> handlerMap;
	
	public HandlerMapping(String...basePackages){
		handlerMap = new LinkedHashMap<>();
		if(basePackages==null || basePackages.length == 0) {
			return;//더 이상 할일이 존재하지 않기 때문에 null , basePackages가 안넘어올때
		}
		
		Map<Class<?>, Annotation> controllerClasses =
				ReflectionUtils.getClassesWithAnnotationAtBasePackages(Controller.class, basePackages);
		
		for(Entry<Class<?>, Annotation> entry : controllerClasses.entrySet()) {
			Class<?> controllerClass = entry.getKey();
			Object commandHandler = null;
			try {
				commandHandler = controllerClass.newInstance();
			}catch (Exception e) {
				
				logger.error("컨트롤러 객체 생성 문제 발생", e);
				continue;
			}
			Map<Method, Annotation> handlerMethods =
					ReflectionUtils.getMethodsWithAnnotationAtClass(
						controllerClass,
						RequestMapping.class, 
						String.class);
						//HttpServletRequest.class, HttpServletResponse.class
						
			if(handlerMethods.size() == 0) continue;//더이상 읽을 entry가 없으면 continue
			Iterator<Method> it = handlerMethods.keySet().iterator();
			while (it.hasNext()) {
				Method handlerMethod = (Method) it.next();
				RequestMapping requestMapping = (RequestMapping) handlerMethods.get(handlerMethod);
				RequestMappingCondition mappingCondition
					= new RequestMappingCondition(requestMapping);
				RequestMappingInfo mappingInfo 
					= new RequestMappingInfo(mappingCondition, commandHandler, handlerMethod);
				handlerMap.put(mappingCondition, mappingInfo);
				logger.info("{}", mappingInfo);
			}//while end
		}//for end
	}//HandlerMapping end
	
	@Override
	public RequestMappingInfo findCommandHandler(HttpServletRequest req) {
		String uri = req.getRequestURI();
		uri = uri.substring(req.getContextPath().length()).split(";")[0];
		RequestMethod method 
			= RequestMethod.valueOf(req.getMethod().toUpperCase());
		req.getMethod();
		RequestMappingCondition key = new RequestMappingCondition(uri, method);
		return handlerMap.get(key);
	}

}