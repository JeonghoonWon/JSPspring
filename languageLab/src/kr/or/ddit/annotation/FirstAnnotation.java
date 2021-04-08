package kr.or.ddit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // enum 상수로 정한다.
@Retention(RetentionPolicy.RUNTIME) // SingleValue annotation
public @interface FirstAnnotation {
	String value() default "기본값"; // 메서드를 정의한것 처럼 보이지만 속성을 정한것.
	int number();
	

}
