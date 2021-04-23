package kr.or.ddit.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

  //  @WebFilter("/*")모든 요청을 다 필터링 하겠다 but 이렇게 사용하면 등록하는 순서를 지정할 수 없기 때문에 언제 블라인드 처리를 할수있는지 불가능하다. 사용하지 않는게 좋다/.
public class BlindFilter implements Filter {
	private static final Logger logger = 
			LoggerFactory.getLogger(BlindFilter.class);
	private Map<String,String> blindMap;
	private String view = "/messages/messageView.jsp";
	
@Override
public void init(FilterConfig filterConfig) throws ServletException {
	logger.info("{} 필터 초기화", this.getClass().getName());
	blindMap = new LinkedHashMap<>();
	blindMap.put("127.0.0.1", "그냥 나니까 블라인드  처리");
	blindMap.put("192.168.0.94", "그냥 유리니까 블라인드  처리");
}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 특정 클라이언트 블라인드 처리해보기
		// 192.168.0.94 
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		
		String uri = req.getRequestURI();
		
		boolean pass = uri.contains(view); // 통과 시켜야한다면 pass 가 true 가지고 있음.
		
		if(!pass) {
			String ip = request.getRemoteAddr();
			boolean blackList = blindMap.containsKey(ip);
			pass =!blackList;
			if(blackList) {
				String reason = blindMap.get(ip);
				logger.info("{} 블라인드 처리 사유 : {}", ip, reason);
			
				req.getSession().setAttribute("reason", reason);
				
			}
		}
		
		if(pass) {
			chain.doFilter(request, response);
		}else {
			resp.sendRedirect(req.getContextPath() + view);
		}
		
			
	}

	@Override
	public void destroy() {
		logger.info("{} 필터 소멸", this.getClass().getName());
		
	}

}
