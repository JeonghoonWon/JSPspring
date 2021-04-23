package kr.or.ddit.container.collection;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor   // 기본생성자로 주입 가능.
@AllArgsConstructor
public class CollectionDIVO {
	// 5가지 주입 방법 알아보자
	// data annotation 으로 setter 생성
	private List<String> list;
	private String[] array;
	private Set<?> set;
	private Map<String, ?> map;
	private Properties props;
	
	
}
