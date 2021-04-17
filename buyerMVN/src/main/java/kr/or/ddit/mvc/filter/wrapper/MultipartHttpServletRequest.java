package kr.or.ddit.mvc.filter.wrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

// 멀티파트를 담당하는 wrapper로 사용.

public class MultipartHttpServletRequest extends HttpServletRequestWrapper {
// adapter 가 되려면 adaptee 를 받을 수 있어야 한다.
	private Map<String, List<MultipartFile>> fileMap; 
	// input 의 이름이 같을수 있기 때문에 MultipartFile 을 List 로 만들어준다.
	
	
	public MultipartHttpServletRequest(HttpServletRequest request) throws IOException, ServletException {
		// 모든 데이터는 request 가 가지고 있다.
		super(request);
		fileMap = new LinkedHashMap<>();
		paresRequest(request);
	}
	private void paresRequest(HttpServletRequest request) throws IOException, ServletException {
		Collection<Part> parts = request.getParts();
		for(Part tmp: parts) {
			if(tmp.getContentType()==null) continue;
			String partName = tmp.getName();
			List<MultipartFile> list = fileMap.get(partName);
			if(list == null) {
				list = new ArrayList<>();
				fileMap.put(partName, list);
			}
			list.add(new MultipartFile(tmp));
		}
		
	}
		public Map<String, List<MultipartFile>> getFileMap() {
			return fileMap;
		}
		
		public MultipartFile getFile(String name) {
			List<MultipartFile> files = fileMap.get(name);
			MultipartFile file = null;
			if(files!=null && files.size() > 0)
				file = files.get(0);
			return file;
		}
		
		public List<MultipartFile> getFiles(String name) {
			// 멀티파트파일 객체로 캡슐화(맵핑) 한다.
			return fileMap.get(name);
		}
}
