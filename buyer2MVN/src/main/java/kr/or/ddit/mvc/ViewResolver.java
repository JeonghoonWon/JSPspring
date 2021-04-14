package kr.or.ddit.mvc;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewResolver implements IViewResolver{
	private String prefix;
	private String suffix;
	
	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public void viewResolve(String viewName, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean redirect = viewName.startsWith("redirect:");
		if(redirect) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {			
			req.getRequestDispatcher(Objects.toString(prefix, "")+viewName+Objects.toString(suffix, "")).forward(req, resp);
			//하드코잉을 한다는 것은 변경사항을 발생시킨다는 것 => 경로는 언제나 바뀔수 있고, 확장자도 달라질수 있음
			//누군가가 setter를 호출해서 넣어야한다. => setter를 안쓰는 순간 null이 발생
			//Objects.toString를 통해 null인 경우 ""를 넣음
		}
	}
	
}
