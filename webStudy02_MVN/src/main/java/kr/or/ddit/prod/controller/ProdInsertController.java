package kr.or.ddit.prod.controller;

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

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.prod.dao.IOthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
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
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addAttribute(req);
		
		String view = "prod/prodForm";
		
		return view;
	}
	

	@RequestMapping(value ="/prod/prodInsert.do", method = RequestMethod.POST )
	public String InsertProd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		ProdVO prod;
//		
//		createProd(prod);
//		prod.getProd_id();
//		등록 성공시 : /prodView.do 로 이동
		req.setCharacterEncoding("UTF-8");

		// 1. 요청 접수
		ProdVO prod = new ProdVO();
		// 입력값 중 한두개 틀렸을 경우 prodForm 으로 다시 가야하므로 기존 입력값도 가져가야한다.
		// 그때 req.setAttribute("prod", prod) 사용
		req.setAttribute("prod", prod);
		try {
			// 한꺼번에 바인딩 하기 위해 beanUtils
			BeanUtils.populate(prod, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		// 2. 검증
		// Map<String : 어떤게 통과 못했는지 , String : 검증 결과 메시지> 
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		// 검증의 대상 : prod 
		boolean valid = validate(prod, errors);
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

	private boolean validate(ProdVO prod, Map<String, String> errors) {
		boolean valid = true;
		
		return valid;
	}
}