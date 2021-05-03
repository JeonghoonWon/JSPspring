package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestChangeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 요청이 들어올때마다 doFilter 를 통해 작업이 수행됨.
		
		// request 에는 setter 가 없다. client 에게서 넘어오는 요청을 바꾸는 경우가 적기 때문에
		HttpServletRequest req = (HttpServletRequest) request;
		
		//request = new HttpServletRequestWrapper(req); 로 처리하면 변경이 되지 않은것. 부모가 가지고 있는걸 그대로 가지고 있기 떄문에
		// 대신 {}; 를 사용하게되면 그 안에서의 변경 처리가 가능해진다.
		
		request = new HttpServletRequestWrapper(req){
			// HttpServletRequestWrapper : adapter
			// req : adaptee
			public String getParameter(String name) {
				if("what".equals(name)) {
					// setter의 역할을 대신할 수 있는 Wrapper 를 만들어서 성질을 변경시킨것.
					return "P101000001";					
				}else {
					 return super.getParameter(name); //원본 파라미터를 그대로 리턴
				}
			};
		};
		
		//HttpServletRequestWrapper 는 HttpServletRequest 와 ServletRequest 의 자식. 상속구조로 처리하는 것.
		
		chain.doFilter(request, response); // 변경된 request(요청) 가 넘어간다. // 원하는 인터페이스가 없는경우 사용. 
		
		
		
	}

	@Override
	public void destroy() {
		
		
	}

}
