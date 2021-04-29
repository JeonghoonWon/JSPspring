package kr.or.ddit.container.auto.service;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.container.auto.dao.ISampleDAO;

@Service("sampleService")
public class SampleServiceImpl implements ISampleService {
	
//	@Resource(type = ISampleDAO.class)  
	private ISampleDAO dao;
		
	// 기본생성자
	public SampleServiceImpl() {
		super();
	
	}

	// dao 를 파라미터로 받을 생성자 생성 / 기본 생성자가 없는 상태. 
//	@Autowired 
	@Inject
	public SampleServiceImpl(ISampleDAO dao) {
		super();
		this.dao = dao;
	}



	@Override
	public String readData(String pk) {
		String raw = dao.selectData(pk); 
		String info = raw+"를 가공한 infomation";
		return null;
	}

}
