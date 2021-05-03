package kr.or.ddit.board.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.validator.DeleteGroup;
import kr.or.ddit.vo.BoardVO;

@Controller
public class BoardDeleteController {
	// service 에 있는 메서드를 사용할 수 있도록 처리 
	@Inject
	private IBoardService service;
	@RequestMapping(value="/board/boardDelete.do", method=RequestMethod.POST)
	public String delete(
		// board 로 넘어오는  BoardVO 를 넘겨 받는다.
		@Validated(DeleteGroup.class) @ModelAttribute("board")BoardVO board	
		, BindingResult errors
		, Model model
		, RedirectAttributes redirectAttribute // attribute 니까 속성을 만들어준다는것. redirect가 되는 중에서도 수행가능. session 에 담아진다.
	) {

		
		// delete group hint 를 적용한 검증
//		boolean valid = new CommonValidator<BoardVO>()
//						.validate(board, errors, DeleteGroup.class);
		String view = null;
		if(!errors.hasErrors()) {
			ServiceResult result = service.removeBoard(board);
			if(ServiceResult.OK.equals(result)) {
				view = "redirect:/board/boardList.do";
			}else {
				// session 이기 때문에 model 로 넣어주는건 의미가 없다.
				redirectAttribute.addFlashAttribute("message", "비밀번호 오류"); 
				//session에 메시지 들어가있고 꺼내면 바로 지워지게 자동 처리. ver(4.x) 부터 사용 가능
				view= "redirect:/board/boardView.do?what="+board.getBo_no(); 
			}
		}else {
			redirectAttribute.addFlashAttribute("message", "필수 데이터 누락");
			view= "redirect:/board/boardView.do?what="+board.getBo_no();
		}
		return view;
	
	}

}
