package kr.or.ddit.prod.dao;

import java.util.List;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

/**
 * 상품관리를 위한 Persistance Layer
 *
 */
public interface IProdDAO {
	public ProdVO selectProd(String prod_id);
	public List<ProdVO> selectProdList(PagingVO<ProdVO> pagingVO);
	public int selectTotalRecord(PagingVO<ProdVO> pagingVO);
	public int insertProd(ProdVO prod);
	public int updateProd(ProdVO prod);
}















