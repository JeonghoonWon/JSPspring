package kr.or.ddit.fileupload.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.filter.wrapper.MultipartFile;
import kr.or.ddit.mvc.filter.wrapper.MultipartHttpServletRequest;

@Controller
public class FileUploadController {
   private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
   @RequestMapping("/fileUpload.do") // method 를 정해두지않으면 get 으로 
   public String form() {
      return "fileupload/uploadForm";  // 논리적 view name
   }
   
   @RequestMapping(value="/fileUpload.do", method=RequestMethod.POST)
   public String upload(
         @RequestParam("uploader") String uploader,
         @RequestPart(value="uploadFile1") MultipartFile[] file1, // 필수
         @RequestPart(value="uploadFile2",required=false) MultipartFile[] file2,  // optional 
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
      
      if(file1.length>0) {
    	  // 파일 선택이 되어있는 경우
    	  file1[0].saveTO(saveFolder);
    	  String saveFileUrl = saveFolderUrl + "/" + file1[0].getUniqueSaveName(); // url 완성
		  session.setAttribute("uploadFile1", saveFileUrl);
		  logger.info("saveFile : {}", saveFileUrl);
    	  
      }
      if(file2!=null && file2.length>0) {
    	  file2[0].saveTO(saveFolder);
    	  String saveFileUrl = saveFolderUrl + "/" + file2[0].getUniqueSaveName(); // url 완성
		  session.setAttribute("uploadFile2", saveFileUrl);
		  logger.info("saveFile : {}", saveFileUrl);
      }
      
      
//      if(req instanceof MultipartHttpServletRequest) {
//    	  MultipartHttpServletRequest wrapper =(MultipartHttpServletRequest) req;
//    	  Map<String, List<MultipartFile>> fileMap = wrapper.getFileMap(); // 모든 데이터를 가져오기위해 map 을 사용한다.
//    	  for(Entry<String,List<MultipartFile>> entry : fileMap.entrySet()) {
//    		  String partName = entry.getKey();
//    		  List<MultipartFile> files = entry.getValue(); 	
//    		  for(MultipartFile file : files) {
//    			  if(file.isEmpty()) continue;
//    			  file.saveTO(saveFolder);
//    			  String saveFileUrl = saveFolderUrl + "/" + file.getUniqueSaveName(); // url 완성
//    			  
//    			  session.setAttribute(partName, saveFileUrl);
//    			  logger.info("saveFile : {}", saveFileUrl);
//    		  }
//    		  
//    	  }
//    	  
//      }
//      Collection<Part> parts = req.getParts();
//      int index = 1;
//      for(Part tmp : parts) { 
//    	  // tmp 가 파일인지 아닌지 확인해줘야 한다.
//    	  if(tmp.getContentType()==null) continue;
//         String saveFileName = processPart(tmp, saveFolder);
//         String saveFileUrl = saveFolderUrl+"/"+saveFileName;
//         session.setAttribute("uploadFile" + index++, saveFileUrl);
//         logger.info("saveFile : {}", saveFileUrl);
//      }
//      
      logger.info("uploader : {}", uploader);
      return "redirect:/fileUpload.do";
   }

  
 
}
