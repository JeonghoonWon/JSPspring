package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.member.service.IMemberService_test;
import kr.or.ddit.member.service.MemberServiceImpl_test;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/mypage.do")
public class MypageServlet_test extends HttpServlet{
	IMemberService_test service = new MemberServiceImpl_test();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		String mem_id = authMember.getMem_id();
		MemberVO detailMember = service.retrieveMember(mem_id);
		
		req.setAttribute("member", detailMember);
		
		
		String view = "/webStudy01/webapps/WEB-INF/views/member/mypage.jsp";
		req.getRequestDispatcher(view).forward(req,resp);
		
	}
	
}
