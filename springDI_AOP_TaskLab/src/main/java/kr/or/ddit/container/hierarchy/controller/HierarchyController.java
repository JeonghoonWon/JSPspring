package kr.or.ddit.container.hierarchy.controller;

import org.springframework.stereotype.Controller;

import kr.or.ddit.container.hierarchy.service.HierarchyService;

@Controller
public class HierarchyController {
	private HierarchyService service;

	public HierarchyController(HierarchyService service) {
		super();
		this.service = service;
	}
	
	// 컨트롤러 하위 
	// dao, service 상위
	
}
