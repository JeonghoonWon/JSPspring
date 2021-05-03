package kr.or.ddit.servlet02;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * 
 * HttpServletResponse : client로 전송될 응답과 관련된 모든 정보를 가진 객체.
 *
 * 프로토콜에서 어떻게 응답 패키징을 하는가?
 * Http 응답 패키징 방식
 * 
 *  1. Response Line : 상태코드 (status code), protocol/지금은 1.1 로 잡혀있음
 *  	** status code : 요청 처리 결과를 의미하는 숫자
 *  	1)	1XX(Http 1.1 부터) : ing... / 현재 통로가 계속 유지되고 있다.
 *  	2)	2XX	: OK(success)
 *  	3)	3XX : 클라이언트의 추가 액션이 요구되는 상태 코드.
 *  		304(Not Modified)
 *  		302/307(Moved, location 헤더와 함께 사용) 
 *  	4)	4XX	: Fail client side Fail 	404 : 클라이언트가 식별자를 잘 못 입력했을때 (400번대 오류는 카테고리를 세분화해서 보여준다.)
 *  			404(Not Found), 400(Bad Request)/ 필수 파라미터가 안넘어왔을때, 숫자가 잘못된 형태로 파싱 되었을때, 숫자의 길이 범위가 넘어갔을때 등등 
 *  			405(Method Not Allowed) / 클라이언트가 post 방식으로 요청했으나 서버에서 post 방식을 사용하지 못하는 경우
 *  			415(Unsupported Media Type) / 현재 클라이언트가 요구하는 미디어는 지원하지 않는다.
 *  			401(UnAuthorized / 서버사이드에 보호해야하는 자원들이 있는경우 일정 수준이상의 보안등급인 자원에 접근할 경우),403(Forbidden / 접근제어) 
 *  5)	5XX : Fail server side Fail, Internal server Error(서버에 대한 문제를 구체적으로 나타내지않는다. 보안상의 이유)
 *  2. Response Header : meta data
 *  	Content-Type(mime), Content-Length(length)
 *  		response.setContentType(mime)
 *  		response.setHeader(name, value)
 *  		response.setDateHeader(name,value(long))
 *  		response.setIntHeader(name,value(int))
 *  
 *  	1) 캐시 제어
 *  	2) auto request(polling)
 *  	3) 흐름 제어(redirect/forward[include])
 *  
 *  3. Response Body(message body, content body)
 *  	response.getWriter(), response.getoutputStream() 으로 기록.
 *
 */
@WebServlet("/respDesc.do")
public class HttpServletResponseDesc extends HttpServlet{
	

}
