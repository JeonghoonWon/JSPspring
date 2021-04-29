package kr.or.ddit.board.dao;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import kr.or.ddit.TestWebAppConfiguration;
import kr.or.ddit.vo.BoardVO;

//Junit 과 Spring 을 연동하기 위한 Runtime 필요 : @RunWith(SpringJUnit4ClassRunner) 
@RunWith(SpringJUnit4ClassRunner.class)  // RunWith annotation은 meta annotation 으로 사용할 수 없다.
@TestWebAppConfiguration
public class IBoardDAOTest {  
	
	@Inject
	private IBoardDAO dao;
	@Inject
	private WebApplicationContext container;
	
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
