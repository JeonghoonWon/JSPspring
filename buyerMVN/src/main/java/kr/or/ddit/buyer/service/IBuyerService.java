package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;


/**
 * 거래처 관리를 위한 Business Logic Layer
 *
 */
public interface IBuyerService {

	/**
	 * 거래처 상세 조회
	 * @param buyer_id
	 * @return 해당 거래처가 존재하지 않는 경우, CustomException 발생
	 */
	public BuyerVO retrieveBuyer(String buyer_id);
		
	/**
	 * 페이징 적용된 상품 목록 조회
	 * @param pagingVO
	 * @return
	 */
	public List<BuyerVO> retrieveBuyerList(PagingVO<BuyerVO> pagingVO);
		
	/**
	 * 페이징 적용된 상품 건수
	 * @param pagingVO
	 * @return
	 */
	public int retrieveBuyerCount(PagingVO<BuyerVO> pagingVO);
}
