package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public class BuyerServiceImpl implements IBuyerService {
	
	private static BuyerServiceImpl self;
	private BuyerServiceImpl() {}
	public static BuyerServiceImpl getInstance() {
		if(self==null) self = new BuyerServiceImpl();
		return self;
	}	
	private IBuyerDAO dao = BuyerDAOImpl.getInstance();
	@Override
	public BuyerVO retrieveBuyer(String buyer_id) {
		// 상품 하나의 정보 가져오기
		BuyerVO buyer = dao.selectBuyer(buyer_id);
		if(buyer==null)
			throw new CustomException(
					String.format("%s 에 해당하는 거래처 없음.",buyer_id)
				);
		return buyer;
	}
	@Override
	public List<BuyerVO> retrieveBuyerList(PagingVO<BuyerVO> pagingVO) {
		return dao.selectBuyerList(pagingVO);
	}
	@Override
	public int retrieveBuyerCount(PagingVO<BuyerVO> pagingVO) {
		return dao.selectTotalRecord(pagingVO);
	}
	
	
}
