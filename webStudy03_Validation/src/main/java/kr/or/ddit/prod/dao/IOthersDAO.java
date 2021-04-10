package kr.or.ddit.prod.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.BuyerVO;

public interface IOthersDAO {
		/**
	 * @return
	 */
	public List<Map<String, Object>> selectLprodList();
	/**
	 * @param buyer_lgu
	 * @return
	 */
	//@Param("prod_lgu") : 값은 이름이 없지만 @Param("prod_lgu") 이름을 생성해줄 수 있다.
	public List<BuyerVO> selectBuyerList(@Param("prod_lgu") String buyer_lgu);
}
