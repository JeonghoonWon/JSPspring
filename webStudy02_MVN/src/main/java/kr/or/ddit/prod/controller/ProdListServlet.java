package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

@WebServlet("/prod/prodList.do")
public class ProdListServlet extends HttpServlet {
	private IProdService service = ProdServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pageParam = req.getParameter("page");
		int currentPage = 1;
		if(pageParam!=null && pageParam.matches("\\d+"))	{	// \\d : 숫자 하나 \\d+ : 숫자 하나 이상
			// 
			currentPage = Integer.parseInt(pageParam);
		}
		//속성을 전부 가지고 있게된다.
		PagingVO<ProdVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		
		int totalRecord = service.retrieveProdCount();
		pagingVO.setTotalRecord(totalRecord);
						
		List<ProdVO> prodList = service.retrieveProdList(pagingVO);
		pagingVO.setDataList(prodList); 
		
		req.setAttribute("pagingVO", pagingVO);

		String view = "/WEB-INF/views/prod/prodList.jsp";
		req.getRequestDispatcher(view).forward(req, resp);
	}
	
}
