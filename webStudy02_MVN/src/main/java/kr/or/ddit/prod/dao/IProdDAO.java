package kr.or.ddit.prod.dao;

import kr.or.ddit.vo.ProdVO;

public interface IProdDAO {
	
	
	/**
	 * PK 를 기준으로 상품 조회
	 * @param prod_id
	 * @return 존재하지 않는 경우, null 반환
	 */
	public ProdVO selectProd(String prod_id);
}
