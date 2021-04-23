package kr.or.ddit.container.hierarchy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.or.ddit.container.hierarchy.controller.HierarchyController;
import kr.or.ddit.container.hierarchy.service.HierarchyService;

public class ContainerHierarchyTestView {
	public static void main(String[] args) {
		// 부모 자식간의 상속 구조가 생성된다.
		// 아무리 계층 구조가 형성되더라도 부모는 자식껄 사용할 수 없다. 부모가 먼저 생기기 때문에
		// 형제 끼리도 사용 불가.
		ConfigurableApplicationContext parent = 
				new ClassPathXmlApplicationContext("kr/or/ddit/container/conf/hierarchy/root-context.xml");
		ConfigurableApplicationContext child = 
				new ClassPathXmlApplicationContext(new String[] {
						"kr/or/ddit/container/conf/hierarchy/child-context.xml"
				},parent);
		
		child.registerShutdownHook();
		
		HierarchyController controller = child.getBean(HierarchyController.class);
		
		HierarchyService service1 = parent.getBean(HierarchyService.class);
		HierarchyService service2 = child.getBean(HierarchyService.class);
		
		System.out.println(service1==service2);
		
//		ExampleController controller =  child.getBean(ExampleController.class);
//		controller.process("a001");
	}
}

//공통빈에대한 중복 해결