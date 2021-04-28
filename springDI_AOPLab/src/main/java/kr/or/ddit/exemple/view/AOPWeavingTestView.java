package kr.or.ddit.exemple.view;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.or.ddit.exemple.service.IExampleService;

public class AOPWeavingTestView {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = 
				new ClassPathXmlApplicationContext("kr/or/ddit/example/conf/*-context.xml");
		IExampleService target =
				context.getBean(IExampleService.class);
		String pk = "a001";
		String info = target.readData(pk);
		System.out.println(info);
		
	}
}
