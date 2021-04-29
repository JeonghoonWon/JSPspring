package kr.or.ddit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

//meta annotation 구조
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//test용 가상의 context 를 만들어 빈을 관리할 수 있도록 하기 위함. 설정파일의 위치를 알려준다.
//@ContextConfiguration 이 2개 이기 때문에 계층구조를 형성해줘야함. @ContextHierarchy 의 배열 안에 원하는 순서 대로 입력
@ContextHierarchy({
	@ContextConfiguration("file:webapp/WEB-INF/spring/*-context.xml")
	, @ContextConfiguration("file:webapp/WEB-INF/spring/appServlet/servlet-context.xml")
	})
//가상의 container를 만들때 web 용으로 사용하기 위해
@WebAppConfiguration
@Transactional // 테스트가 끝나면 알아서 ROLLBACK 해준다.
public @interface TestWebAppConfiguration {

}
