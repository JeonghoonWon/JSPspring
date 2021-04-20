package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

/**
 * 게시판 관리를 위한 Business Logic Layer
 *
 */
public interface IBoardService {
	//보드생성
	/** 신규 게시글 등록
	 * @param board 
	 * @return
	 */
	public ServiceResult createBoard(BoardVO board); // bo_no 를 seq 로 넘기기때문에 성공 실패 두가지 경우만 발생 /ServiceResult enum 사용
	// 
	public int retrieveBoardCount(PagingVO<BoardVO> pagingVO);
	public List<BoardVO> retrieveBoardList(PagingVO<BoardVO> pagingVO);
	/**
	 * @param search
	 * @return 존재하지 않는다면 cusntom exception
	 */
	public BoardVO retrieveBoard(BoardVO search);	// 게시글이 존재하지않는 경우 예외 발생시켜야함
	/**
	 * 게시글 수정
	 * @param board
	 * @return 
	 */
	public ServiceResult modifyBoard(BoardVO board);	// 게시글이 존재하는 지 확인 - > pass 확인 => 암호화를 통해 확인 => 그다음 수정 => 수정 후 성공 여부 판단
	public ServiceResult removeBoard(BoardVO search); // modifyBoard 와 동일. 게시글이 존재하는 지 확인 - > pass 확인 => 암호화를 통해 확인 => 그다음 수정 => 수정 후 성공 여부 판단

	
	public AttatchVO download(int att_no); //  // return 값 : AttatchVO -> 파일 정보 필요
	
	public boolean boardAuthenticate(BoardVO search); // bo_no 와 bo_pass 필요. 평문으로 입력된 bo_pass 를 encoding 후 암호화 후 비교 해야함. 

}
