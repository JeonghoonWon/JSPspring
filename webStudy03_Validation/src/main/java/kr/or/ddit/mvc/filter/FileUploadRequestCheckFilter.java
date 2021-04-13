package kr.or.ddit.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.mvc.filter.wrapper.MultipartHttpServletRequest;

/**
 * 1. 파일이 포함된 multipart 요청인지 식별.
 * 2. multipart 요청이라면, 원본 요청을 wrapper로 변경.
 * 3. wrapper 를 이용해 Part 데이터를 쉽게 핸들링 할 수 있는 구조 설정.
 * 
 */
public class FileUploadRequestCheckFilter implements Filter{
	private static final Logger logger =
			LoggerFactory.getLogger(FileUploadRequestCheckFilter.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 필터 초기화", getClass().getName());

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String contentType = req.getContentType(); // getContentType(); 은 바디에 있는 타입을 찾는다. post 방식은 바디가 있고 get 은 바디가 없기 때문에 get은 null 값이 넘어온다.
		if(contentType !=null && contentType.startsWith("multipart")) { // file 이 있는지 없는지 확인.
			MultipartHttpServletRequest wrapper =
					new MultipartHttpServletRequest(req);
			
			chain.doFilter(wrapper, response);
		}else {
			chain.doFilter(request, response); // 일반 요청이기 때문에 doFilter로 다른 필터로 넘겨준다.
		}
	}

	@Override
	public void destroy() {
		logger.info("{} 필터 소멸", getClass().getName());
		
	}

}
