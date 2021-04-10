package kr.or.ddit.buyer.dao;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

/**
 * 거래처 관리를 위한 Persistance Layer
 *
 */
public interface IBuyerDAO {
	public BuyerVO selectBuyer(String buyer_id);
	public List<BuyerVO> selectBuyerList(PagingVO<BuyerVO> pagingVO);
	public int selectTotalRecord(PagingVO<BuyerVO> pagingVO);
	
}
