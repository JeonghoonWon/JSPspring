package kr.or.ddit.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.Reply2VO;

/**
 * 게시글 관리를 위한 persistence layer
 *
 */
@Repository
public interface IReplyDAO {
	// 하나의 길에 댓글 
	// 댓글은 비동기로 crud 댓글 목록 조회 페이징처리 . 하나의 댓글 수정, 삭제.
	// URI 만 설계할것.
	
	public int insertReply(Reply2VO reply); // 댓글 등록
	public int selectReplyCount(PagingVO<Reply2VO> pagingVO); // 페이징 처리를 위한 댓글 수 세기
	public List<Reply2VO> selectReplyList(PagingVO<Reply2VO> pagingVO);
	public Reply2VO selectReply(Reply2VO reply);
	public int updateReply(Reply2VO reply);
	public int deleteReply(Reply2VO reply);
	
	
}
