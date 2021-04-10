package kr.or.ddit.buyer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.ProdVO;

public interface IOthersDAO {
	/**
	 * @return
	 */
	public List<Map<String, Object>> selectLprodList();

	
	/**
	 * @param prod_lgu
	 * @return
	 */
	//@Param("buyer_lgu") : 값은 이름이 없지만@Param("buyer_lgu") 이름을 생성해줄 수 있다.
	public List<ProdVO> selectProdList(@Param("buyer_lgu") String prod_lgu);
}
