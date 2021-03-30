package kr.or.ddit.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginCheckServlet
 */
@WebServlet("/login/loginCheck.do")
public class LoginCheckServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		HttpSession session = req.getSession();
		if(session.isNew()) {
			
			//session 최초의 요청이 들어왔을때 세션이 만들어지게된다 (?) 
			//쿠키가 사라지면 요청이 계속 새롭게받아진다.
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
			
		}
		
		String view = null;
		
		String mem_id = req.getParameter("mem_id");
		String mem_pw = req.getParameter("mem_pw");
		
		String message = null;
		//요청 분석
		boolean redirect = false;
		
		boolean valid = validate(mem_id,mem_pw);
		
		if(valid) {
			//인증(아이디와 패스워드가 같으면 인증성공)
			
			boolean auth = authenticate(mem_id,mem_pw);
			
			if(auth) {
				
				//인증 성공시 index.jsp로 이동(현재, 요청 정보 삭제)	
				
				redirect = true;
				view ="/";
				session.setAttribute("authId", mem_id);
				
			}else{
				//인증 실패시 loginForm.jsp로 이동
				redirect =true;
				//잘못된 인증시에는 관리를 하지않는다.
				
				
				//비밀번호 오류 메세지를 보낸다.
				view ="/login/loginForm.jsp";
				message = "비밀번호 오류";
				session.setAttribute("failedId", mem_id);
			}
		}else{
			//검증 -- 아이디나 패스워드가 누락됨.
			
			redirect = true;
            view = "/login/loginForm.jsp";
            message ="아이디또는 비밀번호 누락";
			
		}

		/*
		 * if(message !=null) { req.setAttribute("message", message);
		 * 
		 * }
		 */
		
		
		
		//view로 이동
		if(redirect) {
			req.getSession().setAttribute("message", message);
			//유지가 되는 방식으로 이동된다
			//
			
			resp.sendRedirect(req.getContextPath()+ view);
			//request를 넘기지 않는다.
			
		}else {
			req.setAttribute("message", message);
			//한번 쓰고 사라지는 방법
			req.getRequestDispatcher(view).forward(req, resp);
			
		}
		
		
		
		
		
		//forward include sendRedirect
	
		

		
		

		
		
		
	
	}


	private boolean authenticate(String mem_id, String mem_pw) {
		
		return mem_id.equals(mem_pw);
		
		
	}


	private boolean validate(String mem_id, String mem_pw) {
		boolean valid =true ;
		
		
		if (mem_id == null || mem_id.isEmpty() || mem_pw.isEmpty() || mem_pw == null) {
			//isEmpty 인스턴스는 생성되었으나 안에 값이 없는 경우를 나타낸다.
			valid = false;

		}
		
		
		
		
		return valid;
	}



}
