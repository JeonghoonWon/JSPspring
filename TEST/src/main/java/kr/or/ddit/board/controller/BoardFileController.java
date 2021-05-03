package kr.or.ddit.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.vo.AttatchVO;

@Controller
public class BoardFileController {
	// 파일하나에 대한 메타 데이터를 가져와야 하기 때문에 IAttatchDAO 필요.
	// 게시판을 싱글톤 적용하지않은 이유 : 알아서 스프링에서 해주기 때문에.
	// 클라이언트가 바로 폴더로 접근 할 수 없기 때문에 handler 를 만들어서 접근 할 수 있게 처리한다.
	// private IAttatchDAO service = AttatchDAOImpl.getInstance();
	
	@Inject	
	private WebApplicationContext container;
	
	@Inject
	private IBoardService service;
	@Value("#{appInfo.boardImages}")
	private String saveFolderURL;
	File saveFolder;
		
	
	private ServletContext application;
	// @Inject를 통해 자기자신에게 등록된 빈에게 자기 자신의 레퍼런스를 넣어줄 수 있음.
//	@Inject
//	public void setContainer(WebApplicationContext container) {
//		this.container = container;
//		application = container.getServletContext();
//		
//	}
	@PostConstruct // injection이 끝난 후 life cycle callback 을 호출하는 annotation
	public void init() {
		application = container.getServletContext(); // injection 이 끝난 다음에 호출. 
		String saveFolderPath = application.getRealPath(saveFolderURL);
		saveFolder = new File(saveFolderPath);
	
	}
	
	@RequestMapping("/board/download.do")
	public String download(
			@RequestParam("what") int att_no
			, Model model
			){
		AttatchVO attatch = service.download(att_no);  
		 model.addAttribute("attatch", attatch);
		return "downloadView";
	}

//		   
//		   
//		   int buffer_size = 1024 * 100;
//	      AttatchVO attatch = service.selectAttatch(att_no);
//	      String saveName = attatch.getAtt_savename();
//	      String fileName = attatch.getAtt_filename();
//	      
//	      String saveFolderURL = "D:/attatches";
//	      File saveFile = new File(saveFolderURL + "/" + saveName);
//	      
//	      OutputStream outstream = null;
//	      FileInputStream fin = null;
//	      if(saveFile.exists()) { // Download할 파일이 있는지 검사
//	         // Download진행 순서
//	         // 1. ContentType 설정
//	         resp.setContentType("application/octet-stream; charset=utf-8");
//	         
//	         // 2. Response 객체의 헤더에 'Content-Disposition'속성을 설정한다.
//	         String headerKey = "Content-Disposition";
//	         //String headerValue = "attachment; filename=\""+ file.getName()+"\";";
//	         String headerValue = "attachment; filename=\""+ getDisposition(fileName, getBrowser(req))+"\";";
//	         resp.setHeader(headerKey, headerValue);
//	         
//	         // 3. 디스크에서 파일을 읽어서 클라이언트로 전송
//	         try {
//	            // 출력용 스트림 객체 생성
//	            outstream = resp.getOutputStream();
//	            
//	            // 파일 입력용 스트림 객체 생성
//	            fin = new FileInputStream(saveFile);
//	            byte[] buffer = new byte[buffer_size];
//	            int len = -1;
//	            
//	            // byte배열을 이용해서 파일 내용을 읽어와 출력용 스트림으로 출력한다.
//	            while((len=fin.read(buffer))>0) {
//	               outstream.write(buffer, 0, len);
//	            }
//	         } catch (IOException e) {
//	            System.out.println("입출력 오류 : " + e.getMessage());
//	         } finally {
//	            outstream.flush();
//	            
//	            if(fin!=null) {
//	               fin.close();
//	            }
//	            
//	            if(outstream!=null) {
//	               outstream.close();
//	            }
//	         }
//	      }else { //Download할 파일이 없을 경우
//	         resp.setCharacterEncoding("utf-8");
//	         resp.setContentType("text/html; charset=utf-8");
//	         resp.getWriter().println("<h3>" + fileName + " 파일은 존재하지 않습니다.</h3>");
//	      }
//	      return null;
//	   }
//	   
//	   // 사용자의 웹브라우저 종류를 구분하는 메서드
//	   private String getBrowser(HttpServletRequest request) {
//	      String header = request.getHeader("User-Agent");
//	      if(header.indexOf("MSIE")>-1) {
//	         return "MSIE";
//	      }else if(header.indexOf("Chrome")>-1) {
//	         return "Chrome";
//	      }else if(header.indexOf("Opera")>-1) {
//	         return "Opera";
//	      }else if(header.indexOf("Trident/7.0")>-1) {
//	         return "MSIE"; 
//	      }
//	      
//	      return"Firefox";
//	   }
//	   
//	   // 웹브라우저 별로 파일명을 인코딩하는 메서드
//	   private String getDisposition(String filename, String browser) {
//	      String encodedFilename = null; // 인코딩된 파일명이 저장될 변수
//	      
//	      try {
//	         if(browser.equals("MSIE")) {
//	            encodedFilename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20");
//	         }else if(browser.equals("Firefox")) {
//	            encodedFilename = "\"" + new String(filename.getBytes("utf-8"), "8859_1") + "\"";
//	         }else if(browser.equals("Opera")) {
//	            encodedFilename = "\"" + new String(filename.getBytes("utf-8"), "8859_1") + "\"";
//	         }else if(browser.equals("Chrome")) {
//	            StringBuffer sb = new StringBuffer();
//	            for(int i = 0; i < filename.length(); i++) {
//	               char c = filename.charAt(i);
//	               if(c > '~') {
//	                  sb.append(URLEncoder.encode("" + c, "utf-8"));
//	               }else {
//	                  sb.append(c);
//	               }
//	            }
//	            encodedFilename = sb.toString();
//	         }else { // 기타브라우저 
//	            throw new RuntimeException("Not suppoerted Browser");
//	         }
//	      } catch (UnsupportedEncodingException e) {
//	         // TODO Auto-generated catch block
//	         e.printStackTrace();
//	      }
//	      
//	      return encodedFilename;
//	   }

