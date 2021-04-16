package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.validator.constraint.TelephoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of="buyer_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerVO implements Serializable{
	private int rnum;
	@NotBlank
	private String buyer_id;
	@NotBlank
	private String buyer_name;
	@NotBlank(groups=InsertGroup.class)
	private String buyer_lgu;
	private String buyer_bank;
	private String buyer_bankno;
	private String buyer_bankname;
	private String buyer_zip;
	private String buyer_add1;
	private String buyer_add2;
	@NotBlank
	@TelephoneNumber
	private String buyer_comtel;
	@NotBlank
	@TelephoneNumber
	private String buyer_fax;
	@NotBlank
	@Email
	private String buyer_mail;
	private String buyer_charger;
	private String buyer_telext;
	private String buyer_delete;
	private String lprod_nm;
}