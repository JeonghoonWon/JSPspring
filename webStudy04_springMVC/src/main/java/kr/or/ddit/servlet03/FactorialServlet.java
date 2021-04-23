package kr.or.ddit.servlet03;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.enumpkg.MimeType;
import kr.or.ddit.servlet03.view.JsonView;

@WebServlet("/03/factorial")
public class FactorialServlet extends HttpServlet {
	// UI 를 클라이언트에게 제공해야 함. DOGET

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// web-inf 로 이동하기 때문에 forword 방식으로 이동
		String view = "/WEB-INF/views/factorialForm.jsp";
		req.getRequestDispatcher(view).forward(req, resp);

	}
	// jsp 까지 연결

	// 포스트로 넘어온 값 받기 위해서 dopost 있어야 함
	// RESTful URI : 무엇을에 해당하는게 URI 어떤 작업을 하겠다는게 method 원래 URI method 의 개념을 재대로
	// 사용하자는게 RESTful
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 값 받기
		String single = req.getParameter("single");
	
		// req.getHeader("accept"); : 클라이언트가 받아 들일 수 있는 MIME 형식에 관련된 정보
		String accept = req.getHeader("accept");
		
		int status = 200;
		String message = null;
		String view = null;
		
		// 값 검증
		if (single == null || !single.matches("[0-9]{1,2}")) {
			// 클라이언트의 실수 400
			status = 400;
			message = "필수 파라미터 누락";
		} else {

			long op = Long.parseLong(single);

			try {
				// 팩토리얼 연산
				long result = factorial(op);
				MimeType mime = MimeType.searchMimeType(accept);
				
				Map<String,Object> target = new HashMap<>();
				target.put("op",op);
				target.put("result",result);
				target.put("expression",String.format("%d!=%d",op,result));
				
				if(MimeType.JSON.equals(mime)) {
					resp.setHeader("Content-Type", "application.json;charset=UTF-8");
					
					new JsonView().mergeModelAndView(target, resp);
					
				}else {
					req.setAttribute("target", target);
					view = "/WEB-INF/views/factorialForm.jsp"; // 다시 사용하고싶은 JSP 로 
				}
				
			} catch (IllegalArgumentException e) {
				status = HttpServletResponse.SC_BAD_REQUEST;
				message = "음수는 연산 불가";
				
			}
		}
		if(status==HttpServletResponse.SC_OK) {
			if(view !=null) {
				req.getRequestDispatcher(view).forward(req, resp);
			}
		}else {
			resp.sendError(status,message);
		}
	}

	// 재귀 호출(recursive call)

	private long factorial(long op) {
		if (op <= 0) {
			// 예외 발생(throw) : unchecked exception thows IllegalArgumentException을 써두지않아도
			// 적용됨.
			throw new IllegalArgumentException("양수만 대상으로 연산 수행 가능.");
		}
		if (op == 1) {
			return 1;
		} else {
			return op * factorial(op - 1);
		}
	}

}
// 검증
// 연산 수행
// 응답 데이터 보냄
// operator 는 carculator 와 다르다.
