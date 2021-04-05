package kr.or.ddit.prod.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.vo.ProdVO;

public class ProdDAOImpl implements IProdDAO {
	
	// 싱글톤 구조를위한 생성자
	private static ProdDAOImpl self;
	private ProdDAOImpl() {}
	public static ProdDAOImpl getInstance() {
		if(self == null) self = new ProdDAOImpl();
		return self;
	}
	
	private SqlSessionFactory sessionFactory =
			CustomSqlSessionFactoryBuilder.getSessionFactory();
	
	@Override
	public ProdVO selectProd(String prod_id) {
		try(//open
				SqlSession session = sessionFactory.openSession();
				){
					IProdDAO mapper = session.getMapper(IProdDAO.class);	
				return mapper.selectProd(prod_id);	
				}
			}

}
