package kr.or.ddit.task;

import java.util.TimerTask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PrintNumberTimerTask {
	
	private int number;
	
//	@Scheduled(initialDelay = 0, fixedRate = 1000 )  // fixedDelay: 작업이 종료되는시점을 기준으로 다음 진행이 될것인지 정하는것. fixedRate: 1초 씩 진행
	// @Scheduled(cron= "10 * * * * *") 
	// @Scheduled(cron= "10 * * * * *") : 매 10초 마다, 
	// @Scheduled(cron= "10 10 * * * *") : 매시간  10초 마다, 
	// @Scheduled(cron= "10 10 3 * * *") : 3시 10분 10초 마다, 
	// @Scheduled(cron= "10 10 3 1 * MON") : 3시 10분 10초 마다, 
	// @Scheduled(cron= "10 10 3 ? * MON") : 일은 고려하지않고 매주 월요일 3시 10분 10초
	// @Scheduled(cron= "10 10 23 ? * MON-FRI") : 일은 고려하지않고 매주 월요일부터 금요일 11시 10분 10초에
	// @Scheduled(cron= "10 10 * ? * MON-FRI") : 일은 고려하지않고 매주 월요일부터 금요일 매시간 10분 10초에
	
	//@Scheduled(cron= "*/5 * * ? * MON-FRI")   // 매주 월요일부터 금요일 까지 오초간격으로
	@Scheduled(cron= "0 0 3 ? * MON") // 월요일 새벽 3시에 수행 
	public void sfasdf() {

		System.out.printf("%d - %s(of %d)\n",++number, Thread.currentThread().getName(), Thread.activeCount()); // 전위연산자로 ++
		
	}

}
