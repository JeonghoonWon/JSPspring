package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import kr.or.ddit.validator.DeleteGroup;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.validator.constraint.TelephoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 관리를 위한 Domain Layer (Java Bean 규약)
 * 
 * Data Mapper(SQL Mapper)를 이용한 다중 테이블 조인 방법 ex) 한명의 회원과 그동안 구매한 상품 목록을 함께 조회.
 * 
 * 1. 가장 먼저 해야할 것 : 메인 데이터를 가진 메인 테이블을 식별 (Member) 2. join에 대상이 되는 테이블로 부터 조회된
 * 데이터를 바인딩 할 VO 설계 (MemberVO, ProdVO) 3. 테이블 사이의 관계를 VO 사이의 관계로 구조화. 관계를 파악할때
 * 1을 기준으로 1:N - has many (MemberVO has many ProdVO) 1:1 - has a (ProdVO has a
 * BuyerVO) 4. resultType 을 대신해서 resultMap 으로 수동 관계 1:N - collection 엘리먼트 1:1 -
 * association 엘리먼트 (Member.xml -> memberMap)
 * 
 *
 */
// 필요한 getter/setter/equals /tostring 있을때 annotation 사용
//@Getter
//@Setter
//@EqualsAndHashCode(of= {"mem_id","mem_regno1","mem_regno2"}) // id가 같으면 이란 조건 생성
//@ToString(exclude= {"mem_pass","mem_regno1","mem_regno2"}) //exclude : 제외하고 표시
@Data // java bean 규약 에 맞는 getter/setter/equals/tostring 불러올 수 있음.
@NoArgsConstructor // 기본 생성자 자동생성
@AllArgsConstructor // 가지고있는 생성자 모두 불러올때
public class MemberVO implements Serializable {
//	public MemberVO() {
//		super();
//	}
	public MemberVO(String mem_id, String mem_pass) {
		super();
		this.mem_id = mem_id;
		this.mem_pass = mem_pass;
	}

	// rownum을 받을 변수 생성
	private int rnum;
	// mem_id 는 등록할때만 사용하니까 InsertGroup 을 등록해준다.
	@NotBlank(groups= {InsertGroup.class, DeleteGroup.class}, message="아이디 필수")
	private String mem_id;
	// message 를 하드코딩 하지않고 properties에 작성한 key 를 가지고 온다.
	@NotBlank(message="{NotBlank.kr.or.ddit.vo.MemberVO.mem_pass.message}")
	@Size(min=4, max=8, message="{Size.kr.or.ddit.vo.MemberVO.mem_pass.message}")
	private String mem_pass;
	// 현재 등록시에만 가능
	@NotBlank(groups=InsertGroup.class)
	private String mem_name;
	@NotBlank(groups=InsertGroup.class)
	private String mem_regno1;
	@NotBlank(groups=InsertGroup.class)
	private String mem_regno2;
	private String mem_bir;
	@NotBlank
	private String mem_zip;
	@NotBlank
	private String mem_add1;
	@NotBlank
	private String mem_add2;
	@TelephoneNumber
	private String mem_hometel;
	@TelephoneNumber
	private String mem_comtel;
	@TelephoneNumber
	private String mem_hp;
	@NotBlank
	@Email
	private String mem_mail;
	private String mem_job;
	private String mem_like;
	private String mem_memorial;
	private String mem_memorialday;
	// 검증 annotation 을 쓰더라도 타입에 따라 쓸수있는게있고 아닌게 있다.
	private Integer mem_mileage;
	private String mem_delete;

	private Set<ProdVO> prodList; // has many(1:N) 관계 생성
	
	private String mem_role;

}
