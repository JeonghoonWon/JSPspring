package kr.or.ddit.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.AuthenticateServiceImpl;
import kr.or.ddit.member.service.IAuthenticateService;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/login/loginCheck02.do")
public class LoginCheckServlet02 extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private IAuthenticateService service = new AuthenticateServiceImpl();
	
	
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   System.out.println("1");
	   HttpSession session = req.getSession();
      if(session.isNew()) {
         resp.sendError(400);
         return;
      }
      //      요청 분석
      String mem_id = req.getParameter("mem_id");
      String mem_pass = req.getParameter("mem_pass");
      String saveId = req.getParameter("saveId");
      
      String view = null;
      System.out.println("2");
      boolean redirect = false;
      String message = null;
      
      boolean valid = validate(mem_id, mem_pass);
      System.out.println("3");
      System.out.println(valid);
      System.out.println(mem_id +"0000"+mem_pass);
	if (valid) {
//      인증(id==password)

			MemberVO member =new MemberVO(mem_id, mem_pass);
			
			ServiceResult result = service.authenticate(member);
			switch (result) {
			case OK:
					// 인증 성공시 index.jsp 로 이동(현재 요청 정보 삭제).
					redirect = true;
					view = "/index02.jsp";
					session.setAttribute("authId", member);
					Cookie idCookie = new Cookie("idCookie", mem_id);
					idCookie.setPath(req.getContextPath());
					int maxAge = 0;
					if ("saveId".equals(saveId)) {
						maxAge = 60 * 60 * 24 * 7;
					}
					idCookie.setMaxAge(maxAge);
					resp.addCookie(idCookie);

				break;

			case NOTEXIST:
				// 인증 실패시 loginForm.jsp 로 이동
				redirect = true;
				view = "/login/loginForm02.jsp";
				// 2) 인증 실패(아이디 상태 유지)
				message = "아이디 오류";

				break;
			case INVALIDPASSWORD:

				// 인증 실패시 loginForm.jsp 로 이동
				redirect = true;
				view = "/login/loginForm.jsp";
				// 2) 인증 실패(아이디 상태 유지)
				message = "비번 오류";
				session.setAttribute("failedId", mem_id);

				break;

			}
		} else {
//         1) 검증
			redirect = true;
			view = "/login/loginForm.jsp";
			message = "아이디나 비번 누락";
		}
      
//      view 로 이동
      if(redirect) {
         req.getSession().setAttribute("message", message);
         resp.sendRedirect(req.getContextPath() + view);
      }else {
         req.setAttribute("message", message);
         req.getRequestDispatcher(view).forward(req, resp);
      }
      
   }

	
   private boolean validate(String mem_id, String mem_pass) {
      boolean valid = true;
      valid = !(mem_id==null || mem_id.isEmpty() ||
            mem_pass==null || mem_pass.isEmpty());
      return valid;
   }
}










