package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService_test;
import kr.or.ddit.member.service.MemberServiceImpl_test;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberDelete_test.do")
public class MemberDeleteServlet_test extends HttpServlet{
	private IMemberService_test service = new MemberServiceImpl_test();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String password = req.getParameter("password");
		System.out.println(password);
		if(password==null||password.isEmpty()) {		// password null check
			resp.sendError(400);					// null or 빈 공간 : error 400
			return;
		}
		
		
//		1. 요청 접수
		
		HttpSession session =  req.getSession();
		MemberVO authMember =(MemberVO) session.getAttribute("authMember");
		String authId = authMember.getMem_id();
		ServiceResult result=  service.removeMember(new MemberVO(authId,password));
		String view = null;
		switch (result) {
		case INVALIDPASSWORD:
			view = "redirect:/mypage_test.do";
			session.setAttribute("message", "비밀번호 오류");
			break;
		case OK:
			session.invalidate();
			view = "redirect:/";
			// session.setAttribute("message", "탈퇴 완료"); // session 이 삭제되었기때문에 message를 보낼 수 없다.
			break;
		default:
			view = "redirect:/mypage_test.do";
			session.setAttribute("message", "서버 오류");
			break;
		}
		
		boolean redirect = view.startsWith("redirect:");
		if(redirect) {
			view = view.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + view);
		}else {
		req.getRequestDispatcher(view).forward(req, resp);
		
		}
		
	}
	
}
