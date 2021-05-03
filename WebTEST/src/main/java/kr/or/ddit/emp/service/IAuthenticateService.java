package kr.or.ddit.emp.service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.EmployeeVO;

public interface IAuthenticateService {
	public ServiceResult authenticate(EmployeeVO employee);
}
