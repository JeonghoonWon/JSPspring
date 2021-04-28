package kr.or.ddit.board.dao;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;

import kr.or.ddit.vo.BoardVO;

public class IBoardDAOTest {
	@Inject
	private IBoardDAO dao;
	
	@Test
	public void testSelectBoard() {
		BoardVO search = new BoardVO();
		search.setBo_no(1317);
		BoardVO board = dao.selectBoard(search);
		assertEquals(2, board.getAttatchList().size());
	
		search.setBo_no(1289);
		board = dao.selectBoard(search);
		assertEquals(0, board.getAttatchList().size());
	
	}

}
