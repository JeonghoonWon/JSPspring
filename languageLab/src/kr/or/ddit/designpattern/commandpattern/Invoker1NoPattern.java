package kr.or.ddit.designpattern.commandpattern;

import kr.or.ddit.annotation.FirstAnnotation;
import kr.or.ddit.annotation.SecondAnnotation;

// 명령을 내리는 class 생성
@FirstAnnotation(value="invoker1",number=23)
public class Invoker1NoPattern {
	
	
	
	public Invoker1NoPattern() {
		super();
		
	}


	private Receiver1 recv1;
	
	
	public Invoker1NoPattern(Receiver1 recv1) {
		super();
		this.recv1 = recv1;
	}

	@SecondAnnotation(url="/test.do")
	public void order() {
		recv1.specificOperate1();
	}
}
