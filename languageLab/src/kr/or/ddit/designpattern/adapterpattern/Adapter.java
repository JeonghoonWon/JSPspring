package kr.or.ddit.designpattern.adapterpattern;

public class Adapter implements Target {
	// Adaptee 사용해야하니까 여기에 넣어준다.
	private Adaptee adaptee;
		
	public Adapter(Adaptee adaptee) {
		super();
		this.adaptee = adaptee;
	}

	@Override
	public void request() {
		adaptee.specificRequest();
		// apdater 를 사용하면 adaptee 가 사용되도록 처리함.

	}

}
