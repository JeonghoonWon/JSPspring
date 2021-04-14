package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.filter.wrapper.MultipartFile;
import kr.or.ddit.prod.dao.IOthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validator.CommonValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.ProdVO;

//@WebServlet("/prod/prodInsert.do")
@Controller
public class ProdInsertController {
	
	private IProdService service = ProdServiceImpl.getInstance();
	private IOthersDAO othersDAO = OthersDAOImpl.getInstance();
	
	private void addAttribute(HttpServletRequest req) {
		List<Map<String, Object>> lprodList 
			= othersDAO.selectLprodList();
		List<BuyerVO> buyerList 
			= othersDAO.selectBuyerList(null);
		req.setAttribute("lprodList", lprodList);
		req.setAttribute("buyerList", buyerList);
	}

	@RequestMapping("/prod/prodInsert.do")
	// 상품을 아무나 등록해선 안되기때문에 보호 할 자원
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addAttribute(req);
		
		String view = "prod/prodForm";
		
		return view;
	}
	

	@RequestMapping(value ="/prod/prodInsert.do", method = RequestMethod.POST )
	public String process(
			@ModelAttribute("prod") ProdVO prod
			, @RequestPart("prod_image") MultipartFile prod_image
			, HttpServletRequest req) throws IOException {
		addAttribute(req);
		// Map<String : 어떤게 통과 못했는지 , String : 검증 결과 메시지> 
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		// 검증의 대상 : prod 
//		EDD / TestDrivenDevelopment 
//		EventDrivenDevelopment : 이벤트가 발생하면 작업을 진행 하는것.

//		서버 사이트에서 처리할 수 있는 이벤트 알아보자.
		
		String saveFolderUrl = "/prodImages";
		File saveFolder = new File(req.getServletContext().getRealPath(saveFolderUrl));
	if(!saveFolder.exists()) {
		saveFolder.mkdirs();
	}
		
		if(!prod_image.isEmpty()) {
			prod_image.saveTO(saveFolder);
			prod.setProd_img(prod_image.getUniqueSaveName());
			// 사진 저장 
			
			// 이름 값 꺼내와서 
			
			// 
		}
		
		boolean valid = new CommonValidator<ProdVO>().validate(prod, errors, InsertGroup.class);
		String view = null;
		String message = null;
		if(valid) {
			ServiceResult result = service.createProd(prod);
			if(ServiceResult.OK.equals(result)) {
				view = "redirect:/prod/prodView.do?what="+prod.getProd_id();
				// 현재 request 정보를 가져갈 필요가 없기 때문에 
				// jsp 가 아닌 "redirect:/prod/prodView.do" 로 보내야 한다.
				// 
			}else {
				message = "서버 오류";
				view = "prod/prodForm";
			}
		}else {
			view = "prod/prodForm";
		}
		
		req.setAttribute("message", message);
		
		return view;
	}


}