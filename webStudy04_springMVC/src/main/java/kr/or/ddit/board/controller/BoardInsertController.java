package kr.or.ddit.board.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.utils.RegexUtils;
import kr.or.ddit.validator.BoardInsertGroup;
import kr.or.ddit.validator.NoticeInsertGroup;
import kr.or.ddit.vo.BoardVO;

@Controller
public class BoardInsertController {
	private String[] filteringTokens = new String[] {"말미잘","해삼"};
	
	@Inject
	private IBoardService service;

	//공지 게시글
	@RequestMapping("/board/noticeInsert.do")
	public String noticeForm(@ModelAttribute("board") BoardVO board) {
		board.setBo_type("NOTICE");
		return "board/boardForm";
		
	}
	
	@RequestMapping(value = "/board/noticeInsert.do",method=RequestMethod.POST)
	public String noticeInsert(
						@Validated(NoticeInsertGroup.class)	 BoardVO board  //coc 를 이용해서 자동으로 request에 등록 .(BoardVO = boardVO의 이름으로) 
						, BindingResult errors  
						// BindingResult : errors 의 상속 수조를 가지고 있음.  검증의 결과를 BindingResult 로 받아올땐 검증의 대상 바로 다음에 적용해준다. 위치가 중요. 어떤부분을 검증해서 결과값이 나오는지 구분.
						, Model model) {
//		req.setAttribute("groupHint", NoticeInsertGroup.class);
		return insert(board,errors,model);
	}
	
	
	@RequestMapping("/board/boardInsert.do")
	public String form(@ModelAttribute("board") BoardVO board
			, @RequestParam(value="parent", required=false, defaultValue="0") int parent 
				// 0이나 -숫자를 세팅해둬야함. 1이상을 사용하면 부모게시글이 될 수도 있다.
			) {
		board.setBo_type("BOARD");
		board.setBo_parent(parent); // parent 값을 넣어준다.
		return "board/boardForm";
	}
	

	
	@RequestMapping(value = "/board/boardInsert.do", method = RequestMethod.POST)
	public String insert(
						 // @Valid 어떻게 그룸힌트를 적용할 것인가? 속성이 없기 때문에 valid 는 사용 할 수 없음 그 대신, @Validated 사용가능.
						@Validated(BoardInsertGroup.class)
						// 그럼 통과하지못한 errors 는 어떻게 받아올 것인가?
						@ModelAttribute("board")BoardVO board // command object , 이 오브젝트를 통해 클라이언트가보내는 모든 정보를 받겠다.
						// "board" 는 model name, command name 이라고도 한다.
						, Errors errors
						,Model model) { // 모든 데이터는 H.A 
		

		
//		// req의 scope 를 사용해서 class를 담아오고 그걸 class 로 받아서 동적으로 받아준다.
//		Class<?> groupHint = (Class<?>) req.getAttribute("groupHint");
//		if(groupHint == null) 
//			groupHint = BoardInsertGroup.class;
		boolean valid = !errors.hasErrors(); //!errors.hasErrors(); : 에러가 없다면, 의 의미
		
//		Set<ConstraintViolation<BoardVO>> errors = validator.validate(board,groupHint); // Set<ConstrationViolation<BoardVO>> : 문제가되는 부분을 set 집합객체로 받아온다. 
//		boolean valid = errors.size()==0;
		String view = null;
		String message = null;
		
		if(valid) {
			// 비속어 필터링 적용
			String replaceText =
			RegexUtils.filteringTokens(board.getBo_content(),'ㅁ',filteringTokens);
			board.setBo_content(replaceText);
			
			ServiceResult result = service.createBoard(board);
			if(ServiceResult.OK.equals(result)) {
				view = "redirect:/board/boardView.do?what="+board.getBo_no();
				// 현재 request 정보를 가져갈 필요가 없기 때문에 
				// jsp 가 아닌 "redirect:/prod/prodView.do" 로 보내야 한다.
				// 
			}else {
				message = "서버 오류";
				view = "board/boardForm";
			}
		}else {
			view = "board/boardForm";
		}
		
		model.addAttribute("message", message);
		
		return view;
	}


}