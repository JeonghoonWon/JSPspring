package kr.or.ddit.buyer.service;

import java.util.List;
import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public class BuyerServiceImpl implements IBuyerService {
	private IBuyerDAO dao = BuyerDAOImpl.getInstance();
	
	@Override
	public List<BuyerVO> retrieveBuyerList(PagingVO pagingVO) {
		return dao.selectBuyerList(pagingVO);
	}

	@Override
	public int retrieveBuyerCount(PagingVO pagingVO) {
		return dao.selectTotalRecord(pagingVO);
	}

	@Override
	public BuyerVO retrieveBuyer(String buyer_id) {
		BuyerVO buyer = dao.selectBuyer(buyer_id);
		if(buyer==null) {
			throw new CustomException(
				String.format("%s 에 해당하는 상품이 없음.", buyer_id)
			);
		}
		return buyer;
	}

	@Override
	public ServiceResult createBuyer(BuyerVO buyer) {
		ServiceResult result = null;
		int rowcnt = dao.insertBuyer(buyer);
		if(rowcnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult modifyBuyer(BuyerVO buyer) {
		ServiceResult result = null;
		int rowcnt = dao.updateBuyer(buyer);
		if(rowcnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult removeBuyer(String buyer_id) {
		ServiceResult result = null;
		int rowcnt = dao.deleteBuyer(buyer_id);
		if(rowcnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

}
