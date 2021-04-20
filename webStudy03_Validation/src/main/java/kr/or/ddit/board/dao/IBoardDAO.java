package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

/**
 * 게시글 관리를 위한 persistence layer
 *
 */
public interface IBoardDAO {
	
	//session 을 open 하지 않고 insertBoard 처리 해야함.
	public int insertBoard(BoardVO board, SqlSession session);
	public int selectBoardCount(PagingVO<BoardVO> pagingVO);
	public List<BoardVO> selectBoardList(PagingVO<BoardVO> pagingVO);
	public BoardVO selectBoard(BoardVO search); // 검색값이 없는 경우 null 값이 나와야 함. 글번호와 board type 을 검색 조건으로 걸어둔다. 
	public int updateBoard(BoardVO board, SqlSession session); // 업데이트 성공실패여부 int
	public int deleteBoard(BoardVO search); // ex)일반 게시글의 3번글을 지울수도 있기 떄문에 검색 을 넣어준다.
	
}
