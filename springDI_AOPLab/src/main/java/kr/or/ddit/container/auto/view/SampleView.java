package kr.or.ddit.container.auto.view;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import kr.or.ddit.container.auto.service.ISampleService;

@Component
public class SampleView {
	private ISampleService service;
	@Resource(name = "sampleService") // 찾는 이름을 명시적으로 지정,바꿔줄 수 있다. autowired 는 불가.
	// setter 생성
	@Required // setter는 주입이 있어야 실행되기 때문에 무조건 실행시키기 위해 required annotation 사용해준다.
//	@Autowired // injection 을 시작한다. 
	public void setService(ISampleService service) {
		this.service = service;
	}
	
	public void view() {
		System.out.println(service.readData("a001"));
	}
	
	public static void main(String[] args) {
		// 1. 컨테이너 객체 생성 다오 서비스 뷰 를 컨테이너에 등록 해줘야함. -> 의존 관계도 형성 해줘야 함.
		ConfigurableApplicationContext context = 
				new ClassPathXmlApplicationContext("kr/or/ddit/container/conf/autoDI-context.xml");
		
		context.registerShutdownHook();
		
		SampleView sampleView = context.getBean(SampleView.class); // SampleView.class 를 받아야 거기 있는 service 사용 가능?
		sampleView.view();
	}
	
}
