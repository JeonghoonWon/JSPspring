package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

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
public class BoardVO implements Serializable{
	private Integer bo_sort;
	private String bo_type;
	private Integer bo_no;
	private String bo_title;
	private String bo_writer;
	private String bo_pass;
	private String bo_content;
	private String bo_date;
	private Integer bo_hit;
	private Integer bo_rec;
	private Integer bo_rep;
	private String bo_seq;
	private Integer bo_parent;
	
	private List<AttatchVO> attatchList; // has many 관계 
	// AttatchVO 를 받아서 리스트에 담아 DB에 저장.
	private List<Reply2VO> replyList; // has many 관계
	
}
