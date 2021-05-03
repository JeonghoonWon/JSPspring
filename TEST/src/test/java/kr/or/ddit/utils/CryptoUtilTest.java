package kr.or.ddit.utils;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoUtilTest {
	private static final Logger logger =
			LoggerFactory.getLogger(CryptoUtilTest.class);

	@Test
	public void testSha512() throws NoSuchAlgorithmException {
	// 여기서 발생한 예외는 jUnit 으로 넘어간다. 프레임워크로 가는것.
		//	fail("Not yet implemented"); 
	
		String str = "java";
		String encoded = CryptoUtil.sha512(str);
		
		logger.debug("인코딩 된 값 : {}", encoded);
		
	
	}

}
