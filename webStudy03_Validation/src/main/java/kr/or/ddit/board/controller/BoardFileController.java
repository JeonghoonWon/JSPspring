package kr.or.ddit.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;



import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.filter.wrapper.MultipartFile;
import kr.or.ddit.vo.AttatchVO;

@Controller
public class BoardFileController {
	// 파일하나에 대한 메타 데이터를 가져와야 하기 때문에 IAttatchDAO 필요.
	// 게시판을 싱글톤 적용하지않은 이유 : 알아서 스프링에서 해주기 때문에. 
	// 클라이언트가 바로 폴더로 접근 할 수 없기 때문에 handler 를 만들어서 접근 할 수 있게 처리한다.
	//   private IAttatchDAO service = AttatchDAOImpl.getInstance();
	   private IBoardService service = BoardServiceImpl.getInstance();
	   
	   @RequestMapping("/board/download.do")
	   public String download(
	         @RequestParam("what") int att_no
	         , HttpServletResponse resp
	         , HttpServletRequest req
	         ) throws IOException {
		   AttatchVO attatch = service.download(att_no);
		   File saveFolder = new File("d:/attatches");
		   File saveFile = new File(saveFolder, attatch.getAtt_savename());
		   //user 정보 받아오기
		   String agent = req.getHeader("User-Agent");
		   String filename = attatch.getAtt_filename();
		   if(StringUtils.containsIgnoreCase(agent, "trident")) {
			   filename = URLEncoder.encode(filename,"UTF-8").replaceAll("\\+"," "); // "+" 를 그냥 사용하면 정규식으로 사용되기 때문에 escape 시켜야한다. "\+"
		   }else {
			   byte[] bytes = filename.getBytes();
			   filename = new String(bytes,"ISO-8859-1");
		   }
		   
		   // 출력 스트림을 개방 하기 전에 처리 다운로드시 파일 이름 설정.
		   resp.setHeader("Content-Disposition","attatchment;filename=\""+filename+"\"");
		   resp.setHeader("Content-Length", attatch.getAtt_size()+"");// header의 값은 문자열로만 가능하기 때문에 size의 long type 을 +"" 로 문자열로 바꿔준다.
		   resp.setContentType("application/octet-stream"); // 저장을 하기 위해선 기본 마임대신 2진 데이터를 보내준다. 바이트 단위로? 
		   try(
				   OutputStream os = resp.getOutputStream();
			){
			   FileUtils.copyFile(saveFile, os);
		   }
		   return null;
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

   @RequestMapping(value="/board/boardImage.do", method=RequestMethod.POST)
   public String imageUpload(
      @RequestPart("upload") MultipartFile upload
      , HttpServletRequest req
      , HttpServletResponse resp
   ) throws IOException {
      String saveFolderURL = "/boardImages";
      String saveFolderPath = req.getServletContext().getRealPath(saveFolderURL);
      File saveFolder = new File(saveFolderPath);
    		  
      if(!saveFolder.exists()) { 
          saveFolder.mkdirs();   //파일이 존재하지 않는다면 파일을 만든다.
       }
      
      //마살링할때 vo가 없으면 map을 통해 객체를 넘어줄 것을 만든다.
      Map<String, Object> resultMap = new HashMap<>();
      if(!upload.isEmpty()){
         upload.saveTO(saveFolder);
         int uploaded = 1;
         String filename = upload.getOriginalFilename();
         String saveName = upload.getUniqueSaveName();
         String url = req.getContextPath() + saveFolderURL + "/" + saveName;
         resultMap.put("uploaded", uploaded);
         resultMap.put("fileName", filename);
         resultMap.put("url", url);
      }
      //-----이 코드까지가 업로드하는 과정
      resp.setContentType("application/json;charset=UTF-8");
      try(
         PrintWriter out = resp.getWriter();
      ){
         ObjectMapper mapper = new ObjectMapper();
         mapper.writeValue(out, resultMap);
      }
      return null;
   }
}