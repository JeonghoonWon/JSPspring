package kr.or.ddit.db.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class CustomSqlSessionFactoryBuilder {
	private static SqlSessionFactory sessionFactory;
	
	static {
		String xmlRes = "kr/or/ddit/db/mybatis/Config.xml";
		try(
			Reader reader = Resources.getResourceAsReader(xmlRes);
				// return type 을 Reader 로 받기 위해.
		){
			sessionFactory =
			new SqlSessionFactoryBuilder().build(reader);			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static SqlSessionFactory getSessionFactory() {
		return sessionFactory;
		
	}
}
