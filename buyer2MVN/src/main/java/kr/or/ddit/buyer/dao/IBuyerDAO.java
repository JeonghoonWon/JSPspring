package kr.or.ddit.buyer.dao;

import java.util.List;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public interface IBuyerDAO {
	public List<BuyerVO> selectBuyerList(PagingVO pagingVO);
	public int selectTotalRecord(PagingVO pagingVO);
	public BuyerVO selectBuyer(String buyer_id);
	public int insertBuyer(BuyerVO buyer);
	public int updateBuyer(BuyerVO buyer);
	public int deleteBuyer(String buyer_id);
}