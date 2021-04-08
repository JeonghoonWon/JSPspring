package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

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

// /prod/prodUpdate.do
@Controller
public class ProdUpdateController {
	private IProdService service = ProdServiceImpl.getInstance();
	private IOthersDAO othersDAO = OthersDAOImpl.getInstance();

	private void addAttribute(HttpServletRequest req) {
		List<Map<String, Object>> lprodList = othersDAO.selectLprodList();
		List<BuyerVO> buyerList = othersDAO.selectBuyerList(null);
		req.setAttribute("lprodList", lprodList);
		req.setAttribute("buyerList", buyerList);
	}
	@RequestMapping("/prod/prodUpdate.do")
	public String updateForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String prod_id = req.getParameter("what");
		if(StringUtils.isBlank(prod_id)) {
			resp.sendError(400);
			return null;
		}
		
	addAttribute(req);
	ProdVO prod = service.retrieveProd(prod_id);
	req.setAttribute("prod", prod);
	return "prod/prodForm";
	}
	@RequestMapping(value ="/prod/prodUpdate.do", method =RequestMethod.POST)
	public String update(HttpServletRequest req, HttpServletResponse resp) {
	ProdVO prod = new ProdVO();
	req.setAttribute("prod", prod);

	try {
		BeanUtils.populate(prod, req.getParameterMap());
	} catch (IllegalAccessException | InvocationTargetException e) {
		throw new RuntimeException(e);
	}
	Map<String, String> errors = new LinkedHashMap<>();
	req.setAttribute("errors", errors);
	boolean valid = validate(prod, errors);
	String view = null;
	String message = null;
	if(valid) {
		ServiceResult result = service.modifyProd(prod);
		if(ServiceResult.OK.equals(result)) {
			view = "redirect:/prod/prodView.do?what="+prod.getProd_id();
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
