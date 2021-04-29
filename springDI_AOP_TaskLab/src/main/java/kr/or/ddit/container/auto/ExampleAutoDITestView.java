package kr.or.ddit.container.auto;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.or.ddit.exemple.service.IExampleService;

public class ExampleAutoDITestView {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = 
				new ClassPathXmlApplicationContext("kr/or/ddit/container/conf/autoDI-context.xml");
		context.registerShutdownHook();
		
		IExampleService service = context.getBean(IExampleService.class);
		System.out.println(service.readData("a001"));
	}
}
