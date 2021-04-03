package kr.or.ddit.enumpkg;

public enum OSType {
// class 를 생성하고, class 를 enum 으로 변경 가능. enum 도 class 라는 말이다.
	MAC("MAC"), ANDROID("Android"), OTHER("기타등등");
	// 생성자
	OSType(String osName){
		this.osName = osName;
		
	}
	private String osName;
	public String getOsName(){
		return this.osName;
	}
	
	// over roding method
	// instance method 는 객체가 필요
	public static String getOsName(String agent) {
		agent = agent.toUpperCase();	
		OSType searched = OTHER;
		for(OSType tmp : values()){
			if(agent.contains(tmp.name())){
				searched = tmp;
				break;
			}
			
		}
		String name = searched.getOsName();
		return name;
	}
}
