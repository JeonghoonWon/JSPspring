package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;


@Controller
public class BoardReadController {
	
	public static final String BOARDAUTH = "board.authenticated";
	private IBoardService service = BoardServiceImpl.getInstance();
	
	@RequestMapping(value="/board/authenticate.do", method=RequestMethod.POST)
	public String boardAuth(
			@RequestParam("bo_no") int bo_no
			, @RequestParam("bo_pass") String bo_pass 
			, HttpSession session
			// 처음부터 BoardVO search 를 사용하게되면 인증처리가 어렵기 때문에 따로따로 받아서 검증 처리 함. 꼭 이게 답은 아니다.
			) {
		
		BoardVO search = new BoardVO();
		search.setBo_no(bo_no);
		search.setBo_pass(bo_pass);		
		String view = null;
		if(service.boardAuthenticate(search)) {
			session.setAttribute(BOARDAUTH, search); // session scope 에 BOARDAUTH 가 있는지없는지에따라 확인가능
			// 더이상 bo_no, bo_pass를 더이상 사용할 필요가 없다. 그리고 인증은 redirect 사용한다.
			view = "redirect:/board/boardView.do?what="+bo_no;
		}else {
			session.setAttribute("message", "비밀번호 오류");
			view = "redirect:/board/boardList.do";
		}
		
		return view;
	}
	
	@RequestMapping("/board/boardView.do")
	public String viewForAjax(
				@RequestParam("what") int bo_no
				,HttpServletRequest req
				,HttpServletResponse resp
				, HttpSession session
		) throws IOException {
		String accept = req.getHeader("Accept");
		
		BoardVO search = new BoardVO();
		req.setAttribute("search", search);
		search.setBo_no(bo_no);
		BoardVO board = service.retrieveBoard(search);
	
		boolean valid = true;
		if("Y".equals(board.getBo_sec())) {
			// 여기까지 오는 과정에서 인증을 거쳤는지 확인
			// 인증을 거쳤다면 그 인증 결과가 어디에 저장되어있는지 확인.
			BoardVO authenticated = 
					(BoardVO) session.getAttribute(BOARDAUTH);
			
			if(authenticated==null || authenticated.getBo_no() != bo_no) {
				valid = false;
			}
						
		}
		String view = null;
		if(valid) {
			// 비밀글이 아니고 인증을 거친 경우 이 if 문을 돈다.
			if(accept.contains("plain")) {
				resp.setContentType("text/plain;charset=UTF-8");
				try(
						PrintWriter out = resp.getWriter();	
						){
					out.println(board.getBo_content());
				}
			}else {
				req.setAttribute("board", board);
				view = "board/boardView";
			}
			
		}else {
			//비밀글인데 인증을 거치지 않은 경우
			view = "board/passwordForm";
			
		}// if(valid) end
		// session에 남아있는 인증을 지워줘야한다.
		session.removeAttribute(BOARDAUTH);
		
		return view;
	}

		
	 @RequestMapping("/board/boardList.do")
	   public String list(
	      @RequestParam(value="page", required=false, defaultValue="1") int currentPage,
	      @RequestParam(value="searchType", required=false) String searchType,
	      @RequestParam(value="searchWord", required=false) String searchWord,
	      @RequestParam(value="minDate", required=false) String minDate,
	      @RequestParam(value="maxDate", required=false) String maxDate,
	      HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	      
	      PagingVO<BoardVO> pagingVO = new PagingVO<>();
	      pagingVO.setCurrentPage(currentPage);
	      
	      //검색조건
	      Map<String, Object> searchMap = new HashMap<>();
	      searchMap.put("searchType", searchType);
	      searchMap.put("searchWord", searchWord);
	      searchMap.put("minDate", minDate);
	      searchMap.put("maxDate", maxDate);
	      pagingVO.setSearchMap(searchMap);
	      
	      int totalRecord = service.retrieveBoardCount(pagingVO);
	      pagingVO.setTotalRecord(totalRecord);
	      
	      List<BoardVO> boardList = service.retrieveBoardList(pagingVO);
	      pagingVO.setDataList(boardList);
	            
	      req.setAttribute("pagingVO", pagingVO);
	      
	      return "board/boardList";
	   }
	}