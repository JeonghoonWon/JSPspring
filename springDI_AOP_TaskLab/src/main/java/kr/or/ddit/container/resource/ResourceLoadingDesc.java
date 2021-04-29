package kr.or.ddit.container.resource;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class ResourceLoadingDesc {
	public static void main(String[] args) throws IOException {
		Resource cpr = new ClassPathResource("log4j2.xml"); //ClassPathResource 는 classpath 를 가지고 있기떄문에 경로를 삭제해준다.
		System.out.println(cpr.getFile().exists()); //log4j2.xml 파일을 찾고 존재를 증명하기 : true
		
		Resource fsr = new FileSystemResource("d:/contents/오래된 노래_utf8.txt");
		System.out.println(fsr.exists()); // true
		UrlResource urlr = new UrlResource("https://www.google.com/logos/doodles/2021/earth-day-2021-6753651837108909-vacta.gif");
		System.out.println(urlr.contentLength());
	
		ConfigurableApplicationContext container = 
				new ClassPathXmlApplicationContext();

		
		// ***container 자체가 resourceloader 역할을 가지고 있기 때문에 바로 사용 가능하다.
		cpr = container.getResource("classpath:log4j2.xml"); // 다양한 형태의 location 을 줄 수 있다. ex) log4j2.xml 를 찾아보자. 앞에 classpath 만 붙여주면 된다.
		System.out.println(cpr); 
		
		fsr = container.getResource("file://d:/contents/오래된 노래_utf8.txt"); // file 의 경로는 file: 로 처리. 실제 경로 구분은 슬러시2개(//) 넣어준다. 
		System.out.println(fsr);
	
		urlr = (UrlResource) container.getResource("https://www.google.com/logos/doodles/2021/earth-day-2021-6753651837108909-vacta.gif");
		System.out.println(urlr);
		
		// 결론 : 구현체의 종류를 알 필요가 없다. Resource 로 통일해둔다면 모든걸 구분할 수 있다.
		
		
	
	}
}
