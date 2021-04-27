package kr.or.ddit.prod.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;

@Repository
public class OthersDAOImpl implements IOthersDAO {

	// sessionFactory 받아오기
	private SqlSessionFactory sessionFactory = 
			CustomSqlSessionFactoryBuilder.getSessionFactory();

	@Override
	public List<Map<String, Object>> selectLprodList() {
		try(//open
			SqlSession session = sessionFactory.openSession();
		){	//selectProd 실행
			IOthersDAO mapper = session.getMapper(IOthersDAO.class);
			return mapper.selectLprodList();
		}
	}

	@Override
	public List<BuyerVO> selectBuyerList(String buyer_lgu) {
		try(
			SqlSession session = sessionFactory.openSession();
		){
			IOthersDAO mapper = session.getMapper(IOthersDAO.class);
			return mapper.selectBuyerList(buyer_lgu);
		}
	}

}
