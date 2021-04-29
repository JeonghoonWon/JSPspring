package kr.or.ddit.thread;

public class PrintNumberJob implements Runnable{// thread에 의해 실행됨

	// 1씩증가 전역변수
	private int number;
	
		
	@Override
	public void run() {
		while (number < 100) {// of %d : 현재 실행중인 thread 갯수
			System.out.printf("%d - %s(of %d)\n",++number, Thread.currentThread().getName(), Thread.activeCount()); // 전위연산자로 ++
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}  	
	
}
