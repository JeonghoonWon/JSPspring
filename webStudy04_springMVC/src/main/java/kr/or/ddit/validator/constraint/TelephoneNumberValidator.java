package kr.or.ddit.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

// 번호는 문자열 이기 때문에 String 으로 잡아준다.
public class TelephoneNumberValidator implements ConstraintValidator<TelephoneNumber,String>{
	
	//annotation을 전역 변수로 선언해준다.
	private TelephoneNumber annotation;
	
	@Override
	public void initialize(TelephoneNumber constraintAnnotation) {
		// init
		this.annotation = constraintAnnotation;
	}
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(StringUtils.isBlank(value)) return true;
		
		String regexp = annotation.regexp();
		// boolean 
		return value.matches(regexp);
		
		}

}
