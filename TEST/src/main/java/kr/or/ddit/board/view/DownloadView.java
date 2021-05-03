package kr.or.ddit.board.view;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.AbstractView;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.vo.AttatchVO;

public class DownloadView extends AbstractView {
	private static final Logger logger = 
			LoggerFactory.getLogger(DownloadView.class);
	
	@Value("#{appInfo.attatchPath}")
	private File saveFolder;
	
	@PostConstruct
	public void init() {
		logger.info("{}초기화,{}주입됨."
				, getClass().getSimpleName()
				, saveFolder.getAbsolutePath());
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		
		AttatchVO attatch = (AttatchVO) model.get("attatch");
		String agent = req.getHeader("User-Agent");
		
		File saveFile = new File(saveFolder, attatch.getAtt_savename());
		// user 정보 받아오기
//		   String agent = req.getHeader("User-Agent");
		String filename = attatch.getAtt_filename();
		// agent 는 값이 있어야 하기때문에 @RequestHeader(value = "User-Agent", required= true) 는
		// true 가 되어야 함.
		if (StringUtils.containsIgnoreCase(agent, "trident")) {
			filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", " "); // "+" 를 그냥 사용하면 정규식으로 사용되기 때문에
																					// escape 시켜야한다. "\+"
		} else {
			byte[] bytes = filename.getBytes();
			filename = new String(bytes, "ISO-8859-1");
		}

		// 출력 스트림을 개방 하기 전에 처리 다운로드시 파일 이름 설정.
		resp.setHeader("Content-Disposition", "attatchment;filename=\"" + filename + "\"");
		resp.setHeader("Content-Length", attatch.getAtt_size() + "");// header의 값은 문자열로만 가능하기 때문에 size의 long type 을 +""
																		// 로 문자열로 바꿔준다.
		resp.setContentType("application/octet-stream"); // 저장을 하기 위해선 기본 마임대신 2진 데이터를 보내준다. 바이트 단위로?
		try (OutputStream os = resp.getOutputStream();) {
			FileUtils.copyFile(saveFile, os);
		}

	}

}
