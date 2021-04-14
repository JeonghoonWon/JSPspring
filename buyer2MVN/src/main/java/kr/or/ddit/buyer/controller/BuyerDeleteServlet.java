package kr.or.ddit.buyer.controller;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;

//@WebServlet("/buyer/buyerDelete.do")
@Controller
public class BuyerDeleteServlet{
	private IBuyerService service = new BuyerServiceImpl();
	
	@RequestMapping("/buyer/buyerDelete.do")
	public String Delete(
			@RequestParam(value="buyer", required=true, defaultValue="1") String buyer_id) {
		String view = null;
		ServiceResult result = service.removeBuyer(buyer_id);
		switch(result) {
			case OK:
				view = "redirect:/buyer/buyerList.do";
				break;
			default:
				view = "buyer/buyerForm";
				break;
		}//switch end
		return view;
	}
	
}