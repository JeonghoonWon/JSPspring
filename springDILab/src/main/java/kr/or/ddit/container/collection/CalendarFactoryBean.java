package kr.or.ddit.container.collection;

import java.util.Calendar;

import org.springframework.beans.factory.config.AbstractFactoryBean;


public class CalendarFactoryBean extends AbstractFactoryBean<Calendar> {
	
	// 기본생성자를 하나더 만듬
	public CalendarFactoryBean(){
		setSingleton(false);
		
	}
	
	@Override
	public Class<?> getObjectType() {
	
		return Calendar.class;
	}

	@Override
	protected Calendar createInstance() throws Exception {
		return Calendar.getInstance(); 
	}

}
