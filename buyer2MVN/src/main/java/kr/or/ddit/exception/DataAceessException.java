package kr.or.ddit.exception;

/**
 * Persistence Layer에서만 사용할 예외로 정의.
 *
 */
public class DataAceessException extends RuntimeException{
	//RuntimeException를 상속을 받았기때문에 Unchecked Exception이 된것이다.
	//부모의 생성자를 그대로 사용
	public DataAceessException() {
		super();
	}

	public DataAceessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataAceessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAceessException(String message) {
		super(message);
	}

	public DataAceessException(Throwable cause) {
		super(cause);
	}
}