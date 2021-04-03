package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.member.service.IMemberService_test;
import kr.or.ddit.member.service.MemberServiceImpl_test;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberList_test.do")
public class MemberListServlet_test extends HttpServlet {
	private IMemberService_test service = new MemberServiceImpl_test();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<MemberVO> memberList=
					service.retrieveMemberList();
		req.setAttribute("memberList", memberList);
		
		String view = "/WEB-INF/views/member/memberList_test.jsp";
			req.getRequestDispatcher(view).forward(req, resp);
		
	}
}
