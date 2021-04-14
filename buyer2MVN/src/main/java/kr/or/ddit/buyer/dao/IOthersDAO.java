package kr.or.ddit.buyer.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BuyerVO;

public interface IOthersDAO {
	public List<Map<String, Object>> selectLprodList();
}