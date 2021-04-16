package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.dao.AttatchDAOImpl;
import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IAttatchDAO;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;


public class BoardServiceImpl implements IBoardService {
	private static BoardServiceImpl self;
	private BoardServiceImpl() {}
	public static BoardServiceImpl getInstance() {
		if(self==null) self = new BoardServiceImpl();
		return self;
	}
		
	private IBoardDAO boardDAO = BoardDAOImpl.getInstance();
	private IAttatchDAO attatchDAO = AttatchDAOImpl.getInstance();

	@Override
	public ServiceResult createBoard(BoardVO board) {
		int cnt = boardDAO.insertBoard(board);
		ServiceResult result = ServiceResult.FAIL;
		if(cnt>0)
			result = ServiceResult.OK;
		return result;
	}

	@Override
	public int retrieveBoardCount(PagingVO<BoardVO> pagingVO) {
		return boardDAO.selectBoardCount(pagingVO);
	}

	@Override
	public List<BoardVO> retrieveBoardList(PagingVO<BoardVO> pagingVO) {
		return boardDAO.selectBoardList(pagingVO);
	}

	@Override
	public BoardVO retrieveBoard(BoardVO search) {
		// 게시글 하나의 정보 .가져오기 
				BoardVO board = boardDAO.selectBoard(search);
				if(board==null)
				// custom exception 발생
					throw new CustomException(
							search.getBo_no()+"에 해당하는 게시글이 없음");
					
				return board;
			}

	@Override
	public ServiceResult modifyBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult removeBoard(BoardVO search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttatchVO download(int att_no) {
		// TODO Auto-generated method stub
		return null;
	}

}
