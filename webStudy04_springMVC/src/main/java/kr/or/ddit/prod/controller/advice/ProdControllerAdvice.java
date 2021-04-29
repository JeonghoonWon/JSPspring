package kr.or.ddit.prod.controller.advice;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.or.ddit.prod.dao.IOthersDAO;
import kr.or.ddit.vo.BuyerVO;

@ControllerAdvice(basePackages = "kr.or.ddit.prod.controller")  // basePackages : joinpoint 설정 
public class ProdControllerAdvice {

	// 컨트롤러에 AOP 적용 advice를 분리 , 공통 관심사 분리.
	
	@Inject
	private IOthersDAO othersDAO;
	
	@ModelAttribute("lprodList") // 메소드를 통해 만들어지는 객체를 넣어준다.
	public List<Map<String, Object>> getLprodList(){
		List<Map<String, Object>> lprodList 
		= othersDAO.selectLprodList();
		return lprodList;
	}
	
	@ModelAttribute("buyerList") // 메소드를 통해 만들어지는 객체를 넣어준다.
	public List<BuyerVO> getBuyerList() {
		List<BuyerVO> buyerList 
		= othersDAO.selectBuyerList(null);
		return buyerList;
	}
	
	// 프록시에서 호출으 해야하기 때문에 private 를 public 으로
//	public void addAttribute(Model model) {
//		
//		
//		model.addAttribute("lprodList", lprodList);
//		model.addAttribute("buyerList", buyerList);
//	}
}
