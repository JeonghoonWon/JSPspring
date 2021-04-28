package kr.or.ddit.container.collection;

import org.springframework.beans.factory.FactoryBean;

public class StringArrayFactoryBean implements FactoryBean<String[]> {
	// FactoryBean<T> : String type 의 배열이 필요하기 때문에  T 에 String[] 입력
	
	@Override
	public String[] getObject() throws Exception {
		// TODO Auto-generated method stub
		return new String[] {"value1", "value2"};
	}

	@Override
	public Class<?> getObjectType() {
		
		return String[].class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;  
		// false : 매번 주입을 할때마다 새로운 싱글톤이 생성되는것. proto type scope가 되는것.
		// true : 한번 만 생성. 싱글톤이 되는것.
	
	} 
	
	
}
