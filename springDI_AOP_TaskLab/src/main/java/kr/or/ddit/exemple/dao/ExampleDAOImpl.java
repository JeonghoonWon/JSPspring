package kr.or.ddit.exemple.dao;

import org.springframework.stereotype.Repository;

@Repository
public class ExampleDAOImpl implements IExampleDAO {

	@Override
	public String selectData(String pk) {
		return "์กฐํ๋ Example data";
	}

}
