package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.BadRequestException;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.vo.MemberVO;


@Controller
public class MemberUpdateController{
	@Inject
	private IMemberService service;
	
	private void addCommandAttribute(Model model) {
		model.addAttribute("command", "update");
	}
		
	
	@RequestMapping("/member/memberUpdate.do")
	public String updateForm(HttpSession session, Model model){
		addCommandAttribute(model);
		MemberVO authMember =  (MemberVO) session.getAttribute("authMember");
		String authId = authMember.getMem_id();
 		MemberVO member = service.retrieveMember(authId);
 		model.addAttribute("member", member);
 		return "member/memberForm";
	}
	
		@RequestMapping(value ="/member/memberUpdate.do", method = RequestMethod.POST )
		public String doPost(
				@Validated(UpdateGroup.class) @ModelAttribute("member") MemberVO member
				, Errors errors
				, HttpSession session
				, Model model) {
		addCommandAttribute(model);
		

		
//		1. 요청 접수
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
		
//		if(mem_image!=null && !mem_image.isEmpty()) {
//			// mem_image 가 있는 경우, 바이트르 꺼내온다.
//			// 바이트로 변환 하기전 이 파일이 진짜 이미지인지 확인해야한다.
//			String mime = mem_image.getContentType();
//			if(!mime.startsWith("image/")) {
//				throw new BadRequestException("이미지 이외의 프로필은 처리 불가.");
//			}
//			byte[] mem_img = mem_image.getBytes();
//			member.setMem_img(mem_img);
//		}
		
//		2. 검증
//		Map<String, List<String>> errors = new LinkedHashMap<>();
//		req.setAttribute("errors", errors);
//		boolean valid = new CommonValidator<MemberVO>().validate(member, errors,UpdateGroup.class);
		
		String view = null;
		String message = null;
		if (!errors.hasErrors()) {
			ServiceResult result = service.modifyMember(member);
			switch (result) {
			case INVALIDPASSWORD:
				view = "member/memberForm";
				message = "비번 오류";
				break;
			case OK:
				view = "redirect:/mypage.do";
				break;
			default:
				message = "서버 오류, 잠시 뒤 다시 시도하세요.";
				view = "member/memberForm";
				break;
			}
		} else {
			// 검증 불통
			view = "member/memberForm";
		}

		model.addAttribute("message", message);

		return view;
	}
}
