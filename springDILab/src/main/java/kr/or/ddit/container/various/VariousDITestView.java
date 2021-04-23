package kr.or.ddit.container.various;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;

public class VariousDITestView {
	public static void main(String[] args) {
		// 구현해보고 실제로 찍어보기
	ConfigurableApplicationContext container = 
				new GenericXmlApplicationContext("classpath:kr/or/ddit/container/conf/variousDI-context.xml");
	
	VariousDIVO vo1 = container.getBean("vo2",VariousDIVO.class);
	System.out.println(vo1);
		
	

	}

}
