package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;

//@WebServlet("/member/memberList.do")
@Controller
public class MemberListController {
	private IMemberService service = new MemberServiceImpl();

	@RequestMapping("/member/memberList.do")
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String searchType = req.getParameter("searchType");
		String searchWord = req.getParameter("searchWord");
		// 검색을 하는 사람도 있고 아닌 사람도 있기 때문에 검증을 할 필요가 없다.
		SearchVO searchVO = new SearchVO(searchType, searchWord);
		
		String pageParam = req.getParameter("page");
		int currentPage = 1;
		if(pageParam!=null && pageParam.matches("\\d+"))	{	// \\d : 숫자 하나 \\d+ : 숫자 하나 이상
			// 
			currentPage = Integer.parseInt(pageParam);
		}
		//속성을 전부 가지고 있게된다.
		PagingVO<MemberVO> pagingVO = new PagingVO(7,2);
		pagingVO.setCurrentPage(currentPage);
		// 검색 조건
		pagingVO.setSimpleSearch(searchVO);	// 검색 조건을 먼저 담아준 후 페이징 처리
		
		int totalRecord = service.retrieveMemberCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		
		List<MemberVO> memberList=
					service.retrieveMemberList(pagingVO);
		pagingVO.setDataList(memberList); 
		
		req.setAttribute("pagingVO", pagingVO);
		
		String view = "member/memberList";
			//req.getRequestDispatcher(view).forward(req, resp);
		return view;
	}
}
