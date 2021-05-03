package kr.or.ddit.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.BoardVO;

/**
 * 첨부파일 관리를 위한 persistence layer
 *
 */
@Repository
public interface IAttatchDAO {
		//여러개 첨부파일 등록
		//게시글을 수정시애도 사용됨.
		public int insertAttatches(BoardVO board); 
		//첨부파일을 조회하는 경우는 download 
		public AttatchVO selectAttatch(int att_no); // AttatchVO : 선택된 파일 정보 필요하기때문에
		// 첨부파일은 수정이 없다. 삭제후 올리는 것
		// 여러개 첨부파일 삭제 (
		// 첨부파일 삭제시 cascade 를 사용하지 않은 이유 : 미들티어와 DB 두곳에 저장되어있기 떄문에  게시글 하나를 삭제할때 첨부파일이 미들티어, DB에 정보가 나눠져있기 때문에 삭제 할 수 없다. 
		// cascade를 적용 후 삭제가 되어 DB 값이 지워진다면 미들티어의 2진 데이터가 갈곳을 잃는다.
		public List<String> selectSaveNameForDelete(BoardVO board);
		public int deleteAttatches(BoardVO board);
}
