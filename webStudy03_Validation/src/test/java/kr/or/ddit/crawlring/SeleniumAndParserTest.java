package kr.or.ddit.crawlring;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class SeleniumAndParserTest {

	@Test
	public void test() {
		System.setProperty("webdriver.chrome.driver","d:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.naver.com");
		try {
			Thread.sleep(6000);
			// 6초 딜레이시켜서 정보 가져온다. 처음 naver를 켜는 시간이 걸리기 때문에.
			
			String source = driver.getPageSource();
//			System.out.println(source);
			
			Document dom = Jsoup.parse(source);
			
//			dom.select("#themecast"); 아이디를 가져오는 거기때문에 select 보단 getElment 사용하는 게좋다.
			Element themecast = dom.getElementById("themecast");
			Elements elements = themecast.select(".title");
			Element title = elements.get(0);
			System.out.println(title);
			
			
			// 크롤링 종료시키기
			driver.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
