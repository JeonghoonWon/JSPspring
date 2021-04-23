package kr.or.ddit.exemple.service;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import kr.or.ddit.exemple.dao.ExampleDAOFactory;
import kr.or.ddit.exemple.dao.ExampleDAO_MySql;
import kr.or.ddit.exemple.dao.ExampleDAO_Oracle;
import kr.or.ddit.exemple.dao.IExampleDAO;

@Service
@Scope("prototype")
public class ExampleServiceImpl implements IExampleService {
	
	//BLL 이 PL 을 사용할 수밖에없는 구조. 의존관계
	// 1. new 키워드로 인스턴스 직접 생성
	// private IExampleDAO dao = new ExampleDAO_Oracle();
	// dao 를 바꾸기위해 서비스까지 바꿔야 하는 상황? 결합력이 너무 높은 상황. 이게 자연스러운상황은 아니다. 
	// 서비스와 다오의 결합력을 떨어트려야 한다. 
	// 방법은 = new ExampleDAO_MySql();  를 없애야 한다.
	// 다양한 페턴 알아보자!
	
	// private IExampleDAO dao = new ExampleDAO_MySql(); 
	
	// 의존관계 형성하는 두번째 방법 
	// 2. Factory Object Pattern
	//private IExampleDAO dao = ExampleDAOFactory.getExampleDAO();
	
	//3. strategy pattern (Dependency Injection) / 전략 패턴
	// 생성자 주입, setter 주입  (무엇을 쓸지 정하는건 전략의 필수성에 따라 달라짐) , 전략의 주입자가 필요함.
	// 전략 패턴에서의 핵심 : 내가 핵심을 만들지 않는다. 그렇다면 전략의 주입자가 필요한 상황이다. 
	// 서비스와 다오의 결합력이 없어진것. 다만 view 에 결합력이 넘어간 상황.view를 수시로 바꿔야 한다.
	// 전략의 주입자가 어플리케이션 밖에 있으면 결합력 이 없어진다 . 주입자가 외부에 있다는 것은 그만큼 어플리케이션의 결합력이 사라진다는 것.
	private IExampleDAO dao;

	public ExampleServiceImpl() {
		super();
		System.out.println(getClass().getSimpleName()+"객체 생성 - 기본 생성자");
	}
	
	public ExampleServiceImpl(IExampleDAO dao) {
		super();
		this.dao = dao;
		System.out.println(getClass().getSimpleName()+"객체 생성 - argument 있는 생성자");
	}
	// setter 사용해서 필요한 객체를 받아 쓸 수있게 처리하면 결합력을 사라지게 할 수 있다.
	//내가 사용하는 주요한객체를 실시간으로 변경할 수 있다. 전략 패턴
	
	@Resource(name="mySql")
	//	@Inject
	@Required
	public void setDao(IExampleDAO dao) {
		this.dao = dao;
		System.out.println(getClass().getSimpleName()+"에서 setter injection 받음.");
		
	}
	// 주입이 끝난 뒤, init 실행 ???
	public void init() {
		System.out.println(getClass().getSimpleName()+"객체 초기화");
	}
	
	public void destroy() {
		System.out.println(getClass().getSimpleName()+"객체 소멸");
	}
	
	
	@Override
	public String readData(String pk) {
		String rawData = dao.selectData(pk);
		String info = rawData +"를 가공한 information";
		return info;
	}

}
