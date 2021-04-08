package kr.or.ddit.designpattern.commandpattern;

public class Invoker {
	private Command[] commands;
	
	public Invoker(Command... commands) { // ... : 가변 파라미터
		super();
		this.commands = commands;
	}

	/*
	 * public void setCommand(Command command) { this.commands = commands; }
	 */
	
	public void order(int mapping) {
		// mapping된 command : url
		if(mapping>=commands.length) {
			throw new RuntimeException("404");
		}
		// 정상 수행
		commands[mapping].execute();
	}
	
}
