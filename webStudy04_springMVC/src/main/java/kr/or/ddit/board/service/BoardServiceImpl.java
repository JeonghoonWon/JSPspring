package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.board.dao.IAttatchDAO;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.dao.IReplyDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.member.service.AuthenticateServiceImpl;
import kr.or.ddit.member.service.IAuthenticateService;
import kr.or.ddit.utils.CryptoUtil;
import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.Reply2VO;

@Service
public class BoardServiceImpl implements IBoardService {
	private static final Logger logger = 
			LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Inject
	private IReplyDAO replyDAO;

	
	private IBoardDAO boardDAO;  // 프록시가 들어오게된다.
	 @Inject//required와 autoWired가 합쳐진거 / com.sun.proxy.$Proxy35 => 가짜 구현체(proxy)가 들어온다.
	   public void setBoardDAO(IBoardDAO boardDAO) {
	      this.boardDAO = boardDAO;
	      logger.info("주입된 boardDAO : {}", boardDAO.getClass().getName());
	}
	private IAttatchDAO attatchDAO;
	@Inject
	public void setAttatchDAO(IAttatchDAO attatchDAO) {
		this.attatchDAO = attatchDAO;
		logger.info("주입된 attatchDAO: {}", attatchDAO.getClass().getName());
	}
	
	@Value("#{appInfo.attatchPath}") // 표현 기반. el 을 사용하면 된다.
	private String attatchPath;
	
	// File 을 사용하기보단 resource 를 사용하는게 좋다. 하지만 아래 File 사용중이기 때문에 File 유지.
	private File saveFolder;
	
	@PostConstruct
	public void init() {
		saveFolder = new File("D:/attatches");
		logger.info("{}초기화,{}주입됨."
					, getClass().getSimpleName()
					, saveFolder.getAbsolutePath());
	}
	
