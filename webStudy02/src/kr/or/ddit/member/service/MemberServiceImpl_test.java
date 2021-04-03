package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.UserNotFoundException;
import kr.or.ddit.member.dao.IMemberDAO_test;
import kr.or.ddit.member.dao.MemberDAOImpl_test;
import kr.or.ddit.vo.MemberVO;

public class MemberServiceImpl_test implements IMemberService_test {
	private IMemberDAO_test dao = new MemberDAOImpl_test();
	
	@Override
	public MemberVO retrieveMember(String mem_id) {
		MemberVO savedMember = dao.selectMemberDetail(mem_id);	
		if(savedMember == null) {
			// custom exception 발생
			throw new UserNotFoundException("아이디에 해당하는 회원이 존재하지 않음.");
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
			}// if(rowcnt>0) end
		}else {
			result = ServiceResult.PKDUPLICATED;
		}
		return result;
	}
	@Override
	public ServiceResult modifyMember(MemberVO member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberVO> retrieveMemberList() {
		// TODO Auto-generated method stub
		return null;
	}

}
