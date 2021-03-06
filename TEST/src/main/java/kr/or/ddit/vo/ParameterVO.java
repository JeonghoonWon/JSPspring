package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 자바빈 규약(VO, DTO, Model, Bean)
 * 1. 값을 가질 수 있는 속성(property) 존재
 * 2. property 캡슐화
 * 3. property에 접근할 수 있는 interface 제공
 * 	  Getter/Setter : get[set]property_name(carmel 표기법)
 * 4. 객체 상태 비교 메소드 제공
 *    ==(주소), equals(상태 비교)의 차이 -> Object의 equals는 주소를 비교한다.
 * 5. 객체 상태 확인 메서드 제공 : toString
 * 6. 직렬화 지원
 * 
 */
public class ParameterVO implements Serializable{  //implements Serializable : 시리얼라이즈를 할 수 있는지없는지 보는것.
	private String[] param1;
	private String[] param2;
	private String[] param3;
	private int[] param4;
	private String param5;
	
	public String[] getParam1() {
		return param1;
	}
	public void setParam1(String[] param1) {
		this.param1 = param1;
	}
	public String[] getParam2() {
		return param2;
	}
	public void setParam2(String[] param2) {
		this.param2 = param2;
	}
	public String[] getParam3() {
		return param3;
	}
	public void setParam3(String[] param3) {
		this.param3 = param3;
	}
	public int[] getParam4() {
		return param4;
	}
	public void setParam4(int[] param4) {
		this.param4 = param4;
	}
	public String getParam5() {
		return param5;
	}
	public void setParam5(String param5) {
		this.param5 = param5;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(param1);
		result = prime * result + Arrays.hashCode(param2);
		result = prime * result + Arrays.hashCode(param3);
		result = prime * result + Arrays.hashCode(param4);
		result = prime * result + ((param5 == null) ? 0 : param5.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParameterVO other = (ParameterVO) obj;
		if (!Arrays.equals(param1, other.param1))
			return false;
		if (!Arrays.equals(param2, other.param2))
			return false;
		if (!Arrays.equals(param3, other.param3))
			return false;
		if (!Arrays.equals(param4, other.param4))
			return false;
		if (param5 == null) {
			if (other.param5 != null)
				return false;
		} else if (!param5.equals(other.param5))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ParameterVO [param1=" + Arrays.toString(param1) + ", param2=" + Arrays.toString(param2) + ", param3="
				+ Arrays.toString(param3) + ", param4=" + Arrays.toString(param4) + ", param5=" + param5 + "]";
	}
	
	
	
}
