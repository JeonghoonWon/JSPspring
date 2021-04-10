package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.buyer.dao.IOthersDAO;
import kr.or.ddit.buyer.dao.OthersDAOImpl;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

@Controller
public class BuyerListController {
	
	private IBuyerService service = BuyerServiceImpl.getInstance();
	private IOthersDAO othersDAO = OthersDAOImpl.getInstance();
	
	private void addAttribute(HttpServletRequest req) {
		List<Map<String,Object>> lprodList
				= othersDAO.selectLprodList();
		
		List<ProdVO> prodList
				= othersDAO.selectProdList(null);
		req.setAttribute("lprodList", lprodList);
		req.setAttribute("prodList", prodList);
	}
	
	@RequestMapping("/buyer/buyerList.do")
	public String list (
				@ModelAttribute("detailSearch") BuyerVO detailSearch 
				,@RequestParam(value="page", required=false, defaultValue="1") int currentPage
				,HttpServletRequest req
				,HttpServletResponse resp) throws ServletException, IOException {
			addAttribute(req);
			
			PagingVO<BuyerVO> pagingVO = new PagingVO<>();
			pagingVO.setCurrentPage(currentPage);
			pagingVO.setDetailSearch(detailSearch);
			
			int totalRecord = service.retrieveBuyerCount(pagingVO);
			pagingVO.setTotalRecord(totalRecord);
			
			List<BuyerVO> buyerList = 
					service.retrieveBuyerList(pagingVO);
			pagingVO.setDataList(buyerList);
			
			String accept = req.getHeader("Accept");
			String view = null;
			if(StringUtils.containsIgnoreCase(accept, "json")) {
				resp.setContentType("application/json;charset=UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				try(
					PrintWriter out = resp.getWriter();	
				){	// 이미 응답 데이터가 결정된 경우에도 null 값이 될 수 있음.
					mapper.writeValue(out, pagingVO);
				}
				
			}else {
				req.setAttribute("pagingVO", pagingVO);
				
				view = "buyer/buyerList";
				
			}
			return view;
			
	}

	
}
	

