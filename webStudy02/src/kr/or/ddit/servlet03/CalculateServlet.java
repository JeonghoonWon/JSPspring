package kr.or.ddit.servlet03;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.spi.oa.ObjectAdapter;

import kr.or.ddit.enumpkg.MimeType;
import kr.or.ddit.enumpkg.OperatorType;
import kr.or.ddit.servlet03.view.JsonView;
import kr.or.ddit.servlet03.view.XmlView;
import kr.or.ddit.vo.CalculateVO;

@WebServlet("/03/calculator")
public class CalculateServlet extends HttpServlet {
	// 전역변수를 사용하게된다면 불특정다수가 하나의 전역변수를 사용하게되고 싱글톤에서 하나만
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 인코딩
		req.setCharacterEncoding("UTF-8");
		// 요청 -> 분석(분석) ->
		int status = bindAndValidate(req); // 검증 수행은 숫자로 받을 수 있다. 성공: 200 / 실패 :400,500
		if (status != HttpServletResponse.SC_OK) {
			resp.sendError(status);
			return;
		}
		// 연산처리 ->
		// 데이터 전달시 한번에 전달하기 위해 VO 를 사용
		CalculateVO vo = (CalculateVO) req.getAttribute("vo");
		OperatorType operator = vo.getOperator();
		double result = operator.operate(vo.getLeft(), vo.getRight());
		String expr = operator.expression(vo);
		vo.setResult(result);
		vo.setExpression(expr);
		
		// 응답데이터 전송
		String accept = req.getHeader("accept");
		MimeType mimeType = MimeType.searchMimeType(accept);
		resp.setContentType(mimeType.getMime());

		StringBuffer respData = new StringBuffer();
		String view = null;

		switch (mimeType) {
		// 마샬링

		case JSON:
			new JsonView().mergeModelAndView(vo,resp);
			break;

		case XML:
			new XmlView().mergeModelAndView(vo,resp);
			break;

		case PLAIN:
			respData.append(expr);
			break;

		default:
			view = "/WEB-INF/views/calculatorView.jsp";

			break;

		}
		// model 2 구조가 된 것. 책임이 분리 
		if (view != null) {
			req.getRequestDispatcher(view).forward(req,resp);

		} else {

			try (// 직렬화
					PrintWriter out = resp.getWriter();) {
				out.print(respData);
			}

		}
	}

	private int bindAndValidate(HttpServletRequest req) {
		int status = HttpServletResponse.SC_OK; // 검증 통과
		String leftParam = req.getParameter("left");
		String rightParam = req.getParameter("right");
		String operatorParam = req.getParameter("operator");

		double left = -1;
		double right = -1;
		OperatorType operator = null; // 객체니까 null 로 표시

		if (leftParam == null || rightParam == null || operatorParam == null) {

			status = HttpServletResponse.SC_BAD_REQUEST;

		} else {
			// 값은 넘어왔으나 확인 할게 있음
			// 1. 숫자의 타입
			try {

				left = Double.parseDouble(leftParam);
				right = Double.parseDouble(rightParam);
				operator = OperatorType.valueOf(operatorParam);
			} catch (IllegalArgumentException e) { // 멀티 익셉션
				// NumberFormatException 를 IllegalArgumentException 가 포함하고 있기 때문에 멀티 익셉션 대신 하나만
				// 사용하면 된다.
				// 정규식을 쓰지않아고 예외를 발생을 잡을 수 있다.
				// 클라이언트의 실수 이므로 400번 에러 띄어준다.
				status = HttpServletResponse.SC_BAD_REQUEST;
			}

			// checked 와 unchecked 차이 : 컴파일 에러를 잡는지 아닌지

		}
		if (status == HttpServletResponse.SC_OK) {
			CalculateVO vo = new CalculateVO(left, right, operator);
			// vo를 vo 로 넘겨준다.
			req.setAttribute("vo", vo);
		}

		return status;
	}

}
