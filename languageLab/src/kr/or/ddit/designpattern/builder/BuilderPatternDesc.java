package kr.or.ddit.designpattern.builder;

/**
 * 
 * 객체 생성 방법
 * 1. 점층적 생성자 패턴
 * 2. 자바빈 패턴
 * 3. Builder 패턴
 * 
 */
public class BuilderPatternDesc {
	public static void main(String[] args) {
		TestVO vo = TestVO.builder().build();
		//case 1.
		TestVO vo1 = TestVO.builder()
							.prop1("prop1")
							.build();
		// case 2.
		TestVO vo2 = TestVO.builder()
						   .prop2("prop2")
						   .prop3("prop3")
						   .build();
		// case 3.
		TestVO vo3 = TestVO.builder()
						   .prop1("prop1")
						   .prop3("prop3")
						   .build();
		// case 4.
		TestVO vo4 = TestVO.builder()
						   .prop1("prop1")
						   .prop2("prop2")
						   .prop3("prop3")
						   .build();
		
// setter 가 없기 때문에 한 번 만든 객체는 더이상 바꿀 수 없다.
					
//		TestVO vo = new TestVO(); // 1. 생성자 패턴
////		case1. prop1 만 결정 
//		TestVO vo1 = new TestVO("prop1");
//		vo.setProp1("prop1");
////		case2. prop2, prop3 결정
//		TestVO vo2 = new TestVO("prop2","prop3");
//		vo2.setProp2("prop2");
//		vo2.setProp3("prop3");
////		case3. prop1, prop3 결정
//		TestVO vo3 = new TestVO("prop1", null, "prop3");
//		
////		case4. prop1, prop2, prop3 결정
//		TestVO vo4 = new TestVO();
//		vo4.setProp1("prop1");
//		vo4.setProp2("prop2");
//		vo4.setProp3("prop3");
	}
}
// 생성자 패턴의 단점
// 타입안정성이 없다. TestVO vo3 = new TestVO(null,"prop1", "prop3");
// 모든 케이스에 대한 객체 생성을 할 수 없다.
// 객체 생성 후 값을 넣을 때 너무 많은 줄을 소모. 같은 코드를 소모
//mutable(변경가능) / immutable(변경불가)