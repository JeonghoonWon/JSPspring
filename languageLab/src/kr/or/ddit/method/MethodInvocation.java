package kr.or.ddit.method;

public class MethodInvocation {
	public static void main(String[] args) {
		String text1 = "original";
		StringBuffer text2 = new StringBuffer("original"); //heap 메모리에 객체 형식으로 들어가있는것
		
		callByValue(text1);
		System.out.println(text1);

		callByReference(text2);
		System.out.println(text2);
	}
	
	private static void callByValue(String data) {
		data = data + "append data";	// 값만 가지고 있음. 
					
	}
	private static void callByReference(StringBuffer data) {
		data.append("append data");		// callByReference 를 사용하면 전역변수 대신 값을 사용할 수 있다??
	}
	
}
