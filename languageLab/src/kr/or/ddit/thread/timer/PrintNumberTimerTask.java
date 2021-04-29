package kr.or.ddit.thread.timer;

import java.util.TimerTask;

public class PrintNumberTimerTask extends TimerTask{
	
	private int number;
	
	@Override
	public void run() {
		if(number == 100) cancel(); // ancel(); : 이미 스케쥴링 되어있는 작업 취소 .
		System.out.printf("%d - %s(of %d)\n",++number, Thread.currentThread().getName(), Thread.activeCount()); // 전위연산자로 ++
		
	}

}
