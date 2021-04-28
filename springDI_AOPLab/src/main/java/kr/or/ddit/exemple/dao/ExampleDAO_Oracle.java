package kr.or.ddit.exemple.dao;

import org.springframework.stereotype.Repository;

@Repository
public class ExampleDAO_Oracle implements IExampleDAO {

	public ExampleDAO_Oracle() {
		super();
		System.out.println(getClass().getSimpleName()+"객체 생성");
	}
	
	// 외부에서 넘어오는 callback method 이기 때문에 서버로 return 할 필요 없다. void 로 설정해둔다.
	public void init() {
		System.out.println(getClass().getSimpleName()+"객체 초기화");
	}
	
	public void destroy() {
		System.out.println(getClass().getSimpleName()+"객체 소멸");
	}
	
	@Override
	public String selectData(String pk) {
		
		return String.format("%s 로 Oracle에서 조회된 raw data", pk);
	}
	
	
}
