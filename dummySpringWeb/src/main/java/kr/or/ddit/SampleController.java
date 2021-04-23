package kr.or.ddit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//controller annotation : 1. bean 으로  자동 등록  2. H.M 가  자동 수집 할 수 있도록 처리 됨.
@Controller
@RequestMapping("/test") // requestMapping 을 밖으로 빼서 공통으로 사용하는걸 지정해둔다. 구분자로 슬러시(/)가 들어간다. ex) /test/sample1.do
public class SampleController {
	@RequestMapping(value = "sample1.do",method=RequestMethod.GET) //기본적으로  get, post 를 둘다 받을 수 있다. but, 메서드 제한을 걸고싶으면 method 를 입력하면 된다.
	public String sample1(Model model) { // req, parameter, requestpart 등 필요한거 입력해서 사용하면 된다. but, model로 모든걸 사용 가능.
		
		model.addAttribute("data","전달한 모델 "); // 이렇게 사용하면 req 를 사용하는거와 같다.
		
		return "sample/view"; 
	}
	
	@RequestMapping("sample2.do")
	public ModelAndView sample2() {  // void 로 해두고 view name 을 생략해두면  view name transrator가 자동적으로 작동  이럴땐, ModelAndView를 사용해서 return 값을 준다.
		ModelAndView mav = new ModelAndView();
		mav.addObject("data","전달한 모델");
		mav.setViewName("sample/view"); // retrun 값이 없어도 modelandview 로 넘겨줄 수 있다.
		return mav;
	}
}
