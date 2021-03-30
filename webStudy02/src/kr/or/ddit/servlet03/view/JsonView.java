package kr.or.ddit.servlet03.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonView extends AbstractView{
	
	@Override
	public void mergeModelAndView(Object target, HttpServletResponse resp)
	throws IOException
	{
		// 마샬링 진행 ( 마샬링과 언마샬링을 진행 할 수 있다.)
		ObjectMapper mapper =new ObjectMapper();
//		String json = mapper.writeValueAsString(target);
		
		try (
		PrintWriter out = resp.getWriter();
		){
			mapper.writeValue(out, target);
//			out.print(json);
		}
	
	}
}
