package kr.or.ddit.thread.timer;

import java.util.Timer;

public class TimerTaskTest {
	public static void main(String[] args) {
		PrintNumberTimerTask task = new PrintNumberTimerTask();
		
		Timer timer = new Timer();
		timer.schedule(task, 0, 1000); // task 주고, 바로시작, 1초마다 한번씩
	}
}
