package kr.or.ddit.buyer.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;

public class OthersDAOImpl implements IOthersDAO {
	private static OthersDAOImpl self;
	private OthersDAOImpl() {}
	
	public static OthersDAOImpl getInstance() {
		if(self==null) self = new OthersDAOImpl();
		return self;
	}
	
	private SqlSessionFactory sessionFactory =
			 CustomSqlSessionFactoryBuilder.getSessionFactory();
	
	@Override
	public List<Map<String, Object>> selectLprodList() {
		try(
			SqlSession session = sessionFactory.openSession();
		){
			IOthersDAO mapper = session.getMapper(IOthersDAO.class);
			return mapper.selectLprodList();
		}
	}
}