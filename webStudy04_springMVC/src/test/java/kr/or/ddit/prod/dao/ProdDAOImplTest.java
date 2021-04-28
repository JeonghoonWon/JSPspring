package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;

import kr.or.ddit.vo.ProdVO;

public class ProdDAOImplTest {
	@Inject
	private IProdDAO dao;
	
	
	
	@Test
	public void testSelectProd() {
	ProdVO prod = dao.selectProd("P101000001");
	assertNotNull(prod);
	assertEquals(2, prod.getUserList().size());
	}

}
