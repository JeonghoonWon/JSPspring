package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.UserNotFoundException;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {
	private IMemberDAO dao = new MemberDAOImpl();
	private IAuthenticateService authService = new AuthenticateServiceImpl();
	
	
	
	@Override
	public MemberVO retrieveMember(String mem_id){
		MemberVO savedMember = dao.selectMemberDetail(mem_id);
		if(savedMember==null) {
			// custom exception 발생
			throw new UserNotFoundException("아이디에 해당하는 회원이 존재하지 않음.");	// unchecked execption
		}
		return savedMember;
	}

	@Override
	public ServiceResult createMember(MemberVO member) {
		ServiceResult result = null;
		if(dao.selectMemberDetail(member.getMem_id())==null) {
			int rowcnt = dao.insertMember(member);
			if(rowcnt>0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAIL;
			}
		}else {
			result = ServiceResult.PKDUPLICATED;
		}
		return result;
	}

	@Override
	public ServiceResult modifyMember(MemberVO member) {
		retrieveMember(member.getMem_id());	// 존재하는지 확인하기 위해 
		ServiceResult result =
				authService.authenticate(new MemberVO(member.getMem_id(),member.getMem_pass()));
		
		if(ServiceResult.OK.equals(result)) {
			int rowcnt = dao.updateMember(member);
			if(rowcnt > 0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAIL;
			}
		}		
		return result;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		retrieveMember(member.getMem_id());	// 존재하는지 확인하기 위해 
		ServiceResult result =
				authService.authenticate(new MemberVO(member.getMem_id(),member.getMem_pass()));
		if(ServiceResult.OK.equals(result)) {
			int rowcnt = dao.deleteMember(member.getMem_id());
			if(rowcnt > 0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAIL;
			}
		}		
		return result;
	}

	@Override
	public List<MemberVO> retrieveMemberList() {
		// TODO Auto-generated method stub
		return null;
	}

}
















