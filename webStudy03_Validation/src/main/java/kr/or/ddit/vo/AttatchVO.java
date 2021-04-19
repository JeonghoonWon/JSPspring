package kr.or.ddit.vo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import kr.or.ddit.mvc.filter.wrapper.MultipartFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author PC-19
 *
 */
@Data
@EqualsAndHashCode(of="att_no")
@NoArgsConstructor
@ToString(exclude="file") // MultipartFile 은 2진 데이터이기 떄문에 toString 에서 제외시켜서 출력되지 않게 처리한다.
public class AttatchVO implements Serializable {
	
	// VO는 다양한 곳에서 사용하기 때문에 잘 짜둬야 한다.
	
	
	private transient MultipartFile file;	// transient 파일의 직렬화 제외 
	
	//생성자를 통해 파일을 받아야한다.

	public AttatchVO(MultipartFile file) {
		super();
		this.file = file;
		this.att_filename = file.getOriginalFilename();
		this.att_savename = file.getUniqueSaveName();
		this.att_contenttype = file.getContentType();
		this.att_size = file.getFileSize(); // size 가 맞지않기 때문에 att_size 를 long 으로 변경해준다.
	}
	private Integer bo_no;
	private Integer att_no;
	private String att_filename;
	private String att_savename;
	private long att_size;
	private String att_contenttype;
	private Integer att_downcount;
	
	// 미들티어에 저장 하기 위한 메서드 
	public void saveTo(File saveFolder) throws IOException {
		file.transferTo(new File(saveFolder,att_savename)); 
	}
	
	
}
