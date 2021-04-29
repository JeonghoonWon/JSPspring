package kr.or.ddit.container.collection;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class CollectionDITestView {
	public static void main(String[] args) {  // main 으로 init 생성
		ConfigurableApplicationContext container = 
				new GenericXmlApplicationContext("classpath:kr/or/ddit/container/conf/collectionDI-context.xml"); // 대상을 결정할때 동적으로  // classpath: resource loader 가 실행되도록 처리
		
		container.registerShutdownHook(); // close 시켜주는 기능
		CollectionDIVO cvo1 = container.getBean("cvo1",CollectionDIVO.class); // class에 있는 타입을 기준으로 찾눈다
		System.out.println(cvo1);
		CollectionDIVO cvo2 = container.getBean("cvo2",CollectionDIVO.class); // class에 있는 타입을 기준으로 찾눈다
		System.out.println(cvo2);
	}
}
