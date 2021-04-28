package kr.or.ddit.exemple.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.or.ddit.exemple.service.IExampleService;

public class ExampleSpringView {
	public static void main(String[] args) {
// 3. 컨테이너의 구현체를 생성. 그 구현체을 통해 컨테이너에 접근
		
		
		// di 컨테이너를 주입자로 선정한다면 new 라는 객체 생성은 1번만 수행하면 된다.
		ApplicationContext container = 
					new ClassPathXmlApplicationContext("kr/or/ddit/example/conf/example-context.xml");   
														// () : 설정파일의 이름과 위치를 알려준다.  /springDILab/src/main/resources/ 의 경로는 classPath 가 가지고 있기때문에 삭제 해준다.
		
		// 등록된 객체를 받아서 사용하면 된다. 
		
//		IExampleService service = 
//				(IExampleService) container.getBean("exampleServiceImpl"); // 식별자를 주어야한다. (id)
		//위 방법은 케스팅을 해야하는 번거로움이 있다.  다른 방법을 찾아보자
//		IExampleService service = container.getBean(IExampleService.class); 
		// 방법은 좋지만 단점이 있다. 하나의 타입으로 빈을 두개 등록한경우. 어떤걸 넣어줄 지 모르기때문에 exception 
		// 자 , 다른 방법을 찾아보자!
		IExampleService service = container.getBean("exampleServiceImpl",IExampleService.class);
		// 가장 안전한 방법이 된다.
		
		String info = service.readData("a001");
		System.out.println(info); //ref="exampleDAO_Oracle" => a001 로 Oracle에서 조회된 raw data를 가공한 information
									// ref="exampleDAO_MySql" => a001 로  MySql 에서 조회된 raw data를 가공한 information
		
		//클로즈를 해줄 수 없다.??????
		
	}
}
