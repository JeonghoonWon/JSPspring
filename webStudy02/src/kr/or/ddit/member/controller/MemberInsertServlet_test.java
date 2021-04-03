package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService_test;
import kr.or.ddit.member.service.MemberServiceImpl_test;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberInsert_test.do")
public class MemberInsertServlet_test extends HttpServlet {
	private IMemberService_test service = new MemberServiceImpl_test();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String view = "/WEB-INF/views/member/memberForm_test.jsp";
		boolean redirect = false;
		// logic
		if (redirect) {
			resp.sendRedirect(req.getContextPath() + view);
		}else {
			req.getRequestDispatcher(view).forward(req, resp);
		}

	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		// 1. 요청 접수
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		try {
			BeanUtils.populate(member,req.getParameterMap());
// org.apache.commons.beanutils.BeanUtils 은 Map을 bean객체로 바꾸어주는 클래스
//		bean 객체에 memberVO 가 들어가있으니까 VO 객체로 바꾸는건가?
//		jsp 에서 넘어온 값을 간단하게 java bean 객체에 맞추어 값을 넣어줌.
//		request.getParameterMap() 에서 사용자 요청을 Map 형식으로 만들고, 
// 		BeanUtils.populate(bean,  request.getParameterMap()) 메소드에서
//		UserBean의 setName() 메소드에 자동으로 name값을 맵핑한다.

		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);				
		}
		// 2. 검증
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(member, errors);
		
		String view = null;
		String message = null;
		if(valid) {
			ServiceResult result = service.createMember(member);
			
			switch (result) {
			case PKDUPLICATED:
				view = "/WEB-INF/views/member/memberForm_test.jsp";
				message = "아이디 중복";
				break;
			case OK:
				view = "redirect:/login/loginForm.jsp";
			default:
				view = "/WEB-INF/views/member/memberForm_test.jsp";
				message = "서버오류 잠시 뒤 다시 시도하세요.";
				break;
				
			} // switch(result) end
		}else {
			// 검증 불통
			view = "/WEB-INF/views/member/memberForm_test.jsp";
		}
		
		req.setAttribute("message", message);
		
		boolean redirect = view.startsWith("redirect:");
		if(redirect) {
			view = view.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + view);
		}else {
			req.getRequestDispatcher(view).forward(req,resp);
		}
	}

	private boolean validate(MemberVO member, Map<String, String> errors) {
		boolean valid = true;
		if (member.getMem_id() == null || member.getMem_id().isEmpty()) {
			valid = false;
			errors.put("mem_id", "회원아이디 누락");
		}
		if (member.getMem_pass() == null || member.getMem_pass().isEmpty()) {
			valid = false;
			errors.put("mem_pass", "회원비밀번호 누락");
		}
		if (member.getMem_name() == null || member.getMem_name().isEmpty()) {
			valid = false;
			errors.put("mem_name", "회원이름 누락");
		}
		if (member.getMem_zip() == null || member.getMem_zip().isEmpty()) {
			valid = false;
			errors.put("mem_zip", "우편번호 누락");
		}
		if (member.getMem_add1() == null || member.getMem_add1().isEmpty()) {
			valid = false;
			errors.put("mem_add1", "회원집주소1 누락");
		}
		if (member.getMem_add2() == null || member.getMem_add2().isEmpty()) {
			valid = false;
			errors.put("mem_add2", "회원집주소2 누락");
		}
		if (member.getMem_mail() == null || member.getMem_mail().isEmpty()) {
			valid = false;
			errors.put("mem_mail", "회원메일 누락");
		}

		return valid;
	}
	
}
