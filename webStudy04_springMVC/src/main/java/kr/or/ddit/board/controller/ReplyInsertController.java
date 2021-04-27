package kr.or.ddit.board.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.vo.BoardVO;

@Controller
public class ReplyInsertController {
	private String[] filteringTokens = new String[] {"말미잘","해삼"};
	
	
	@Inject
	private IBoardService service;

	//공지 게시글
	@RequestMapping("/board/noticeInsert.do")
	public String noticeForm(@ModelAttribute("board") BoardVO board) {
		board.setBo_type("NOTICE");
		return "board/boardForm";
		
	}
	// 댓글 

	//@requsetMapping 의 value 값과 method 대신 get/put/replyMapping 등을 
}