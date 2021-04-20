package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.validator.BoardInsertGroup;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.validator.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 공지글/게시글을 공통으로 관리할 domain layer
 *
 */
@Data
@EqualsAndHashCode(of="bo_no")
@ToString(exclude= {"attatchList", "replyList"})
//그룹이 3개 존재. 기본그룹, 인서트 그룹, 업데이트 그룹
public class BoardVO implements Serializable{
	private Integer bo_sort;
	@NotBlank
	private String bo_type;
	@NotNull(groups=InsertGroup.class)
	@Min(value=1, groups=UpdateGroup.class)
	private Integer bo_no;
	@NotBlank
	private String bo_title;
	@NotBlank(groups=BoardInsertGroup.class) 
	private String bo_writer;
	@NotBlank(groups=BoardInsertGroup.class)
	private String bo_pass;
	private String bo_content;
	private String bo_date;
	private Integer bo_hit;
	private Integer bo_rec;
	private Integer bo_rep;
	private String bo_sec;
	private Integer bo_parent;
	private int startAttNo;
	
	private List<AttatchVO> attatchList; // has many 관계 
	// AttatchVO 를 받아서 리스트에 담아 DB에 저장.
	private List<Reply2VO> replyList; // has many 관계
	
	// 여러개가 지워질 수 있기 때문에 int 배열
	private int[] delAttNos;
	
}
