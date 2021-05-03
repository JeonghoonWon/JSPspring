package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // java bean 규약 에 맞는 getter/setter/equals/tostring 불러올 수 있음.
@NoArgsConstructor // 기본 생성자 자동생성
@AllArgsConstructor // 가지고있는 생성자 모두 불러올때 사용
public class EmployeeVO implements Serializable {
	
	
	
	public EmployeeVO(String employee_id, String employee_pwd) {
		super();
		this.employee_id = employee_id;
		this.employee_pwd = employee_pwd;
	}
	// VO 생성
	private String employee_id;
	private String employee_pwd;
	private String employee_name;
	private String employee_phone;
	private String employee_email;
	private String employee_authority;
	private String employee_picture;
}
