package kr.or.ddit.container.javaconfig;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.ddit.container.various.VariousDIVO;

public class JavaConfigTestView {
	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				new AnnotationConfigApplicationContext(JavaConfiguration.class); // 이렇게 하면 container 가 생성된것
		context.registerShutdownHook();
		
		VariousDIVO vo1 = context.getBean(VariousDIVO.class);
		VariousDIVO vo2 = context.getBean(VariousDIVO.class);
		System.out.println(vo1==vo2);
		
	}
}
