package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public interface IBuyerService {
	public List<BuyerVO> retrieveBuyerList(PagingVO pagingVO);
	public int retrieveBuyerCount(PagingVO pagingVO);
	public BuyerVO retrieveBuyer(String buyer_id);
	public ServiceResult createBuyer(BuyerVO buyer);
	public ServiceResult modifyBuyer(BuyerVO buyer);
	public ServiceResult removeBuyer(String buyer_id);
}