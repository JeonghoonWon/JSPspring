package kr.or.ddit.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login/logout.do")
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if(session.isNew())	{//세션이 새로운 것인지 확인을 해본다. 이게 true 인 경우 문제가 있는것
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}		
		session.invalidate();  //1. session scope 안에있는 내용 삭제 	2. session 만료 기능이 있음.
		
		String view = "/";
		resp.sendRedirect(req.getContextPath() + view); // session이 끝나면서 req가 없기 때문에 redirect으로 이동
		
	}
}
