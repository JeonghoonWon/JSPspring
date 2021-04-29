package kr.or.ddit.container.hierarchy;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;

import kr.or.ddit.exemple.service.IExampleService;

//@Controller
public class ExampleController {
	private IExampleService service;
	
	@Inject // type 을 기준으로 서치
	public void setService(IExampleService service) {
		this.service = service;
	}
	
	
	public void process(String pk) {
		String info = service.readData(pk);
		System.out.println(info);
	}
}
// grouping 을 지어서 등록을 하고싶다면 기본 객체를 그냥 사용해선 안된다.
