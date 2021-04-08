package kr.or.ddit.designpattern.commandpattern;

public class Receiver2 {
	// 실제로 작업을 할 수있는걸 생성 한 것.
	
	// 규칙성 없이 만들고 있는 것 .POJO(Plain Old Java Object) 
	public void specific2Operate2() {
		System.out.println("리시버 2에서 명령을 수행했음.");
	}
}
