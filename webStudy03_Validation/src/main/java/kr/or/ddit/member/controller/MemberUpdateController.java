package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.validator.CommonValidator;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

//@WebServlet("/member/memberUpdate.do")
@Controller
public class MemberUpdateController{
	private IMemberService service = new MemberServiceImpl();
	
	private void addCommandAttribute(HttpServletRequest req){
		req.setAttribute("command","update");
	}
		
	
		@RequestMapping("/member/memberUpdate.do")
		public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addCommandAttribute(req);
		HttpSession session =  req.getSession();
		MemberVO authMember =(MemberVO) session.getAttribute("authMember");
		String authId = authMember.getMem_id();
		MemberVO member = service.retrieveMember(authId);
		//memberForm.jsp 재활용
		req.setAttribute("member", member);
		String view = "member/memberForm";
					
		//req.getRequestDispatcher(view).forward(req, resp);
		return view;
		}
	
		@RequestMapping(value ="/member/memberUpdate.do", method = RequestMethod.POST )
		public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addCommandAttribute(req);
		
		req.setCharacterEncoding("UTF-8");
		
//		1. 요청 접수
		MemberVO member = new MemberVO();
		HttpSession session =  req.getSession();
		MemberVO authMember =(MemberVO) session.getAttribute("authMember");
		String authId = authMember.getMem_id();
		member.setMem_id(authId);
		req.setAttribute("member", member);
//		member.setMem_id(req.getParameter("mem_id"));
		try {
			BeanUtils.populate(member,req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);				
		}
//		2. 검증
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = new CommonValidator<MemberVO>().validate(member, errors,UpdateGroup.class);
		
		String view = null;
		String message = null;
		if (valid) {
			ServiceResult result = service.modifyMember(member);

			switch (result) {
			case INVALIDPASSWORD:
				view = "member/memberForm";
				message = "비밀번호 오류";
				break;
			case OK:
				
				view = "redirect:/mypage.do";
				break;
			default:
				view = "member/memberForm";
				message = "서버오류 잠시 뒤 다시 시도하세요.";
				break;
			}
		} else {
			// 검증 불통
			view = "member/memberForm";
		}

		req.setAttribute("message", message);

		return view;
	}

}
