package kr.or.ddit.buyer.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public class BuyerDAOImpl implements IBuyerDAO {
	private static BuyerDAOImpl self;
	private BuyerDAOImpl() {}
	
	public static BuyerDAOImpl getInstance() {
		if(self==null) self = new BuyerDAOImpl();
		return self;
	}
	
	private SqlSessionFactory sessionFactory =
			 CustomSqlSessionFactoryBuilder.getSessionFactory();
	
	@Override
	public List<BuyerVO> selectBuyerList(PagingVO pagingVO) {
		try(
			SqlSession session = sessionFactory.openSession();
		){
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			return mapper.selectBuyerList(pagingVO);
		}
	}

	@Override
	public int selectTotalRecord(PagingVO pagingVO) {
		try(
			SqlSession session = sessionFactory.openSession();
		){
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			return mapper.selectTotalRecord(pagingVO);
		}
	}

	@Override
	public BuyerVO selectBuyer(String buyer_id) {
		try(
			SqlSession session = sessionFactory.openSession();
		){
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			return mapper.selectBuyer(buyer_id);
		}
	}

	@Override
	public int insertBuyer(BuyerVO buyer) {
		try(
			SqlSession session = sessionFactory.openSession();
		){
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			int cnt =  mapper.insertBuyer(buyer);
			session.commit();
			return cnt;
		}
	}

	@Override
	public int updateBuyer(BuyerVO buyer) {
		try(
			SqlSession session = sessionFactory.openSession();
		){
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			int cnt =  mapper.updateBuyer(buyer);
			session.commit();
			return cnt;
		}
	}

	@Override
	public int deleteBuyer(String buyer_id) {
		try(
			SqlSession session = sessionFactory.openSession();
		){
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			int cnt =  mapper.deleteBuyer(buyer_id);
			session.commit();
			return cnt;
		}
	}

}