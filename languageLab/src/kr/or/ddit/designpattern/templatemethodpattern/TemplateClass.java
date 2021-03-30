package kr.or.ddit.designpattern.templatemethodpattern;

public abstract class TemplateClass {  
	// 한개이상의 추상메서드를 가지고 있기 때문에 public class도 추상메서드가 되어야한다.
	
	public final void template() {
		// final 붙여서 @override 불가능하게 한다.
		stepOne();
		stepTwo();
		stepThree();
		
		
	}
	// 외부에서는 template 만 호출 가능. 나머지는 private / projected
	private void stepOne() {
		System.out.println("1단계");
	}
	protected abstract void stepTwo();
	
	
	private void stepThree() {
		System.out.println("3단계");
		
	}
}
