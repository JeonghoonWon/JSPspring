package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author PC-19
 *
 */
@Data // java bean 규약 에 맞는 getter/setter/equals/tostring 불러올 수 있음.
@EqualsAndHashCode(of="rep_no")
@NoArgsConstructor // 기본 생성자 자동생성
@AllArgsConstructor // 가지고있는 생성자 모두 불러올때
public class Reply2VO implements Serializable {
	private Integer bo_no;
	private Integer rep_no;
	private String rep_writer;
	private String rep_pass;
	private String rep_content;
	private String rep_date;
}
