package kr.or.ddit.member.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class AuthenticateServiceImpl implements IAuthenticateService {
	private IMemberDAO dao = new MemberDAOImpl();
	
	@Override
	public ServiceResult authenticate(MemberVO member) {
		MemberVO savedMember = dao.selectMemberForAuth(member.getMem_id());
		ServiceResult result = null;
		if(savedMember!=null) {
			String inputPass = member.getMem_pass();
			String savedPass = savedMember.getMem_pass();
			if(savedPass.equals(inputPass)) {
				try {
					BeanUtils.copyProperties(member, savedMember);
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.INVALIDPASSWORD;
			}
		}else {
			result = ServiceResult.NOTEXIST;
		}
		return result;
	}

}










