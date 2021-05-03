package kr.or.ddit.filter.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

/**
 * 이미 인증을 거치고 온 요청에 대해 
 * 현재 보호 자원에 대한 접근 허용 여부를 확인할 필터.
 *
 */
public class AuthorizationFilter implements Filter {
	private static final Logger logger = 
					LoggerFactory.getLogger(AuthorizationFilter.class);
	
	private ServletContext application;
	private int statusCode =HttpServletResponse.SC_UNAUTHORIZED;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 필터 초기화", getClass().getName());
		String scStr = filterConfig.getInitParameter("statusCode");
		if(scStr!=null && StringUtils.isNumeric(scStr)) { //StringUtils.isNumeric : 숫자로 구성
			statusCode = Integer.parseInt(scStr);
		}
		this.application = filterConfig.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Map<String,String[]> securedResources = (Map) application.getAttribute(AuthenticationFilter.RESOUCEMAPNAME);
	
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// 1. 보호 자원에 대한 요청인지 식별
		String uri = req.getRequestURI();
		uri = uri.substring(req.getContextPath().length()).split(";")[0];
		boolean secured = securedResources.containsKey(uri);
		
		boolean pass = true;
		if(secured) {
			// AuthenticationFilter 가 먼저 실행 됬다는 전제 하에 진행된다.
			// 그래서 null  값 을 비교할 필요 없다.
			String[] resRoles =securedResources.get(uri);
			MemberVO authMember = (MemberVO) req.getSession().getAttribute("authMember");
			String userRole = authMember.getMem_role();
			// 2진 트리구조가 되기 위해선 sort 가 먼저 진행 되어야 한다. 그걸 Authentication 에서 진행시킨다.
			if(Arrays.binarySearch(resRoles, userRole) <0) {
				// 0보다 작을 경우 권한이 없는것
				pass =false;
			}
			
			
			
			
//			MemberVO authMember = (MemberVO) req.getSession().getAttribute("authMember");
//			String mem_role = authMember.getMem_role();
//			String[] authRole = securedResources.get(uri);
//			for(String tmp : authRole) {
//				if(tmp.equals(mem_role)) {
//					pass = true;
//					break;
//				}else {
//					pass = false;
//				}
//			}
			
			
			
		}
		// 보호 필요 없는 자원
		if(pass) {
			chain.doFilter(request, response);
		}else {
			// resp.sendError(HttpServletResponse.SC_FORBIDDEN); // 완전 이용 못하게 처리
			resp.sendError(statusCode,String.format("%s 자원에 대한 권한이 없음.", uri)); //401 error 내보내기
			
			//resp.sendError(401, "권한이 없는 유저입니다.");
		}
		
//		1. 보호자원 현재 요청이 보호자원에 대한 요청인지
//		보호자원이 아니면 통과
//
//
//		보호필요자원 이면  인증 여부 확인 
//		앞에서 확인 했으니까 
//		보호가 필요한 자원이면 
//
//		현재 자원에 사용된 롤과 로그인한 유저 롤과 일치여부 
//		일치 = 통과
//		불일치 = 통과 못하게 . 권한이 없는 자원을 요구한것 서비스 제공 X
//		클라이언트 에게  error 401 (unauthorized)
//
//		권한이 없는 상태에서 요청한 경우 401
	
	}
	

	@Override
	public void destroy() {
		logger.info("{} 필터 소멸", getClass().getName());
		
	}

}
