package kr.or.ddit.thread;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

public class PrintNumberJobGenerator implements Runnable {
	
	private ThreadPoolExecutor executor;
	
	
	@Inject
	public PrintNumberJobGenerator(ThreadPoolExecutor executor) {
		super();
		this.executor = executor;
	}



	@Override
	public void run() {
		while(true) {
//			if ((Thread.activeCount() -2 ) >= 8)
//				break;
			
			PrintNumberJob job = new PrintNumberJob();
			// CPU를 할당받은 thread
//			Thread thread = new Thread(job);
//			thread.start();
			executor.execute(job);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	
}
