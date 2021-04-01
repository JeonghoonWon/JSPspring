package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.exception.DataAccessException;
import kr.or.ddit.vo.MemberVO;

public class MemberDAOImpl implements IMemberDAO {

	@Override
	public MemberVO selectMemberForAuth(String mem_id) {
		MemberVO member = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT MEM_ID, MEM_PASS, MEM_NAME, MEM_MAIL                 ");
		sql.append(" FROM MEMBER                                       ");
		sql.append(" WHERE MEM_ID = ?   ");
		try (Connection conn = ConnectionFactory.getConnection();
//			Statement stmt = conn.createStatement();	
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setString(1, mem_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new MemberVO();
				member.setMem_id(rs.getString("MEM_ID"));
				member.setMem_pass(rs.getString("MEM_PASS"));
				member.setMem_name(rs.getString("MEM_NAME"));
				member.setMem_mail(rs.getString("MEM_MAIL"));
			}
			return member;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public MemberVO selectMemberDetail(String mem_id) {
		MemberVO member = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                                         ");
		sql.append("     MEM_ID,    MEM_PASS,    MEM_NAME,                          ");
		sql.append("     MEM_REGNO1,    MEM_REGNO2,                                 ");
		sql.append("     TO_CHAR(MEM_BIR, 'YYYY-MM-DD') MEM_BIR,                    ");
		sql.append("     MEM_ZIP,    MEM_ADD1,    MEM_ADD2,                         ");
		sql.append("     MEM_HOMETEL,    MEM_COMTEL,    MEM_HP,                     ");
		sql.append("     MEM_MAIL,    MEM_JOB,    MEM_LIKE,                         ");
		sql.append("     MEM_MEMORIAL,                                              ");
		sql.append("     TO_CHAR(MEM_MEMORIALDAY, 'YYYY-MM-DD') MEM_MEMORIALDAY,    ");
		sql.append("     MEM_MILEAGE, MEM_DELETE                                    ");
		sql.append(" FROM    MEMBER                                                 ");
		sql.append(" WHERE MEM_ID = ?                                               ");
		try (Connection conn = ConnectionFactory.getConnection();
//			Statement stmt = conn.createStatement();	
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setString(1, mem_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new MemberVO();
				member.setMem_id(rs.getString("MEM_ID"));
				member.setMem_pass(rs.getString("MEM_PASS"));
				member.setMem_name(rs.getString("MEM_NAME"));
				member.setMem_regno1(rs.getString("MEM_REGNO1"));
				member.setMem_regno2(rs.getString("MEM_REGNO2"));
				member.setMem_bir(rs.getString("MEM_BIR"));
				member.setMem_zip(rs.getString("MEM_ZIP"));
				member.setMem_add1(rs.getString("MEM_ADD1"));
				member.setMem_add2(rs.getString("MEM_ADD2"));
				member.setMem_hometel(rs.getString("MEM_HOMETEL"));
				member.setMem_comtel(rs.getString("MEM_COMTEL"));
				member.setMem_hp(rs.getString("MEM_HP"));
				member.setMem_mail(rs.getString("MEM_MAIL"));
				member.setMem_job(rs.getString("MEM_JOB"));
				member.setMem_like(rs.getString("MEM_LIKE"));
				member.setMem_memorial(rs.getString("MEM_MEMORIAL"));
				member.setMem_memorialday(rs.getString("MEM_MEMORIALDAY"));
				member.setMem_mileage(rs.getInt("MEM_MILEAGE"));
				member.setMem_delete(rs.getString("MEM_DELETE"));
			}
			return member;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public int insertMember(MemberVO member) {

		StringBuffer sql = new StringBuffer();

		sql.append(" INSERT INTO MEMBER (                                   ");
		sql.append("    MEM_ID,    MEM_PASS,    MEM_NAME,                   ");
		sql.append("    MEM_REGNO1,    MEM_REGNO2,    MEM_BIR,              ");
		sql.append("    MEM_ZIP,    MEM_ADD1,    MEM_ADD2,                  ");
		sql.append("    MEM_HOMETEL,    MEM_COMTEL,    MEM_HP,              ");
		sql.append("    MEM_MAIL,    MEM_JOB,    MEM_LIKE,                  ");
		sql.append("    MEM_MEMORIAL,    MEM_MEMORIALDAY,    MEM_MILEAGE   ");
		sql.append("    )                                                   ");
		sql.append(" VALUES (                                               ");
		sql.append("		?,    ?,    ?,                                  ");
		sql.append("	    ?,    ?,    TO_DATE(?,'YYYY-MM-DD'),            ");
		sql.append("	    ?,    ?,    ?,                                  ");
		sql.append("	    ?,    ?,    ?,                                  ");
		sql.append("	    ?,    ?,    ?,                                  ");
		sql.append("	    ?,    TO_DATE(?,'YYYY-MM-DD'),    3000          ");
		sql.append("    	)                                               ");

		try (Connection conn = ConnectionFactory.getConnection();
//			Statement stmt = conn.createStatement();	
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			int i = 1;
			pstmt.setString(i++, member.getMem_id());
			pstmt.setString(i++, member.getMem_pass());
			pstmt.setString(i++, member.getMem_name());
			pstmt.setString(i++, member.getMem_regno1());
			pstmt.setString(i++, member.getMem_regno2());
			pstmt.setString(i++, member.getMem_bir());
			pstmt.setString(i++, member.getMem_zip());
			pstmt.setString(i++, member.getMem_add1());
			pstmt.setString(i++, member.getMem_add2());
			pstmt.setString(i++, member.getMem_hometel());
			pstmt.setString(i++, member.getMem_comtel());
			pstmt.setString(i++, member.getMem_hp());
			pstmt.setString(i++, member.getMem_mail());
			pstmt.setString(i++, member.getMem_job());
			pstmt.setString(i++, member.getMem_like());
			pstmt.setString(i++, member.getMem_memorial());
			pstmt.setString(i++, member.getMem_memorialday());
			return pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

		@Override
		public int updateMember(MemberVO member) {
			StringBuffer sql = new StringBuffer();
		sql.append("	UPDATE MEMBER           ");
		sql.append("    SET                     ");
		sql.append("       MEM_NAME =?,         ");
		sql.append("       MEM_BIR =?,          ");
		sql.append("       MEM_ZIP =?,          ");
		sql.append("       MEM_ADD1 =?,         ");
		sql.append("       MEM_ADD2 =?,         ");
		sql.append("       MEM_HOMETEL =?,      ");
		sql.append("       MEM_COMTEL =?,       ");
		sql.append("       MEM_HP =?,           ");
		sql.append("       MEM_MAIL =?,         ");
		sql.append("       MEM_JOB =?,          ");
		sql.append("       MEM_LIKE =?,         ");
		sql.append("       MEM_MEMORIAL =?,     ");
		sql.append("       MEM_MEMORIALDAY =?   ");
		sql.append("  WHERE                     ");
		sql.append("	    MEM_ID =?           ");

		
		

		try (Connection conn = ConnectionFactory.getConnection();
//				Statement stmt = conn.createStatement();	
					PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {

			int i = 1;
			pstmt.setString(i++, member.getMem_name());
			pstmt.setString(i++, member.getMem_bir());
			pstmt.setString(i++, member.getMem_zip());
			pstmt.setString(i++, member.getMem_add1());
			pstmt.setString(i++, member.getMem_add2());
			pstmt.setString(i++, member.getMem_hometel());
			pstmt.setString(i++, member.getMem_comtel());
			pstmt.setString(i++, member.getMem_hp());
			pstmt.setString(i++, member.getMem_mail());
			pstmt.setString(i++, member.getMem_job());
			pstmt.setString(i++, member.getMem_like());
			pstmt.setString(i++, member.getMem_memorial());
			pstmt.setString(i++, member.getMem_memorialday());
			pstmt.setString(i++, member.getMem_id());
			return pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new DataAccessException(e);
		}
	
	}

	@Override
	public int deleteMember(String mem_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MemberVO> selectMemberList() {
		// TODO Auto-generated method stub
		return null;
	}

}
