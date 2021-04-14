package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
	// 필수 속성 (기본값이 없기 떄문에)
	String value();
	
	boolean required() default true;
	
	// 요청파라미터가 비어있다는 가정하에 만들어진다. String 으로 받는다.
	String defaultValue() default "";
}
