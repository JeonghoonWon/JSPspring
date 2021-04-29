package kr.or.ddit.prod.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.TestWebAppConfiguration;
import kr.or.ddit.vo.ProdVO;

@RunWith(SpringJUnit4ClassRunner.class)  // RunWith annotation은 meta annotation 으로 사용할 수 없다.
@TestWebAppConfiguration
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
