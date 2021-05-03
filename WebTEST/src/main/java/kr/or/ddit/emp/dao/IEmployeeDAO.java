package kr.or.ddit.emp.dao;

import java.util.List;

import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.PagingVO;
@Repository
public interface IEmployeeDAO {

	/**
	 * PK 를 기준으로 한명의 회원 조회(인증용)
	 * 
	 * @param employee_id
	 * @return 존재하지 않는 경우, null 반환.
	 */
	public EmployeeVO selectEmployeeForAuth(String employee_id);

	/**
	 * 회원 정보 상세 조회
	 * 
	 * @param employee_id
	 * @return 존재하지 않는 경우, null 반환.
	 */
	public EmployeeVO selectEmployeeDetail(String employee_id);

	/**
	 * 신규 등록
	 * 
	 * @param Employee
	 * @return 등록된 row count > 0 성공
	 */
	public int insertEmployee(EmployeeVO Employee); // int : 몇개가 insert 됬는지

	/**
	 * 회원 정보 수정
	 * 
	 * @param Employee
	 * @return 수정된 row count > 0 성공
	 */
	public int updateEmployee(EmployeeVO Employee);

	/**
	 * 회원 정보 삭제(???)
	 * 
	 * @param employee_id
	 * @return 삭제된 row count > 0 성공
	 */
	public int deleteEmployee(String employee_id);

	/**
	 * 회원 목록 조회
	 * 
	 * @param pagingVO TODO
	 * @return 조건에 맞는 회원이 없다면, List 의 size()==0
	 */
	public List<EmployeeVO> selectEmployeeList(PagingVO pagingVO);

	public int selectTotalRecord(PagingVO<EmployeeVO> pagingVO);

	public void realDeleteEmployee(Map<String, Object> pMap); // 프로시져를 통해 진행되는데 프로시져는 returntype 이 없기때문에 void
}
