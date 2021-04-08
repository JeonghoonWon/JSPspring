package kr.or.ddit.designpattern.commandpattern;

public class ConcreteCommand2 implements Command {
	private Receiver2 recv2;
	
	
	// 생성자
	public ConcreteCommand2(Receiver2 recv2) {
		super();
		this.recv2 = recv2;
	}


	@Override
	public void execute() {
		recv2.specific2Operate2();
	
	}

}
