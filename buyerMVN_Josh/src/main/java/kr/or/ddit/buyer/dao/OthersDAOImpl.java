package kr.or.ddit.buyer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;

import kr.or.ddit.vo.ProdVO;

public class OthersDAOImpl implements IOthersDAO {

	// 싱글톤 구조를 위한 생성자
	private static OthersDAOImpl self;
	private OthersDAOImpl() {}
	public static OthersDAOImpl getInstance() {
		if(self==null) self = new OthersDAOImpl();
		return self;
	}
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
	public List<ProdVO> selectProdList(String prod_lgu) {
		try(
			SqlSession session = sessionFactory.openSession();
		){
			IOthersDAO mapper = session.getMapper(IOthersDAO.class);
			return mapper.selectProdList(prod_lgu);
		}
	}

}
