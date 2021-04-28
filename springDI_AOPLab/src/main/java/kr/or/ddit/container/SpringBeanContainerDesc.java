package kr.or.ddit.container;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import kr.or.ddit.exemple.service.IExampleService;

/**
 * 
 * 
 * Spring Bean Container 사용단계
 * : bean 의 lifecycle 관리자
 * 1. spring container module 을 빌드패스에 추가
 * 		(beans, core, context, spEL)
 * 2. bean meta datas(bean definition metadata) 등록 파일 / 실제 맵핑에 대한 .,???????????????????
 *		1) bean 등록(bean 엘리먼트)
 *		2) 등록된 bean 들간의 의존관계 형성 (의존성 주입, dependency injection)
 *			- constructor injection (필수 전략 주입)
 *				constructor-arg , c namespace(3.1)
 *			- setter injection (optional 전략 주입)
 *				property, p namespace(3.0)
 *
 *	3. entry point(main) 에서  Container 객체 생성
 *		ApplicationContext 의 구현체
 *	4. getBean 으로 의존객체 주입
 *		- type 을 기준으로 한 주입 (두개 이상의 빈이 존재시 exception 발생)
 *		- id 를 기준으로 한 주입 (케스팅을 해야하는 번거로움이 있다)
 *		- 그래서 2개를 같이 사용해주는 방법이 가장 이상적.
 *
 *	5. 컨테이너 종료(shutdownHook 등록)
 *
 *	*** 컨테이너의 빈 관리 정책
 *	1. 특별한 설정(scope)이 없는 한 빈은 singleton 으로 관리됨.
 *		** 싱글톤의 대상은 빈이다!!!!!
 *		scope - singleton(기본 정책) - 하나의 빈은 하나의 객체
 *				prototype - 객체가 주입 될때마다 새로운 객체가 생성.
 *				request / session 주로 web application 에서 사용. 
 *			하나의 request 가 들어오면 하나의 빈이 생성
 *			하나의 session 안에서만 bean 생성 
 *
 *		스프링은 언제 객체를 생성할까?
 *	2. 특별한 설정(lazy-init) 이 없는 한 컨테이너가 초기화될때 등록된 빈의 모든 객체 생성.
 *		: 객체의 생성 시점ㅇ르 지연시키거나 생성 순서를 어느정도 제어할 수 있음.
 *	3. depends-on 을 이용하여 빈들간의 순서를 직접 제어도 가능은 함. 가능한 사용 안하는게 좋다. 서로가 의존 한다면 문제가 생긴다.(순환고리 생성)
 *	
 *	4. 생명주기 콜백 정의 가능
 *		*** init-method 는 필요한 모든 주입이 모든 끝난 후에 호출됨. 선생님이 많이 사용하시니까 잘 따라가자.	
 *
 */
public class SpringBeanContainerDesc {
	public static void main(String[] args) {
		ConfigurableApplicationContext container
					= new GenericXmlApplicationContext("classpath:kr/or/ddit/container/conf/spring-container.xml");
									// classpath : classpath 를 붙여주면 위치를 자동으로 찾아준다.
	container.registerShutdownHook(); // 데몬스레드가 돌아간다. 
	// 활성화 되어있는 스레드가 존재하지 않으면 컨테이너를 종료 시킨다.
	//container.close();를 적어주지않아도 처리된다.
		
//	IExampleService service1 = 
//			container.getBean("service1",IExampleService.class);
//	IExampleService service1_1 = 
//			container.getBean("service1",IExampleService.class);
//	IExampleService service2 = 
//			container.getBean("service2",IExampleService.class);
//	IExampleService service2_2 = 
//			container.getBean("service2",IExampleService.class);
//	System.out.println(service1.readData("a001"));
//	System.out.println(service1 == service2);  
//	System.out.println(service1 == service1_1);   // true : bean을 대상으로 비교
//	System.out.println(service2 == service2_2);   // true : bean을 대상으로 비교
	//System.out.println(service1 == service2);  주소값 비교 : true  싱글톤 패턴이다.
	//System.out.println(service1 == service2);  주소값 비교 : false 알고있던 싱글톤 패탠과는 조금 달라진다.
	
//	만들어지는 객체들 사이가 다를때는 싱글톤 패턴을 사용하면 안된다.
	
	
	//컨테이너를 닫아준다.
	//container.close();
	
	}
}
