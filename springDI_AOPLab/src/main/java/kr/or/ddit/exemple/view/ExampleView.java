package kr.or.ddit.exemple.view;

import org.springframework.stereotype.Component;

import kr.or.ddit.exemple.dao.ExampleDAO_Oracle;
import kr.or.ddit.exemple.dao.IExampleDAO;
import kr.or.ddit.exemple.service.ExampleServiceImpl;
import kr.or.ddit.exemple.service.IExampleService;


public class ExampleView {
	
	
	public static void main(String[] args) {
		IExampleDAO dao = new ExampleDAO_Oracle();
		IExampleService service = new ExampleServiceImpl(dao);
		String info = service.readData("a001");
		System.out.println(info);
	}
}
