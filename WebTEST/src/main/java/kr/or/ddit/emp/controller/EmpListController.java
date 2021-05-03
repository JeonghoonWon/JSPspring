package kr.or.ddit.emp.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.emp.service.IEmployeeService;
import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;

@Controller
public class EmpListController{
	
	@Inject
	private IEmployeeService service;
	
	@RequestMapping("/emp/empView.do")
	public String view(
		@RequestParam("who") String who
		, Model model
	) {
		EmployeeVO employee = service.retrieveEmployee(who);
		model.addAttribute("employee", employee);
		return "emp/mypage";
	}
	
	@RequestMapping("/emp/empList.do")
	public String empList(
			@ModelAttribute("searchVO") SearchVO searchVO
			, @RequestParam(value="page", required=false, defaultValue="1") int currentPage
			, Model model){
		
		PagingVO<EmployeeVO> pagingVO = new PagingVO<>(7, 2);
		pagingVO.setCurrentPage(currentPage);
		// 검색 조건
		pagingVO.setSimpleSearch(searchVO);
		
		int totalRecord = service.retrieveEmployeeCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<EmployeeVO> empList = 
				service.retrieveEmployeeList(pagingVO);
		pagingVO.setDataList(empList);
		
		model.addAttribute("pagingVO", pagingVO);
		
		return "emp/empList";
	}
}

