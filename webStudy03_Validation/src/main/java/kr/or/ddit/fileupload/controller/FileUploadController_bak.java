package kr.or.ddit.fileupload.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.BadRequestException;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;

//@Controller
public class FileUploadController_bak {
   private static final Logger logger = LoggerFactory.getLogger(FileUploadController_bak.class);
   @RequestMapping("/fileUpload.do") // method 를 정해두지않으면 get 으로 
   public String form() {
      return "fileupload/uploadForm";  // 논리적 view name
   }
   
   @RequestMapping(value="/fileUpload.do", method=RequestMethod.POST)
   public String upload(
         @RequestParam("uploader") String uploader,
         HttpServletRequest req, HttpSession session) throws IOException, ServletException {
	//  @RequestParam("uploader") String uploader 를 사용했기때문에 
	//  String uploader = req.getParameter("uploader"); 실행 할 필요 없다.
	//req.getParameter("uploadFile"); // return 값이 string 이기 때문에 file 의 2진데이터 가져올 수 없다.
			
	   
      ServletContext application = req.getServletContext();
      String saveFolderUrl ="/prodImages";
      File saveFolder = new File(application.getRealPath(saveFolderUrl));   // 하드코딩 하지 않고 서버의 위치가 어디있느냐에 따라 위치를 받아올 수 있다.
      
      if(!saveFolder.exists()) { 
         saveFolder.mkdirs();   //파일이 존재하지 않는다면 파일을 만든다.
      }
      
      Collection<Part> parts = req.getParts();
      int index = 1;
      for(Part tmp : parts) { 
    	  // tmp 가 파일인지 아닌지 확인해줘야 한다.
    	  if(tmp.getContentType()==null) continue;
         String saveFileName = processPart(tmp, saveFolder);
         String saveFileUrl = saveFolderUrl+"/"+saveFileName;
         session.setAttribute("uploadFile" + index++, saveFileUrl);
         logger.info("saveFile : {}", saveFileUrl);
      }
      
      logger.info("uploader : {}", uploader);
      return "redirect:/fileUpload.do";
   }

   private String getOriginalFilename(Part part) {
	 // Content-Disposition: form-data; name="uploadFile1"; filename=""  
	String disposition = part.getHeader("Content-Disposition");
	int index = disposition.indexOf("\"",disposition.indexOf("filename="));
	String originalFilename = null;
	if(index!=-1) {
		originalFilename = disposition.substring(index).replace("\"","");
	}
	return originalFilename;
   }
   
   private String processPart(Part uploadFile1, File saveFolder) throws IOException {
      String originalFilename1 = getOriginalFilename(uploadFile1);  
      if(StringUtils.isNotBlank(originalFilename1)) { //원본파일이 없는 경우
         String mime1 = uploadFile1.getContentType();
         if(!mime1.startsWith("image/")) {   //이미지가 아닌파일이 업로드 된다면?
            throw new BadRequestException();   //핸들러 어뎁터에 요청이 넘어간다.
         }
         File saveFile1 = new File(saveFolder, originalFilename1);
         byte[] buffer = new byte[1024];
         int cnt = -1;
         try(
            InputStream is1 = uploadFile1.getInputStream();
            FileOutputStream fos1 = new FileOutputStream(saveFile1);
         ){
            while((cnt = is1.read(buffer))!=-1) {
               fos1.write(buffer, 0, cnt);   
            }
         }   //try end
      }//if end
      return originalFilename1;
      
      
      
      
   }
}
