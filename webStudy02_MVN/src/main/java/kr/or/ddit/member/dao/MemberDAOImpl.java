package kr.or.ddit.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.MemberVO;

public class MemberDAOImpl implements IMemberDAO {
	
	// 싱글톤 구조을 위한 생성자
	private static MemberDAOImpl self;
	private MemberDAOImpl() {}
	public static MemberDAOImpl getInstance() {
		if(self==null) self = new MemberDAOImpl();
		return self;
	}
	
	private SqlSessionFactory sessionFactory =
			CustomSqlSessionFactoryBuilder.getSessionFactory();

	@Override
	public MemberVO selectMemberForAuth(String mem_id) {
		try(//open
		SqlSession session = sessionFactory.openSession();
		){
		// 어떤 쿼리문이 사용되는지에 따라 selectOne 경로 지정. select id 와 경로.
	//	return(MemberVO) session.selectOne("kr.or.ddit.member.dao.IMemberDAO.selectMemberForAuth");
		// open 해주면 close 해줘야함
		// 기존selectOne 보다 mapper 를 사용하면 파라미터를 누락 시킬 경우가 사라진다.
		// 프록시 (IMemberDAO)
		IMemberDAO mapper = session.getMapper(IMemberDAO.class);	
		return mapper.selectMemberForAuth(mem_id);	
		}
	}

	@Override
	public MemberVO selectMemberDetail(String mem_id) {
		try(
				SqlSession session = sessionFactory.openSession();
		){
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			return mapper.selectMemberDetail(mem_id);
		}
	}

	@Override
	public int insertMember(MemberVO member) {
		try(
				SqlSession session = sessionFactory.openSession();
		){
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			int cnt = mapper.insertMember(member);
						      // transaction 내가 다룰 수 있어야 함.
			session.commit(); // 커밋을 내가 할 수 있게 처리한다. session.commit();
			return cnt;
		}
	}

	@Override
	public int updateMember(MemberVO member) {
		try(
				SqlSession session = sessionFactory.openSession();
		){
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			int cnt = mapper.updateMember(member);
						      // transaction 내가 다룰 수 있어야 함.
			session.commit(); // 커밋을 내가 할 수 있게 처리한다. session.commit();
			return cnt;
		}
	}
	

	@Override
	public int deleteMember(String mem_id) {
		try(
				SqlSession session = sessionFactory.openSession();
		){
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			int cnt = mapper.deleteMember(mem_id);
						      // transaction 내가 다룰 수 있어야 함.
			session.commit(); // 커밋을 내가 할 수 있게 처리한다. session.commit();
			return cnt;
		}
	}

	@Override
	public List<MemberVO> selectMemberList() {
		try(
				SqlSession session = sessionFactory.openSession();
		){
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			return mapper.selectMemberList();
		}
	}
	

}
