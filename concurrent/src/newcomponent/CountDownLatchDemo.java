package newcomponent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {

	public static void main(String[] args) {

		// 前期要完成的任务数量
		int PRE_TASK_SIZE = 30;
		// 等待前期完成的任务后的任务
		int WAIT_TASK_SIZE = 3;

		ExecutorService exec = Executors.newCachedThreadPool();

		CountDownLatch latch = new CountDownLatch( PRE_TASK_SIZE );
		for( int i = 0; i < WAIT_TASK_SIZE; i++ ) {
			exec.execute( new WaitingTask( latch ) );
		}
		for( int i = 0; i < PRE_TASK_SIZE; i++ ) {
			exec.execute( new TaskPortion( latch ) );
		}
		
		System.out.println( "launched all task" );
		exec.shutdown();
	}
}

// 所有任务被分成2部分A和B,A必须在B之前完成,下面的代码是任务A
class TaskPortion implements Runnable {

	private static int count = 0;
	private final int id = count++;
	private final CountDownLatch latch;

	// Random类是线程安全的,但是由于线程之间存在竞争,因此效率可能会变低
	// 可以考虑使用ThreadLocalRandom
	private static Random rand = new Random( 67 );

	public TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			doSomeWork();
			latch.countDown();
		} catch( InterruptedException e ) {
			System.out.println( "interrupted" );
			// 有必要清理资源的话可以在这里清理
		}

	}

	public void doSomeWork() throws InterruptedException {
		// 随机睡眠以下模拟做了一些事情
		Thread.sleep( rand.nextInt( 2000 ) );
		System.out.println( "First " + id + "-->completed some work" );
	}
}

class WaitingTask implements Runnable {

	private static int count = 0;
	private final int id = count++;
	private final CountDownLatch latch;

	private static Random rand = new Random( 67 );

	public WaitingTask(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			latch.await();// 在这里阻塞,直到latch的值减少到0
			doAnotherWork();
		} catch( InterruptedException e ) {
			System.out.println( "interrupted" );
			// 有必要清理资源的话可以在这里清理
		}

	}

	public void doAnotherWork() throws InterruptedException {
		// 随机睡眠以下模拟做了一些事情
		Thread.sleep( rand.nextInt( 2000 ) );
		System.out.println( "Second: " + id + "-->completed another work" );
	}
}
