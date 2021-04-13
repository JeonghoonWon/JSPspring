package kr.or.ddit.designpattern.adapterpattern;

public class OtherConcrete implements Target {

	@Override
	public void request() {
		// 어떤 클래스에 의한 처리인지 알아보기
		System.out.printf("%s 에 의해 직접 처리된 명령\n",getClass().getName());

	}

}
