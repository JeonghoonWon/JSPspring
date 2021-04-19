package kr.or.ddit.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.ddit.mvc.annotation.Controller;
import kr.or.ddit.mvc.annotation.RequestMapping;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.filter.wrapper.MultipartFile;

@Controller
public class BoardFileController {

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