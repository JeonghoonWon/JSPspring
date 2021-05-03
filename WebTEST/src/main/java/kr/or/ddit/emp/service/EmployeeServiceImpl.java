package kr.or.ddit.emp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.emp.UserNotFoundException;
import kr.or.ddit.emp.dao.IEmployeeDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.PagingVO;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	
	@Inject
	private IEmployeeDAO dao;
	
	@Inject
	private IAuthenticateService authService;
	
	@Override
	public EmployeeVO retrieveEmployee(String employee_id) {
		EmployeeVO savedEmp = dao.selectEmployeeDetail(employee_id);
		if(savedEmp==null) {
			// custom exception 발생
			throw new UserNotFoundException("아이디에 해당하는 회원이 존재하지 않음.");	// unchecked execption
		}
		return savedEmp;
	}

	@Override
	public ServiceResult createEmployee(EmployeeVO Employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult modifyEmployee(EmployeeVO Employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult removeEmployee(EmployeeVO Employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeVO> retrieveEmployeeList(PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int retrieveEmployeeCount(PagingVO<EmployeeVO> pagingVO) {
		// TODO Auto-generated method stub
		return 0;
	}

}
