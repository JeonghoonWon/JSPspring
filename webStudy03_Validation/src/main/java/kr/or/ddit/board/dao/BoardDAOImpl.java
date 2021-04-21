package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

public class BoardDAOImpl implements IBoardDAO {
		private static BoardDAOImpl self;
		public BoardDAOImpl() {}
		public static BoardDAOImpl getInstance() {
			if(self==null) self = new BoardDAOImpl();
			return self;
		}
		
		private SqlSessionFactory sessionFactory = 
				CustomSqlSessionFactoryBuilder.getSessionFactory();	

	@Override
	public int insertBoard(BoardVO board, SqlSession session) {
		 return session.insert("kr.or.ddit.board.dao.IBoardDAO.insertBoard",board);  // mybatis 에 전달해야 하는 값만 전달 해준다. 
	}

	@Override
	public int selectBoardCount(PagingVO<BoardVO> pagingVO) {
		try(
				SqlSession session = sessionFactory.openSession();
			){
				IBoardDAO mapper = session.getMapper(IBoardDAO.class);
				return mapper.selectBoardCount(pagingVO);
			}
		}
	@Override
	public List<BoardVO> selectBoardList(PagingVO<BoardVO> pagingVO) {
		try(
				SqlSession session = sessionFactory.openSession();
			){
				IBoardDAO mapper = session.getMapper(IBoardDAO.class);
				return mapper.selectBoardList(pagingVO);
			}
		}


	@Override
	public BoardVO selectBoard(BoardVO search) {
		try(
				SqlSession session = sessionFactory.openSession();
			){
				IBoardDAO mapper = session.getMapper(IBoardDAO.class);
				return mapper.selectBoard(search);
			}
		}
	@Override
	public int updateBoard(BoardVO board, SqlSession session) {
		// interface 의 qualified name
		return session.update("kr.or.ddit.board.dao.IBoardDAO.updateBoard", board);
//		try(
//				SqlSession session = sessionFactory.openSession();
//			){
		
//				IBoardDAO mapper = session.getMapper(IBoardDAO.class);
//				int cnt = mapper.updateBoard(board, session);
////				session.commit();
//				return cnt; 
			}
//		}

	@Override
	public int deleteBoard(BoardVO search, SqlSession session) {
		
		return session.delete("kr.or.ddit.board.dao.IBoardDAO.deleteBoard", search);
		
	}

}