	private void encodeRePassword(Reply2VO reply) {
		String rep_pass = reply.getRep_pass();
		if(StringUtils.isBlank(rep_pass)) return;
		try {
			String encodedPass = CryptoUtil.sha512(rep_pass);
			reply.setRep_pass(encodedPass);
		}catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	
	private void encodePassword(BoardVO board) {
		String bo_pass = board.getBo_pass();
		if(StringUtils.isBlank(bo_pass)) return; // 암호화 할 필요 없으면 return
		try {
			String encodedPass = CryptoUtil.sha512(bo_pass);
			board.setBo_pass(encodedPass);
		}catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	private int processes(BoardVO board) {
		int cnt = 0;
		List<AttatchVO> attatchList = board.getAttatchList();
		if(attatchList != null && attatchList.size() > 0) {
			cnt += attatchDAO.insertAttatches(board);
			try {
				for(AttatchVO attatch : attatchList) {
//					if(1==1)
//						throw new RuntimeException("강제 발생 예외");
					attatch.saveTo(saveFolder);
				}
			}catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return cnt;
	}
	
	private int deleteFileProcesses(BoardVO board) {
		int[] delAttNos = board.getDelAttNos();
		int cnt = 0;
		if(delAttNos!=null && delAttNos.length > 0) {
			List<String> saveNames = 
					attatchDAO.selectSaveNameForDelete(board);
			for(int delAttNo : delAttNos) {
				AttatchVO attatch = attatchDAO.selectAttatch(delAttNo);
				saveNames.add(attatch.getAtt_savename());
			}
			// 첨부파일 메타 데이터 삭제
			attatchDAO.deleteAttatches(board);
			// 이진 데이터 삭제
			for(String saveName : saveNames) {
				File saveFile = new File(saveFolder,saveName);
				saveFile.delete();
				
			}
		}
		return cnt;
	}
	
	@Transactional	// 어노테이션 하나를 붙여서 코드를 선언하는것 -=> 선언적 프로그래밍)
	@Override
	public ServiceResult createBoard(BoardVO board) {
		
		ServiceResult result = ServiceResult.FAIL;
		// == 비밀번호 암호화 ==
		encodePassword(board);
		//=======================
		
		
			int cnt = boardDAO.insertBoard(board);
			if(cnt>0) {
				// == 첨부파일 처리 ==
				cnt += processes(board);
				// ===============
				if(cnt>0) {
					// ok가 결정 될 때만 commit
					result = ServiceResult.OK;
		
				
			}
		}// try end
		
		
		
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
					throw new CustomException(search.getBo_no()+"에 해당하는 게시글이 없음");
				
				boardDAO.incrementHit(board.getBo_no());
				return board;
			}
	
	@Transactional
	@Override
	public ServiceResult modifyBoard(BoardVO board) {

		// 게시글 존재 여부 확인
		ServiceResult result = ServiceResult.INVALIDPASSWORD;
		encodePassword(board);
		
				// 인증 성공시
				// 게시글의 일반 데이터 수정
				// 신규 파일에 대한 등록
				// 삭제할 파일에 대한 처리
				int cnt = boardDAO.updateBoard(board);
				if(cnt>0) {
					// 신규 파일에 대한 등록
				cnt += processes(board);
					// 삭제할 파일에 대한 처리
					cnt += deleteFileProcesses(board);
					if(cnt>0)
						result = ServiceResult.OK;
				
				}
				return result;
		} //try end
	
	@Transactional
	@Override
	public ServiceResult removeBoard(BoardVO search) {
	
				ServiceResult result = ServiceResult.FAIL;
				BoardVO savedBoard = boardDAO.selectBoard(search);
				encodePassword(search);
				String savedPass = savedBoard.getBo_pass();
				String inputPass = search.getBo_pass();
				// 인증
				if(savedPass.equals(inputPass)) {
					// 첨부파일 삭제
					List<AttatchVO> attatchList 
						= savedBoard.getAttatchList();
					if(attatchList.size()>0) {
						int[] delAttNos = new int[attatchList.size()];
						search.setDelAttNos(delAttNos);
						for(int i = 0; i < delAttNos.length; i++) {
							delAttNos[i] = 
									attatchList.get(i).getAtt_no();
						}	
						deleteFileProcesses(search);
					}// if(attatchList.size) end
					
					// 게시글 삭제
					int cnt = boardDAO.deleteBoard(search);
					if(cnt>0) {
						result = ServiceResult.OK;
				
					}
				}else {
					result = ServiceResult.INVALIDPASSWORD;
				}
				return result;
			}
		
	@Override
	public AttatchVO download(int att_no) {
		AttatchVO attatch = attatchDAO.selectAttatch(att_no);
		if(attatch==null)
			throw new CustomException( att_no + "에 해당하는 파일이 없음.");
		return attatch;
	}
	@Override
	public boolean boardAuthenticate(BoardVO search) {
		// BoardVO search : 입력 된 값.
		BoardVO saved = boardDAO.selectBoard(search);
		//BoardVO saved : 저장된 값.
		encodePassword(search);
		String savedPass = saved.getBo_pass();
		String inputPass = search.getBo_pass();
		
		
		return savedPass.equals(inputPass);
	}
	@Override
	public Object recommend(int bo_no) {
		Object result = ServiceResult.FAIL;
		int cnt = boardDAO.incrementRcmd(bo_no);
		if(cnt > 0) {
			BoardVO search = new BoardVO();
			search.setBo_no(bo_no);
			BoardVO boardVO = boardDAO.selectBoard(search);
			result = boardVO.getBo_rec();
		}
		return result;
	}

	@Override
	public Object report(int bo_no) {
		Object result = ServiceResult.FAIL;
		int cnt = boardDAO.incrementRpt(bo_no);
		if(cnt > 0) {
			BoardVO search = new BoardVO();
			search.setBo_no(bo_no);
			BoardVO boardVO = boardDAO.selectBoard(search);
			result = boardVO.getBo_rep();	
		}
		return result;
	}



	@Override
	public ServiceResult createReply(Reply2VO reply) {
		ServiceResult result = ServiceResult.FAIL;
		// == 비밀번호 암호화 ==
		encodeRePassword(reply);
		//=======================
		
		
			int cnt = replyDAO.insertReply(reply);
			if(cnt>0) result = ServiceResult.OK;
		
			return result;
	}
}


