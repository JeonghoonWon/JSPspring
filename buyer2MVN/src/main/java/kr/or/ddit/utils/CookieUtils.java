package kr.or.ddit.utils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {
	private Map<String, Cookie> cookieMap;
	private HttpServletRequest req;//req를 사용해야만 이 안에 있는 것들을 사용할 수 있음
	//CookieUtils는 request에 의존해야하는 상황이 생김
	//req를 내가 생성하는 것이 아닌 받아와야 한다. => 전략패턴
	//어떻게 이 것에 전략패턴을 받을것인지?? 캡슐화가 되어있기때문에 다이렉트로 받기는 힘들다.
	//그래서 setter를 사용하거나 생성자를 통해서 주입을 받는다.
	//선택이 기준은 이 전략이 필수전략이냐 아니냐(옵션전략) 따라 나뉜다.
	
	//생성자를 새로 만듬으로써 기본생성자가 사라지고, req를 무조건 사용해야 한다. => 필수적이다.
	public CookieUtils(HttpServletRequest req) {
		super();
		this.req = req;
		cookieMap = new LinkedHashMap<>();
		Cookie[] cookies = req.getCookies();
		//Cookie가 존재하지 않을 수도 있기때문에 if문이 필요, 사용자가 쿠키를 허락하지 않을 수도 있음
		if(cookies!=null) {
			for(Cookie tmp : cookies) {
				cookieMap.put(tmp.getName(), tmp);
			}
		}
	}//CookieUtils생성자 끝
	
	/**
	 * 쿠키 검색		
	 * @param name 검색할 쿠키명
	 * @return 존재하지 않는 경우 null 반환.
	 */
	//document를 볼때는 파라미터와 리턴명이 중요!!
	public Cookie getCookie(String name) {//cookie중에 name을 검색해서 cookie를 가져올려고 한다.
		return cookieMap.get(name);
	}
	
	/**
	 * 쿠키값 조회
	 * @param name
	 * @return 존재하지 않으면 null반환.
	 * @throws IOException
	 */
	public String getCookieValue(String name) throws IOException {
		Cookie cookie = getCookie(name);
		//값이 없는 경우 에러가 발생
		String value = null;
		if(cookie!=null) {
			value =  URLDecoder.decode(cookie.getValue(), "UTF-8");
			//IOException의 하위 에러 UnsupportedEncodingException이다.
			//encoding형식이 존재하지 않을 때 존재하는 에러이다.
		}
		return value;
	}
	
	public boolean exists(String name){//name값에 해당하는 쿠키의 존재여부를 확인하기 위한것
		return cookieMap.containsKey(name);
	}
}//CookieUtils class 끝