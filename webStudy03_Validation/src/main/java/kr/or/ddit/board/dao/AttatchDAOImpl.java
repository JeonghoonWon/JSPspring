package kr.or.ddit.board.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.BoardVO;

public class AttatchDAOImpl implements IAttatchDAO {
	private static AttatchDAOImpl self;
	private AttatchDAOImpl() {}
	public static AttatchDAOImpl getInstance() {
		if(self==null) self = new AttatchDAOImpl();
		return self;
	}
	private SqlSessionFactory sessionFactory = 
			CustomSqlSessionFactoryBuilder.getSessionFactory();
	
	@Override
	public int insertAttatches(BoardVO board) {
		try(	// insert 가 수행될때 commit 이2번 실행된다. 이게 맞는건지 확인해야함.
				SqlSession session = sessionFactory.openSession();
			){
				IAttatchDAO mapper = session.getMapper(IAttatchDAO.class);
				int cnt =  mapper.insertAttatches(board);
				session.commit();
				return cnt;
			}
		}
	@Override
	public AttatchVO selectAttatch(int att_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteAttatches(BoardVO board) {
		// TODO Auto-generated method stub
		return 0;
	}

}
