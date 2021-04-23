package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.validator.InsertGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(of="prod_id")
@ToString(of= {"prod_id", "prod_name", "prod_lgu"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdVO implements Serializable {
	private int rnum;
	@NotBlank
	private String prod_id;
	@NotBlank
	private String prod_name;
	@NotBlank
	private String prod_lgu;
	private String lprod_nm;
	@NotBlank
	private String prod_buyer;
	@NotNull
	@Min(0)
	private Integer prod_cost;
	@Min(0)
	private Integer prod_price;
	@Min(0)
	private Integer prod_sale;
	private String prod_outline;
	private String prod_detail;
	@NotBlank(groups=InsertGroup.class)
	private String prod_img;
	@Min(0)
	private Integer prod_totalstock;
	private String prod_insdate;
	@Min(0)
	private Integer prod_properstock;
	private String prod_size;
	private String prod_color;
	private String prod_delivery;
	@Min(0)
	private String prod_unit;
	@Min(0)
	private Integer prod_qtyin;
	@Min(0)
	private Integer prod_qtysale;
	private Integer prod_mileage;
	
	private BuyerVO buyer; // has a(1:1) 관계	
	
	private Set<MemberVO> userList;	// has many 관계
}







