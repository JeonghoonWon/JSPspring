package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.prod.dao.IProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements IProdService {
	private static ProdServiceImpl self;
	private ProdServiceImpl() {}
	public static ProdServiceImpl getInstance() {
		if(self==null) self = new ProdServiceImpl();
		return self;
	}
	private IProdDAO dao = ProdDAOImpl.getInstance();

	@Override
	public ProdVO retrieveProd(String prod_id) {
		// 상품 하나의 정보 .가져오기 
		ProdVO prod = dao.selectProd(prod_id);
		if(prod==null)
		// custom exception 발생
			throw new CustomException(
					String.format("%s 에 해당하는 상품이 없음.", prod_id)
				);
		return prod;
	}
	@Override
	public List<ProdVO> retrieveProdList(PagingVO<ProdVO> pagingVO) {
		return dao.selectProdList(pagingVO);
	}
	@Override
	public int retrieveProdCount(PagingVO<ProdVO> pagingVO) {
		return dao.selectTotalRecord(pagingVO);
	}
	@Override
	public ServiceResult createProd(ProdVO prod) {
		int cnt = dao.insertProd(prod);
		ServiceResult result = ServiceResult.FAIL;
		if(cnt>0)
			result = ServiceResult.OK;
		return result;
	}
	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		// TODO Auto-generated method stub
		return null;
	}

}





