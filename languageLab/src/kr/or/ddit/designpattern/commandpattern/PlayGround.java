package kr.or.ddit.designpattern.commandpattern;

public class PlayGround {
	
	public static void main(String[] args) {
		Receiver1 recv1 = new Receiver1();
		Receiver2 recv2 = new Receiver2();
//========================================	
		// 패턴 적용 전
		Invoker1NoPattern iv1 = 
				new Invoker1NoPattern(recv1);
		iv1.order();
		
		Invoker2NoPattern iv2 = 
				new Invoker2NoPattern(recv2);
		iv2.order();
//========================================
		// command pattern 적용
		Command command1 = new ConcreteCommand(recv1);
		Command command2 = new ConcreteCommand2(recv2);
		Invoker front = new Invoker(command1, command2); //  invoker 에 가변 파라미터가 있기때문에 여러개 넘길 수 있음.
		front.order(1); // command2 실행
		front.order(0); // command1 실행
		front.order(7); //수행 할 수 없는 게 나오면 throw new RuntimeException("404");
		
		// Invoker 와 Receiver 의 결합도를 줄인것(ConcreteCommand를 통해서)
//		front.order();
//		front.setCommand(command2);
//		front.order();	
		}
}
