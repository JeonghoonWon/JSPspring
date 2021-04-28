package kr.or.ddit.container.hierarchy.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.container.hierarchy.dao.HierarchyDAO;

@Service
public class HierarchyService {
	private HierarchyDAO dao; // 의존관계 형성
	@Inject
	
	
	public void setDao(HierarchyDAO dao) {
		this.dao = dao;
	}
}
