package kr.or.ddit.enumpkg;

public enum BrowserType{//근본적으로 enum이 클래스이다.
	EDG("엣지"), CHROME("크롬"), TRIDENT("익스플로러"), OTHER("기타등등");
	//setter대신 값을 넣기 위해 사용하는 것은 생성자
	BrowserType(String browserName){
		this.browserName = browserName;
	}
	private String browserName;
	public String getBrowserName(){
		return this.browserName;
	}
	
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