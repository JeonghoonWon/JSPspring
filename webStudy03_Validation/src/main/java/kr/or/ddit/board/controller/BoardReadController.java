package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;


@Controller
public class BoardReadController {
	
	private IBoardService service = BoardServiceImpl.getInstance();
	
	@RequestMapping("/board/boardView.do")
	public String viewForAjax(
				@RequestParam("what") int bo_no
				,HttpServletRequest req
				,HttpServletResponse resp
		) throws IOException {
		String accept = req.getHeader("Accept");
		
		BoardVO search = new BoardVO();
		search.setBo_no(bo_no);
		BoardVO board = service.retrieveBoard(search);
		
		String view = null;
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