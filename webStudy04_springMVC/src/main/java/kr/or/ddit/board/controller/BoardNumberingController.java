package kr.or.ddit.board.controller;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardNumberingController {
	@Inject
	private IBoardService service;
	 
	@RequestMapping(value="{numeringType}.do"
					, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> recommend(
		@RequestParam(required=true) int what	
		, @PathVariable String numeringType //@PathVariable() 경로의 일부분을 변수화 시킨다. but, 이름 아규먼트 같으면 () 생략가능.
	) {		// 유연하긴하지만 사용자제하는게 좋다
		Object result = null;
		if("recommend".equals(numeringType)) {
			result = service.recommend(what);
		}else if("report".equals(numeringType)) {
			result = service.report(what); // 경로 변수가 어떤걸로 들어오는지에 따라 다르게 적용 가능.
		}
		Map<String, Object> resultMap = new HashMap<>();
//		boolean success = ServiceResult.OK.equals(result);
		boolean success = !(result instanceof ServiceResult);
		resultMap.put("success", success);
		
		if(success) {
			BoardVO board = service.retrieveBoard(BoardVO.builder().bo_no(what).build());
			if("recommend".equals(numeringType)) {
				resultMap.put("recommend", board.getBo_rec());
			}else if("report".equals(numeringType)) {
				resultMap.put("report", board.getBo_rep());
			}
		}else {
			resultMap.put("message", "서버오류로 추천 실패");
		}
		return resultMap;
	}
}