package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.buyer.dao.IOthersDAO;
import kr.or.ddit.buyer.dao.OthersDAOImpl;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.vo.BuyerVO;

//@WebServlet("/buyer/buyerInsert.do")
@Controller
public class BuyerInsertServlet{
	private IBuyerService service = new BuyerServiceImpl();
	private IOthersDAO othersDAO = OthersDAOImpl.getInstance();
	
	private void addAttribute(HttpServletRequest req) {
		List<Map<String, Object>> lprodList = othersDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
	}
	
	@RequestMapping("/buyer/buyerInsert.do")
	public String doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addAttribute(req);
		return "buyer/buyerForm";
	}
	
	@RequestMapping(value="/buyer/buyerInsert.do", method=RequestMethod.POST)
	public String doPost(
			@ModelAttribute("buyer") BuyerVO buyer,
			HttpServletRequest req) 
		throws ServletException, IOException {
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(buyer, errors);
		String view = null;
		String message = null;
		if(valid) {
			ServiceResult result = service.createBuyer(buyer);
			switch(result) {
				case OK:
					view = "redirect:/buyer/buyerView.do?buyer="+buyer.getBuyer_id();
					break;
				default:
					message = "서버 오류, 잠시 뒤 다시 시도하세요.";
					view = "buyer/buyerForm";
					break;
			}//switch end
		}//if end
		req.setAttribute("message", message);
		return view;
	}

	private boolean validate(BuyerVO buyer, Map<String, String> errors) {
		boolean valid = true;
		if (StringUtils.isBlank(buyer.getBuyer_name())) {
			valid = false;
			errors.put("buyer_name", "거래처명 누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_lgu())) {
			valid = false;
			errors.put("buyer_lgu", "거래처분류명 누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_comtel())) {
			valid = false;
			errors.put("buyer_comtel", "거래처회사번호 누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_fax())) {
			valid = false;
			errors.put("buyer_fax", "거래처팩스번호 누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_mail())) {
			valid = false;
			errors.put("buyer_mail", "거래처메일 누락");
		}
		return valid;
	}
}