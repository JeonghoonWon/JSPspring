package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

/**
 * 상품관리를 위한 Business Logic Layer
 *
 */
public interface IProdService {
	
	/**
	 * 상품 상세 조회
	 * @param prod_id
	 * @return 해당 상품이 존재하지 않는경우, RuntimeException발생
	 */
	public ProdVO retrieveProd(String prod_id);
	public List<ProdVO> retrieveProdList(PagingVO pagingVO);
	public ServiceResult createProd(ProdVO prod);	//createProd 는 성공, 실패로 나뉘기 때문에 ServiceResult 
	public ServiceResult modifyProd(ProdVO prod);
	public int retrieveProdCount();
	
}
