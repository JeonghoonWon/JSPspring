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

@Controller
public class BuyerReadController{
	private IBuyerService service = new BuyerServiceImpl();
	private IOthersDAO othersDAO = OthersDAOImpl.getInstance();
	
	private void addAttribute(HttpServletRequest req) {
		List<Map<String, Object>> lprodList = othersDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
	}
	
	@RequestMapping("/buyer/buyerList.do")
	public String BuyerList(
			@ModelAttribute("detailSearch") BuyerVO detailSearch , 
			@RequestParam(value="page", required=false, defaultValue="1") int currentPage,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addAttribute(req);
		PagingVO<BuyerVO> pagingVO = new PagingVO(4, 2);
		pagingVO.setCurrentPage(currentPage);	
		pagingVO.setDetailSearch(detailSearch);
		
		int totalRecord = service.retrieveBuyerCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<BuyerVO> buyerList = service.retrieveBuyerList(pagingVO);
		pagingVO.setDataList(buyerList);
		String accept = req.getHeader("Accept");
		String view = null;
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			resp.setContentType("application/json;charset=UTF-8");
			ObjectMapper mapper = new ObjectMapper();//마살링하기 위해 필요
			try(
				PrintWriter out = resp.getWriter();	
			){
				mapper.writeValue(out, pagingVO);
			}
		}else {
			req.setAttribute("pagingVO", pagingVO);
			
			view = "buyer/buyerList";
		}
		return view;
	}
	
	@RequestMapping("/buyer/buyerView.do")
	public String View(
			@RequestParam(value="buyer", required=true, defaultValue="1") String buyer_id,
			HttpServletRequest req) throws ServletException, IOException {
		BuyerVO buyer = service.retrieveBuyer(buyer_id);
		req.setAttribute("buyer", buyer);
		return "buyer/buyerView";
	}
}