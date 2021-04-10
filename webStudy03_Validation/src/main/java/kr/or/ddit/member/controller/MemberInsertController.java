package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.validator.CommonValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.MemberVO;

// @WebServlet("/member/memberInsert.do") 
// @WebServlet("/member/memberInsert.do") 
// Marker annotation 이면서 SingleValue annotation
// front 뒤에서 동작하는 commend controller 가 되어야 함.

@Controller
public class MemberInsertController {
	private IMemberService service = new MemberServiceImpl();

	@RequestMapping("/member/memberInsert.do") 
	public String form() { // argument 가 필요 없다.
		
		return "member/memberForm";	// "member/memberForm"; : 논리적인 뷰네임
		
	}
	
	@RequestMapping(value = "/member/memberInsert.do", method = RequestMethod.POST) 
	public String process(@ModelAttribute("member") MemberVO member, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Locale.setDefault(Locale.ENGLISH);
		
		
		
//		2. 검증
		Map<String, List<String>> errors = new LinkedHashMap<>(); // List 로 넣으면 하나의 
		req.setAttribute("errors", errors);
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		Validator validator = factory.getValidator(); // 검증 수행

		
////		Set<ConstraintViolation<MemberVO>> violations = validator.validate(member);
//		
//		// size 가 0 인 경우 진행
//		boolean valid = violations.size() == 0;
//		if(!valid) {
//			for(ConstraintViolation<MemberVO> violation : violations) {
//				// 누구의 검증 결과인지 알아야한다. get propertyPath 로 값을 가져온다.
//				String propName = violation.getPropertyPath().toString();
//				String message = violation.getMessage();
//				List<String> already = errors.get(propName);
//				if(already==null) {
//					already = new ArrayList<>();
//					errors.put(propName, already);
//					
//				}
//				already.add(message);
//			}
//			
//		}
//		boolean valid = validate(member, errors);
		boolean valid =
				new CommonValidator<MemberVO>().validate(member,errors,InsertGroup.class);
		
		String view = null;
		String message = null;
		if (valid) {
			ServiceResult result = service.createMember(member);

			switch (result) {
			case PKDUPLICATED:
				view = "member/memberForm";
				message = "아이디 중복";
				break;
			case OK:
				view = "redirect:/login/loginForm.jsp";
				
				break;
			default:
				view = "member/memberForm";
				message = "서버오류 잠시 뒤 다시 시도하세요.";
				break;
			}
		} else {
			// 검증 불통
			view = "member/memberForm";
		}

		req.setAttribute("message", message);

		return view;
	}

	
}
