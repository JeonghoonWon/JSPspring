package kr.or.ddit.member.service;

import kr.or.ddit.vo.MemberVO;

public interface IMemberService {
	/**
	 * 회원 정보 상세 조회
	 * @param mem_id
	 * @return
	 */
	public MemberVO retrieveMember(String mem_id);
}
