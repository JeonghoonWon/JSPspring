package kr.or.ddit.enumpkg;

public enum BrowserType {
// class 를 생성하고, class 를 enum 으로 변경 가능. enum 도 class 라는 말이다.
	EDG("엣지"), CHROME("크롬"), TRIDENT("익스플로러"), OTHER("기타등등");
	// 생성자
	BrowserType(String browserName){
		this.browserName = browserName;
		
	}
	private String browserName;
	public String getBrowserName(){
		return this.browserName;
	}
	
	// over roding method
	// instance method 는 객체가 필요
	public static String getBrowserName(String agent) {
		agent = agent.toUpperCase();	
		BrowserType searched = OTHER;
		for(BrowserType tmp : values()){
			if(agent.contains(tmp.name())){
				searched = tmp;
				break;
			}
			
		}
		String name = searched.getBrowserName();
		return name;
	}
}
