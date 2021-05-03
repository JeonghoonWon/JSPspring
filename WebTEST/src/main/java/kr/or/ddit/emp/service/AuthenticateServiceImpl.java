package kr.or.ddit.emp.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.emp.dao.IEmployeeDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.EmployeeVO;

@Service
public class AuthenticateServiceImpl implements IAuthenticateService {
	private static final Logger logger =
			LoggerFactory.getLogger(AuthenticateServiceImpl.class);
	@Inject
	private IEmployeeDAO dao;
	
	@Override
	public ServiceResult authenticate(EmployeeVO employee) {
		EmployeeVO savedEmp = dao.selectEmployeeForAuth(employee.getEmployee_id());
		ServiceResult result = null;
		if(savedEmp!=null) {
			//비밀번호 인증
			String inputPass = employee.getEmployee_pwd(); //입력한 비밀번호
			String savedPass = savedEmp.getEmployee_pwd(); //아이디를 통해 가져온 비밀번호
			
				if(inputPass==savedPass) {
					result = ServiceResult.OK;
				}else {
					result = ServiceResult.INVALIDPASSWORD;
				}
	
		}else {
			//아이디가 잘못되었을 경우 => 즉 아이디가 존재하지 않는다는 뜻
			result = ServiceResult.NOTEXIST;
		}
		return result;
	}

}










