package kr.or.ddit.mvc.filter.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class MultipartFile {
	private Part adaptee;
	private String originalFilename;
	private String uniqueSaveName;
	private boolean empty;
	
	
	public MultipartFile(Part adaptee) {
		super();
		this.adaptee =adaptee;
		
		String disposition = adaptee.getHeader("Content-Disposition");
		int index = disposition.indexOf("\"",disposition.indexOf("filename="));
//		String originalFilename = null;
		if(index!=-1) {
			originalFilename = disposition.substring(index).replace("\"","");
		} // originalFilename 원본 파일명 생성 ->  private String originalFilename; 에 값 넘어감.
		empty = StringUtils.isBlank(originalFilename); // 원본 파일 명이 없으면 업로드할 파일도 없다.
		
		this.uniqueSaveName = UUID.randomUUID().toString(); 
		// 사용 이유
		// 1. 확장자를 제거.
		// 2. 업로드 하는 사람이 파일 이름을 예측 할 수 없게 하기 위해서.
	}
		  
	public String getName() {
		return adaptee.getName();
		// part name 을 가져옴.
	}
	
	public String getContentType() {
		return adaptee.getContentType();
		// 
	}
	
	public long getFileSize() {
		return adaptee.getSize();
		// 파일이 넘어왔으면 파일 크기를 알 수 있음
	}
	
	public String getOriginalFilename() {
		return originalFilename;	//getter 생성
		// part 가 가지고 있지 않던 추가적인 interface 를 만들어준것.
		
	}
	
	public String getUniqueSaveName() {
		return uniqueSaveName;
	
	}
	public InputStream getInputStream() throws IOException {
		return adaptee.getInputStream();
	}
	
	public void saveTO(File saveFolder) throws IOException {
		File saveFile = new File(saveFolder, uniqueSaveName); // 생성되는 파일 객체의 이름은 아무도 모른다.
		adaptee.write(saveFile.getAbsolutePath()); // saveFile의 절대 경로에 파일을 저장하겠다.
	}
	
	public boolean isEmpty() {
		// 비어있는 파일 구분 . 추가적인 속성 적용 가능
		return empty;
	}
	
	public byte[] getBytes() throws IOException {
		try(
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		InputStream is = getInputStream();
		){
		IOUtils.copy(is, os);
		return os.toByteArray();
		}
	}
}
