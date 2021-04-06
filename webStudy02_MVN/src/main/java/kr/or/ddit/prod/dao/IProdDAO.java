package kr.or.ddit.prod.dao;

import java.util.List;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

/**
 * 상품관리를 위한 Persistance Layer
 *
 */
public interface IProdDAO {
	
	/**
	 * PK 를 기준으로 상품 조회
	 * @param prod_id
	 * @return 존재하지 않는 경우, null 반환
	 */
	public ProdVO selectProd(String prod_id);
	
	/**
	 * @param pagingVO TODO
	 * @return
	 */
	public List<ProdVO> selectProdList(PagingVO pagingVO);
	
	/**
	 * @param prod
	 * @return int rowcount > 0 입력 성공
	 */
	public int insert(ProdVO prod);
	
	/**
	 * @param prod
	 * @return int rowcount > 0 입력 성공
	 */
	public int updateProd(ProdVO prod);
	
	//public deleteProd 
	
	public int selectTotalRecord();
	
}
