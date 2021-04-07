package kr.or.ddit.designpattern.builder;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// java Bean 규약에 따라 VO 를 생성함.
@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class TestVO implements Serializable {

	private String prop1;
	private String prop2;
	private String prop3;
	
	// 1. 생성자 필요 , 2. getter만 필요
//	
//	
//	//public 에서 private 로 변경. 나만 쓸 수 있음.
//	private TestVO(String prop1, String prop2, String prop3) {
//		super();
//		this.prop1 = prop1;
//		this.prop2 = prop2;
//		this.prop3 = prop3;
//	}
//	public String getProp1() {
//		return prop1;
//	}
//	public String getProp2() {
//		return prop2;
//	}
//	public String getProp3() {
//		return prop3;
//	}
//	// 객체생성을 전담할 수 있는 builder 필요 
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((prop1 == null) ? 0 : prop1.hashCode());
//		result = prime * result + ((prop2 == null) ? 0 : prop2.hashCode());
//		result = prime * result + ((prop3 == null) ? 0 : prop3.hashCode());
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		TestVO other = (TestVO) obj;
//		if (prop1 == null) {
//			if (other.prop1 != null)
//				return false;
//		} else if (!prop1.equals(other.prop1))
//			return false;
//		if (prop2 == null) {
//			if (other.prop2 != null)
//				return false;
//		} else if (!prop2.equals(other.prop2))
//			return false;
//		if (prop3 == null) {
//			if (other.prop3 != null)
//				return false;
//		} else if (!prop3.equals(other.prop3))
//			return false;
//		return true;
//	}
//	@Override
//	public String toString() {
//		return "TestVO [prop1=" + prop1 + ", prop2=" + prop2 + ", prop3=" + prop3 + "]";
//	}
//	// return type 을 TestVOBuilder 로 잡는다.
//	public static TestVOBuilder builder() {
//		return new TestVOBuilder();
//	}
//	public static class TestVOBuilder{
//		
//		private TestVOBuilder() {}
//		private String prop1;
//		private String prop2;
//		private String prop3;
//		
//		public TestVOBuilder prop1(String prop1) {
//			this.prop1 = prop1;
//			return this;
//		}
//		public TestVOBuilder prop2(String prop2) {
//			this.prop2 = prop2;
//			return this;
//		}
//		public TestVOBuilder prop3(String prop3) {
//			this.prop3 = prop3;
//			return this;
//		}
//		// builder 또한 체인구조를 가지고있음.
//		
//		public TestVO build() {
//			return new TestVO(prop1, prop2, prop3);
//		}
//	}
	
}
