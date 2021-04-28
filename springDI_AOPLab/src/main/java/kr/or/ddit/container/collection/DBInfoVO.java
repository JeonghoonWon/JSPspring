package kr.or.ddit.container.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // setter 주입도 가능
@AllArgsConstructor // 이것만 쓰면 생성자 주입만 가능
public class DBInfoVO {
	private String driverClassName;
	private String url;
	private String user;
	private String password;
	private long maxWait;
	
}
