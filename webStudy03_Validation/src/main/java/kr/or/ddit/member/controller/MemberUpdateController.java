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
import kr.or.ddit.mvc.annotation.resolvers.BadRequestException;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.filter.wrapper.MultipartFile;
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
	public String updateForm(HttpSession session, HttpServletRequest req){
		addCommandAttribute(req);
		MemberVO authMember =  (MemberVO) session.getAttribute("authMember");
		String authId = authMember.getMem_id();
 		MemberVO member = service.retrieveMember(authId);
 		req.setAttribute("member", member);
 		return "member/memberForm";
	}
	
		@RequestMapping(value ="/member/memberUpdate.do", method = RequestMethod.POST )
		public String doPost(@ModelAttribute("member") MemberVO member
				, @RequestPart(value="mem_image", required=false) MultipartFile mem_image
				, HttpSession session
				,HttpServletRequest req) throws IOException {
		addCommandAttribute(req);
		

		
//		1. ?????? ??????
//		MemberVO member = new MemberVO();
//		HttpSession session =  req.getSession();
		MemberVO authMember =(MemberVO) session.getAttribute("authMember");
		String authId = authMember.getMem_id();
		member.setMem_id(authId);
//		req.setAttribute("member", member);
//		member.setMem_id(req.getParameter("mem_id"));
//		try {
//			BeanUtils.populate(member,req.getParameterMap());
//		} catch (IllegalAccessException | InvocationTargetException e) {
//			throw new RuntimeException(e);				
//		}
		
		if(mem_image!=null && !mem_image.isEmpty()) {
			// mem_image ??? ?????? ??????, ???????????? ????????????.
			// ???????????? ?????? ????????? ??? ????????? ?????? ??????????????? ??????????????????.
			String mime = mem_image.getContentType();
			if(!mime.startsWith("image/")) {
				throw new BadRequestException("????????? ????????? ???????????? ?????? ??????.");
			}
			byte[] mem_img = mem_image.getBytes();
			member.setMem_img(mem_img);
		}
		
//		2. ??????
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
				message = "???????????? ??????";
				break;
			case OK:
				
				view = "redirect:/mypage.do";
				break;
			default:
				view = "member/memberForm";
				message = "???????????? ?????? ??? ?????? ???????????????.";
				break;
			}
		} else {
			// ?????? ??????
			view = "member/memberForm";
		}

		req.setAttribute("message", message);

		return view;
	}

}
