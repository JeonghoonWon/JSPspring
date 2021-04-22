package kr.or.ddit.container.various;

import java.io.File;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VariousDIVO {
	private String str;
	private int number;
	private double dblNumber;
	private char ch;
	private boolean boolData;
	
	// 각기 다른것들 넣어보자
	private Resource cpr;
	private Resource fsr;
	private Resource urlr;
	
}
