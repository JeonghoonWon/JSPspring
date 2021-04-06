package kr.or.ddit.mybatis;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.DBPropertyVO;

public class MybatisTestView {
	private static SqlSessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(MybatisTestView.class);
	// kr.or.ddit  경로를 사용하는 것
	static {
		String xmlRes = "kr/or/ddit/mybatis/Configuration.xml";
		try( 
			Reader reader = Resources.getResourceAsReader(xmlRes);
		){
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		}catch (IOException e) {
			// 버츄얼 머신까지 보내줘야 함  RuntimeException(e);
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		try (
		SqlSession session = sessionFactory.openSession();
				
		){
			IDBPropertyDAO mapper = session.getMapper(IDBPropertyDAO.class);
			// jdk 안에서 프록시가 만들어지려면 interface 가 필요.
			// 없으면 jdk 생성 될 수 없다. 
			// 구현체를 만들 적 이 없지만 사용가능. 가상을 사용???
			List<DBPropertyVO> propList = mapper.selectDBPropertyList();
			for(DBPropertyVO tmp : propList) {
				// sysout 대신 로그 사용  
				// message argument : {}
				logger.info("{}", tmp);
			}
		}
		
		
	}
}
