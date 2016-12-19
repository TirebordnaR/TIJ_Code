package share;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {

	private IntGenerator g; // 数字生成器
	private int id; // 线程id

	public EvenChecker(IntGenerator g, int id) {
		this.g = g;
		this.id = id;
	}

	// 测试IntGenerator的next()是否返回的是偶数
	@Override
	public void run() {
		while( !g.isCanceled() ) {
			int val = g.next();
			if ( val % 2 != 0 ) {
				System.out.println( val + " not even !" + id );
				g.cancel();
			}
		}
	}

	public static void test(IntGenerator g, int threadCount) {

		ExecutorService exec = Executors.newCachedThreadPool();
		for( int i = 0; i < threadCount; i++ ) {
			exec.execute( new EvenChecker( g, i ) );
		}

		// shutdown并不是终止线程,仅仅是发出一个中断线程的信号
		// 而且只能interrupt那些目前没有任务，处于等待状态从blockingQueue获取任务的thread
		// 而不能interrupt那些在任务执行过程中的thread,或者是任务执行过程中挂起的thread
		exec.shutdown();

	}

	// 默认开启10个EvenChecker线程测试
	public static void test(IntGenerator g) {
		test( g, 10 );
	}
}
