package kr.or.ddit.emp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.PagingVO;


public interface IEmployeeService {

		
		/**
		 * 회원 정보 상세 조회
		 * @param mem_id
		 * @return 존재하지 않으면, custom exception 발생
		 */
		public EmployeeVO retrieveEmployee(String employee_id);
		
			
		/**
		 * 신규 등록
		 * @param Employee
		 * @return PKDUPLICATED, OK, FAIL
		 */
		public ServiceResult createEmployee(EmployeeVO Employee);
			
		/**
		 * 자기 정보 수정
		 * @param Employee
		 * @return 존재하지 않으면, custom exception 발생, INVALIDPASSWORD, OK, FAIL
		 */
		public ServiceResult modifyEmployee(EmployeeVO Employee);
			
		/**
		 * 회원 탈퇴
		 * @param Employee
		 * @return 존재하지 않으면, custom exception 발생, INVALIDPASSWORD, OK, FAIL
		 */
		public ServiceResult removeEmployee(EmployeeVO Employee);
			
		/**
		 * 회원 목록 조회
		 * @param pagingVO TODO
		 * @return 조건에 맞는 회원이 없으면, size()=0
		 */
		public List<EmployeeVO> retrieveEmployeeList(PagingVO pagingVO);
		
		/**
		 * 페이징 처리를 위한 회원수 조회
		 * @param pagingVO TODO
		 * @return
		 */
		public int retrieveEmployeeCount(PagingVO<EmployeeVO> pagingVO);
		
		
	}
	

