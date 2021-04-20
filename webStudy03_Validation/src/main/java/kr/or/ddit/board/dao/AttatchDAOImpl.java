package kr.or.ddit.board.dao;

import java.util.List;

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
	public int insertAttatches(BoardVO board, SqlSession session) {
//		try(	// insert 가 수행될때 commit 이2번 실행된다. 이게 맞는건지 확인해야함.
//				SqlSession session = sessionFactory.openSession();
				// 여기서 session 을 또 실행 할 수 없음.
//			){
		 return session.insert("kr.or.ddit.board.dao.IAttatchDAO.insertAttatches",board); // 이 한줄로 
		 
		 // 아래 내용은 필요 없어진다.
//				IAttatchDAO mapper = session.getMapper(IAttatchDAO.class);
//				int cnt =  mapper.insertAttatches(board, session);
////				session.commit();
//				return cnt;
			}
//		}
	@Override
	public AttatchVO selectAttatch(int att_no) {
		try(
		         SqlSession session = sessionFactory.openSession();
		      ){
		         IAttatchDAO mapper = session.getMapper(IAttatchDAO.class);
		         return mapper.selectAttatch(att_no);
		      }
		   }

	@Override
	public int deleteAttatches(BoardVO board, SqlSession session) {
		return session.delete("kr.or.ddit.board.dao.IAttatchDAO.deleteAttatches",board);
	}
	
	@Override
	public List<String> selectSaveNameForDelete(BoardVO board) {
		try(
	         SqlSession session = sessionFactory.openSession();
	      ){
	         IAttatchDAO mapper = session.getMapper(IAttatchDAO.class);
	         return mapper.selectSaveNameForDelete(board);
	      }
	   }

}
