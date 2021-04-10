package kr.or.ddit.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import kr.or.ddit.vo.MemberVO;

public class CommonValidator<T> {

	private Validator validator;
	// 생성자 코드블럭
	{
		ValidatorFactory factory = Validation.byDefaultProvider()
									.configure()
									.messageInterpolator(
											new ResourceBundleMessageInterpolator(
													new PlatformResourceBundleLocator("kr.or.ddit.messages.errorMessages")
											)
									).buildValidatorFactory(); // 내부적으로 builder pattern 이 사용되고있다.
		validator = factory.getValidator(); // 검증 수행

	}

	public boolean validate(T target, Map<String,List<String>> errors,Class<?>...groups) {
			Set<ConstraintViolation<T>> violations 
						= validator.validate(target, groups);
		
		// size 가 0 인 경우 진행
		boolean valid = violations.size() == 0;
		if(!valid) {
			for(ConstraintViolation<T> violation : violations) {
				// 누구의 검증 결과인지 알아야한다. get propertyPath 로 값을 가져온다.
				String propName = violation.getPropertyPath().toString();
				String message = violation.getMessage();
				List<String> already = errors.get(propName);
				if(already==null) {
					already = new ArrayList<>();
					errors.put(propName, already);
					
				}
				already.add(message);
			} // for end	
		} // if end
		return valid;
	}

}
