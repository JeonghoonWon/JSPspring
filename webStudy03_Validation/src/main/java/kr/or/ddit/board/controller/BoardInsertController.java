package kr.or.ddit.board.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RegExUtils;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.filter.wrapper.MultipartFile;
import kr.or.ddit.validator.CommonValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.validator.NoticeInsertGroup;
import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.utils.RegexUtils;

@Controller
public class BoardInsertController {
	private String[] filteringTokens = new String[] {"말미잘","해삼"};
	
	private IBoardService service = BoardServiceImpl.getInstance();

	//공지 게시글
	@RequestMapping("/board/noticeInsert.do")
	public String noticeForm(@ModelAttribute("board") BoardVO board) {
		board.setBo_type("NOTICE");
		return "board/boardForm";
		
	}
	
	@RequestMapping(value = "/board/noticeInsert.do",method=RequestMethod.POST)
	public String noticeInsert(
						@ModelAttribute("board") BoardVO board
						, HttpServletRequest req) {
		req.setAttribute("groupHint", NoticeInsertGroup.class);
		return insert(board, null, req);
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
	public String insert(@ModelAttribute("board")BoardVO board
						,@RequestPart(value="bo_files",required=false) MultipartFile[] bo_files
						,HttpServletRequest req) {

		if(bo_files!=null) {
			List<AttatchVO> attatchList = new ArrayList<>();
			for(MultipartFile file : bo_files) {
				if(file.isEmpty()) continue;
				attatchList.add(new AttatchVO(file));
			}
			if(attatchList.size() > 0)
				board.setAttatchList(attatchList);
				
		}
		
		
		Map<String, List<String>> errors = new LinkedHashMap<>(); // List 로 넣으면 하나의 
		req.setAttribute("errors", errors);
		
		// req의 scope 를 사용해서 class를 담아오고 그걸 class 로 받아서 동적으로 받아준다.
		Class<?> groupHint = (Class<?>) req.getAttribute("groupHint");
		if(groupHint == null) {
			groupHint = BoardInsertController.class;
		}
		boolean valid = new CommonValidator<BoardVO>().validate(board, errors, groupHint);
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
		
		req.setAttribute("message", message);
		
		return view;
	}


}