package kr.or.ddit.container.collection;

import org.springframework.beans.factory.FactoryBean;

public class CalendarFactoryBean2 implements FactoryBean<String> {

	@Override
	public String getObject() throws Exception {
		// TODO Auto-generated method stub
		return toString();
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false; // false : properties scope
	}

}