	@RequestMapping(value = "/board/boardImage.do"
			, method = RequestMethod.POST
			, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)  //"application/json;charset=UTF-8" 대신 사용. 하드코딩 할 필요 없다.
	@ResponseBody // body 를 통해 전달 해준다는 annotaion 이걸 H.A 가 가져감. H.A 에서 마샬링, 직렬화 진행
	public Map<String, Object> imageUpload(@RequestPart("upload") MultipartFile upload) throws IllegalStateException, IOException // resp를 지웠음. 이젠 마샬링,직렬화 우리가 할 필요 없다.
			 {
		
		// 인젝션 전역변수로 빼야하고 서블릿 컨텍스트 해야함. 세이브 폴터를 언제 만들지 어플리케이션 다 들어온다음에 인젝션 받은 다음에 .


		if (!saveFolder.exists()) {
			saveFolder.mkdirs(); // 파일이 존재하지 않는다면 파일을 만든다.
		}

		// 마살링할때 vo가 없으면 map을 통해 객체를 넘어줄 것을 만든다.
		Map<String, Object> resultMap = new HashMap<>();
		if (!upload.isEmpty()) {  
			String saveName = UUID.randomUUID().toString(); //upload.getUniqueSaveName(); 저장명 설정. 저장명은 다양하게 사용 할 수 있기때문에.
		//	upload.saveTO(saveFolder);
			upload.transferTo(new File(saveFolder, saveName));
			int uploaded = 1;
			String filename = upload.getOriginalFilename();
			String url = application.getContextPath() + saveFolderURL + "/" + saveName;
			resultMap.put("uploaded", uploaded);
			resultMap.put("fileName", filename);
			resultMap.put("url", url);
		}
		// -----이 코드까지가 업로드하는 과정
//		resp.setContentType("application/json;charset=UTF-8");  마샬링 
//		try (PrintWriter out = resp.getWriter();) { 직렬화
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.writeValue(out, resultMap);
//		}
		return resultMap;
	}
}