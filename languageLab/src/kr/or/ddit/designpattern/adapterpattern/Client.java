package kr.or.ddit.designpattern.adapterpattern;

public class Client {
	private Target target;
	
	//생성자
	public Client(Target target) {
		super();
		this.target = target;
	}
	public void execute() {
		target.request();
	}
	
	public static void main(String[] args) {
//		Target target = new OtherConcrete();
		// Adapter 는 랩퍼 .알맹이(adptee)가 필요하다.
		Adaptee adaptee = new Adaptee();
		Target target = new Adapter(adaptee);
		Client client = new Client(target);
		client.execute();
	}
	
	
}
