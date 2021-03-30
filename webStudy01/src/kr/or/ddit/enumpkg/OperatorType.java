package kr.or.ddit.enumpkg;

import kr.or.ddit.vo.CalculateVO;

public enum OperatorType {
	// 생성자에서 연산자의 기호를 아래쪽 sign 에 넘기고 , 생성자의 두번째 realOperator 전달. 
	// 다시 그 객체를 realOperator 로 받고 
	PLUS('+', new RealOperator() {
		public double operate(double left, double right) {
			return left + right;
		}
	}), 
	MINUS('-', new RealOperator() {
		public double operate(double left, double right) {
			return left - right;
		}
	}),  
	MULTIPLY('*', new RealOperator() {
		public double operate(double left, double right) {
			return left * right;
		}
	}), 
	// lambda 식. 간결하다 .
	// JavaScript 형태로 쓰인다. 
	// 아무때나 쓸 수 있는건 아님.
	DIVIDE('/', (left, right) ->{
		return left / right;
	}),
	MODULAR('%', (left, right) ->{
		return left % right;
	});
	@FunctionalInterface
	private interface RealOperator{
		double operate(double left, double right);
		// 추상 메서드 ???????????????????????????????
		
	}
	
	// 생성자로 넘겨줘야 함

	// 생성자를 만드는 순간 기본 생성자는 없어지는 것 컴파일 에러가 뜬다 
	private OperatorType(char sign, RealOperator realOperator) {
		this.sign = sign;
		// realOperator 를 사용하게되면 enum 에 오류가 뜬다. 
		// enum 에 RealOperator 를 써줘야 함.
		this.realOperator = realOperator;
	}
	
	private RealOperator realOperator;
	private char sign; 	// 상수가 결정되어야 나타낼 수 있는 것
	public char getSign() {
		return sign;
	}
	
	public double operate(double left, double right) {
		// 연산자는 달라도 데이터의 사용값은 같다.
		
		return realOperator.operate(left, right);
		
	}
	private static final String EXPRPTRN = "%f %s %f = %f";
	// 결합력이 발생 
	public String expression(CalculateVO vo) {
		return String.format(EXPRPTRN,vo.getLeft(),sign,
				vo.getRight(),operate(vo.getLeft(),(vo.getRight())));
	}

}
