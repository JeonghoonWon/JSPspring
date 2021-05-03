package kr.or.ddit.member.service;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.utils.CryptoUtil;
import kr.or.ddit.vo.MemberVO;

@Service
public class AuthenticateServiceImpl implements IAuthenticateService {
	private static final Logger logger =
			LoggerFactory.getLogger(AuthenticateServiceImpl.class);
	@Inject
	private IMemberDAO dao;
	
	@Override
	public ServiceResult authenticate(MemberVO member) {
		MemberVO savedMember = dao.selectMemberForAuth(member.getMem_id());
		ServiceResult result = null;
		if(savedMember!=null) {
			//비밀번호 인증
			String inputPass = member.getMem_pass(); //입력한 비밀번호
			try {
				String encodedPass = CryptoUtil.sha512(inputPass);
				String savedPass = savedMember.getMem_pass(); //아이디를 통해 가져온 비밀번호
				if(savedPass.equals(encodedPass)) {
					try {
						BeanUtils.copyProperties(member, savedMember); //reflection
					} catch (IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException(e);
						 //(e)를 넘음으로써 원래 있던 에러를 유지시켜서 tomcat에게 보낸다.
					}
					result = ServiceResult.OK;
				}else {
					result = ServiceResult.INVALIDPASSWORD;
				}
			}catch (Exception e) {
				logger.error("", e);
				result = ServiceResult.FAIL;
			}
		}else {
			//아이디가 잘못되었을 경우 => 즉 아이디가 존재하지 않는다는 뜻
			result = ServiceResult.NOTEXIST;
		}
		return result;
	}

}










