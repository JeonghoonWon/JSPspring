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
			
			if(savedMember != null) {
				//비밀번호 인증을 해야한다
				
				String inputPass =member.getMem_pass();
				String savedPass = savedMember.getMem_pass();
				
				
				if(savedPass.equals(inputPass)) {	
					
					try {
						BeanUtils.copyProperties(member, savedMember);
					} catch (IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException(e);
											//e를 넣어주는 이유 원래있던 에러를 넣고 결과를 내뱉는다.
					
					}
					
					result= ServiceResult.OK;
					System.out.println("ok");
					
				}else{
					result = ServiceResult.INVALIDPASSWORD;
					
					
					
				}
				
				
			}else {
				
				result = ServiceResult.NOTEXIST;
				
			}
			return result;
			
			
		}



}
