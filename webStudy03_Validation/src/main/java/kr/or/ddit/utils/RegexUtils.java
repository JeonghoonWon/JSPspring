package kr.or.ddit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	//static 처리 
	public static String filteringTokens(String origin,String replace, String...tokens) {
//		"말미잘", "해삼", "멍게"
//		String regex = "(말미잘|해삼|멍게)"  // 3개중 하나라도 포함되어있으면 | 를 사용. 
									// 괄호를 사용해서 하나로 묶어준다.
		// 정규표헌식 하나가 만들어진것.
		String regex = String.format("(%s)", String.join("|", tokens));
		Pattern pattern = Pattern.compile(regex); // 패턴 객체 생성된 것.
		Matcher matcher = pattern.matcher(origin);
		// 새로운 문자열 을 만들어줄 스트링 버퍼 생성
		StringBuffer result = new StringBuffer();
		while(matcher.find()) {
			matcher.appendReplacement(result, replace); // 들어가지말아야할 문장에 AAA 로 변경
		}
		// 들어가지 말아야할 글자 뒷부분을 붙여주는 부분
		matcher.appendTail(result);
		return result.toString();
	}
	
	
	public static String filteringTokens(String origin,char maskingCh, String...tokens) {
//		"말미잘", "해삼", "멍게"
//		String regex = "(말미잘|해삼|멍게)"  // 3개중 하나라도 포함되어있으면 | 를 사용. 
									// 괄호를 사용해서 하나로 묶어준다.
		// 정규표헌식 하나가 만들어진것.
		String regex = String.format("(%s)", String.join("|", tokens));
		Pattern pattern = Pattern.compile(regex); // 패턴 객체 생성된 것.
		Matcher matcher = pattern.matcher(origin);
		// 새로운 문자열 을 만들어줄 스트링 버퍼 생성
		StringBuffer result = new StringBuffer();
		while(matcher.find()) {
			String replace = 
					matcher.group(1).replaceAll(".", new Character(maskingCh).toString()); 
			// 그룹을 찾을 수 있도록 번호를 알려준다. 그 안에서 돌도록처리
			// "." : 아무 문자.

			matcher.appendReplacement(result, replace); // 들어가지말아야할 문장에 AAA 로 변경
		}
		// 들어가지 말아야할 글자 뒷부분을 붙여주는 부분
		matcher.appendTail(result);
		return result.toString();
	}
}
