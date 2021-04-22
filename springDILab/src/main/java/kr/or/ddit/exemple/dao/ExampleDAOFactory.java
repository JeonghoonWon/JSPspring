package kr.or.ddit.exemple.dao;

public class ExampleDAOFactory { 

	// IExampleDAO : 리턴 값 .  ExampleDAOFactory 가 서비스와 다오의 결합력을 없애버린것. factory가 dao 를 결정해 주는 상황.
	// but, 여기를 통하기 위한 조건이 필요. 이것또한 서비스와 팩터리의 결합력이 발생하는 상황.
	public static IExampleDAO getExampleDAO() {
	//	return new ExampleDAO_MySql();
		return new ExampleDAO_Oracle();
	}
}
