package kr.or.ddit.filter;

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

/**
 *  필터 : 요청과 응답에 대한 전/후 처리를 담당하는 재사용 가능한 객체
 *  잘못된 요청을 돌려보내는, 흐름을 바꾸는 것도 필터가 처리한다.
 *	응답데이터가 만들어졌을때 크기를 줄여야 하는 상황이 있어야하는데 그때 압축을 해야 함.
 *	
 *	필터 활용 단계
 *	1. Filter 구현체 정의
 *		1) lifecycle callback : init, destroy
 *		2) filtering callback : doFilter
 *				chain.doFilter 를 기준으로 요청과 응답에 대한 필터링 구분.
 *	2. 서버에 등록 : 싱글톤으로 관리, FilterChain 이 형성됨.
 *		** 주의 ! : FilterChain 내에 필터링 되는 순서는 필터 등록 순서를 따름.(web.xml 등록 기준)
 *				요청에 대한 필터링과 응답에 필터링은 역순으로 처리됨.
 *	3. 필터링 요청 매핑 설정
 *
 */
public class FilterDesc implements Filter {
	private static final Logger logger = 
				LoggerFactory.getLogger(FilterDesc.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 필터 초기화", this.getClass().getName());
		
	}

	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response, 
			FilterChain chain) // FilterChain : 필터들의 모음
			throws IOException, ServletException {
		
		// request filtering // 필터의 순서는 스텝 메모리에 저장시켜서 순차적으로 진행 될수 있게 처리한다.
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		logger.info("{} 요청 필터링", uri);
		chain.doFilter(request, response);
		// response filtering
		response.getContentType();
		String mime = response.getContentType();
		logger.info("{} 응답 필터링, 응답 컨텐츠 : {}", uri,mime);
		
	}

	@Override
	public void destroy() {
		logger.info("{} 필터 소멸", this.getClass().getName());

		
	}

}
