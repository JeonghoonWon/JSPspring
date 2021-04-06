package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@EqualsAndHashCode(of= {"prod_id"})
@ToString(of= {"prod_id", "prod_name", "prod_lgu"})
public class ProdVO implements Serializable {
	private String prod_id;
	private String prod_name;
	private String prod_lgu;
	private String lprod_nm;
	private String prod_buyer;
	private int prod_cost;
	private int prod_price;
	private int prod_sale;
	private String prod_outline;
	private String prod_detail;
	private String prod_img;
	private int prod_totalstock;
	private String prod_insdate;
	private int prod_properstock;
	private String prod_size;
	private String prod_color;
	private String prod_delivery;
	private String prod_unit;
	private int prod_qtyin;
	private int prod_qtysale;
	private int prod_mileage;
	
	private BuyerVO buyer;	// has a 관계 : 테이블이 1대1 관계
	

}