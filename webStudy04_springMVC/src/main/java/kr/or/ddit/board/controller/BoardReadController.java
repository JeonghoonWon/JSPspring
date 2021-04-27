package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.exception.BadRequestException;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;


@Controller
public class BoardReadController {
	
	public static final String BOARDAUTH = "board.authenticated";
	// injection 을 사용하려면 처음부터 끝까지 Inject 가 되어야 한다.
	@Inject
	private IBoardService service;
	@Inject	
	private WebApplicationContext container; //web 에서 사용하기 때문에 WebApplicationContext / 주입을 위해 inject annotation 사용해준다. 하위 컨테이너의 레퍼런스가 들어가있음.
	private ServletContext application;
	
	@PostConstruct
	public void init() {
		application = container.getServletContext();
		 //@Inject이 된 이후에 발생하게된다!
	}
	
	@RequestMapping(value="/board/authenticate.do", method=RequestMethod.POST)
	public String boardAuth(
			@RequestParam("bo_no") int bo_no
			, @RequestParam("bo_pass") String bo_pass 
			, HttpSession session
			// 처음부터 BoardVO search 를 사용하게되면 인증처리가 어렵기 때문에 따로따로 받아서 검증 처리 함.but, 꼭 이게 답은 아니다.
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
	@RequestMapping("/board/boardView.do")// 일반은 html 로 보내는 것. // 하나의 게시글 상세정보
	public String view(@RequestParam("what") int bo_no
					, @ModelAttribute("search") BoardVO search
					, Model model
					, HttpSession session) { // 넘어오는 파라미터와 argument가 똑같을때 생략가능. 생략하면 자동으로 requiered true 가 됨.
		// 비밀글인지 아닌지 
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
				// 동기요청에대한 헨들러로 		
		}
		String view = null;
		if(valid) {
			model.addAttribute("board", board);
			view = "board/boardView";
		}else {
			//비밀글인데 인증을 거치지 않은 경우
			view = "board/passwordForm";
		}// if(valid) end
		return view;

			}
	
	@RequestMapping(value ="/board/boardView.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String preView(  // 비동기로 왔을때 json 으로 보내는것.
				@RequestParam("what") int bo_no
				, @ModelAttribute("search") BoardVO search
				, HttpSession session
		) throws IOException {

		search.setBo_no(bo_no);
		BoardVO board = service.retrieveBoard(search);
	
		boolean valid = true;
		if("Y".equals(board.getBo_sec())) { // 비정상적인 상황
			// 여기까지 오는 과정에서 인증을 거쳤는지 확인
			// 인증을 거쳤다면 그 인증 결과가 어디에 저장되어있는지 확인.
			BoardVO authenticated = 
					(BoardVO) session.getAttribute(BOARDAUTH);
			
			if(authenticated==null || authenticated.getBo_no() != bo_no) {
				valid = false;
			}
				// 동기요청에대한 헨들러로 		
		}
		
		if(valid) { // 정상적인 상황
			// session에 남아있는 인증을 지워줘야한다.
			session.removeAttribute(BOARDAUTH);
			// 직렬화를 위한 내용이기떄문에 논리적인 뷰 네임화 차별을 주기위해 responcebody annotation
			return board.getBo_content();
		}else {
			// 비 정상적인 상황
			throw new BadRequestException("비밀글은 미리보기 불가.");
		}
	}
	@RequestMapping(value="/board/noticeList.do", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody // 비동기로  json 처리하는경우 적용.
	public PagingVO<BoardVO> noticeListForAjax(
				@RequestParam(value="searchType", required=false) String searchType
				, @RequestParam(value="searchWord", required=false) String searchWord
				, @RequestParam(value="page", required=false, defaultValue="1") int currentPage
			    , @RequestParam(value="minDate", required=false) String minDate
			    , @RequestParam(value="maxDate", required=false) String maxDate
			    , HttpServletRequest req) {
		searchType = "type";
		searchWord = "NOTICE";
		return listForAjax(currentPage, searchType, searchWord, minDate, maxDate);
	}
	
	@RequestMapping("/board/noticeList.do") // 일반적인 요청처리
	public String noticeList(
				@RequestParam(value="searchType", required=false) String searchType
				, @RequestParam(value="searchWord", required=false) String searchWord
				, @RequestParam(value="page", required=false, defaultValue="1") int currentPage
			    , @RequestParam(value="minDate", required=false) String minDate
			    , @RequestParam(value="maxDate", required=false) String maxDate
			    , Model model)  {
		searchType = "type";
		searchWord = "NOTICE";
		return listForHTML(currentPage, searchType, searchWord, minDate, maxDate,model);
	}
	
	 @RequestMapping(value={"/board/boardList.do"})  //value를 배열로 넣는다. value[]
	   public String listForHTML(
			      @RequestParam(value="page", required=false, defaultValue="1") int currentPage,
			      @RequestParam(value="searchType", required=false) String searchType,
			      @RequestParam(value="searchWord", required=false) String searchWord,
			      @RequestParam(value="minDate", required=false) String minDate,
			      @RequestParam(value="maxDate", required=false) String maxDate,
			      Model model) {
		 	
		 PagingVO<BoardVO> pagingVO = listForAjax(currentPage, searchType, searchWord, minDate, maxDate);
		      
		 	model.addAttribute("pagingVO", pagingVO);
		      
		 	return "board/boardList";
		 
	 }

	 // 동기와 비동기에 대한 처리가 필요하다.
	 // 클라이언트가 html/ json중 어떤걸 요청하는지 파악. accept header 를 통해 확인.
	 @RequestMapping(value="/board/boardList.do", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 @ResponseBody
	   public PagingVO<BoardVO> listForAjax(
	      @RequestParam(value="page", required=false, defaultValue="1") int currentPage,
	      @RequestParam(value="searchType", required=false) String searchType,
	      @RequestParam(value="searchWord", required=false) String searchWord,
	      @RequestParam(value="minDate", required=false) String minDate,
	      @RequestParam(value="maxDate", required=false) String maxDate) {
	      
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
	      
	      for(BoardVO tmp : boardList) {
	    	  String thumbnail = application.getContextPath() + "/images/Coca-Cola-logo.png";
	    	  String source = tmp.getBo_content();
	    	  
	    	  if(source == null) {
	    		  tmp.setThumbnail(thumbnail);
	    		  continue;
	    	  }
	    	  
	    	  Document dom = Jsoup.parse(source);  // dom 트리 구조 생성
	    	  Elements imgs = dom.getElementsByTag("img");
	    	  if(!imgs.isEmpty()) {
	    		  // 썸네일로 보여줄 사진이 없는 경우 하나를 생성해줘야함.
	    		  // src 에 들어가있는 주소값을 뽑아와야한다.
	    		  Element img = imgs.get(0); // 첫번째 사진을 썸네일로
	    		  thumbnail = img.attr("src");
	    	  }
	    	  tmp.setThumbnail(thumbnail);
	            
	      }
	   
	      	return pagingVO;  // 그대로 내보낼 수 없기 때문에 마샬링 
	     
	 }
	 
}