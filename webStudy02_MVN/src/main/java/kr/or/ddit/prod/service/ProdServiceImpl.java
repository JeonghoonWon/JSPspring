package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.member.UserNotFoundException;
import kr.or.ddit.prod.dao.IProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements IProdService {
	private IProdDAO dao = ProdDAOImpl.getInstance();
	
	// 싱글톤 
	private static ProdServiceImpl self;
	private ProdServiceImpl() {}
	public static ProdServiceImpl getInstance() {
		if(self == null) self = new ProdServiceImpl();
		return self;
	}
	

	@Override
	public ProdVO retrieveProd(String prod_id) {
		// 상품 하나의 정보 .가져오기 
		ProdVO savedProd = dao.selectProd(prod_id);
		if(savedProd==null) {
			// custom exception 발생
			throw new CustomException("해당 상품이 존재하지 않음.");	// unchecked execption
		}
		return savedProd;
	}
	@Override
	public List<ProdVO> retrieveProdList(PagingVO pagingVO) {
		
		return dao.selectProdList(pagingVO);
	}
	@Override
	public ServiceResult createProd(ProdVO prod) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int retrieveProdCount() {
		return dao.selectTotalRecord();
	}




	
}
