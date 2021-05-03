package kr.or.ddit.emp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.emp.service.IEmployeeService;
import kr.or.ddit.enumpkg.ServiceResult;


@Controller
public class EmpIdCheckController{
	

	
	@Inject
	private IEmployeeService service;
	
		@RequestMapping(value ="/emp/idCheck.do", method = RequestMethod.POST,
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
		@ResponseBody
		public Map<String,Object> doPost(
				@RequestParam("employee_id") String employee_id
				) {
		Map<String,Object> resultMap = new HashMap<>();
		try {
			service.retrieveEmployee(employee_id);
			resultMap.put("result",ServiceResult.FAIL);
		} catch (Exception e) {
			resultMap.put("result",ServiceResult.OK);
		}
		 
		return resultMap;
	}

		
}