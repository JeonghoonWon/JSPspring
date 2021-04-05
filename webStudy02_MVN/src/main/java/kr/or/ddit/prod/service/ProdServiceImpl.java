package kr.or.ddit.prod.service;

import kr.or.ddit.member.UserNotFoundException;
import kr.or.ddit.prod.dao.IProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements IProdService {
	private IProdDAO dao = ProdDAOImpl.getInstance();
	
	private static ProdServiceImpl self;
	private ProdServiceImpl() {}
	public static ProdServiceImpl getInstance() {
		if(self == null) self = new ProdServiceImpl();
		return self;
	}
	

	@Override
	public ProdVO retrieveProd(String prod_id) {
		ProdVO savedProd = dao.selectProd(prod_id);
		if(savedProd==null) {
			// custom exception 발생
			throw new RuntimeException("해당 상품이 존재하지 않음.");	// unchecked execption
		}
		return savedProd;
	}




	
}
