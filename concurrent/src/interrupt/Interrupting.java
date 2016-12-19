package interrupt;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// 如果只中断某个单一的任务,可以考虑调用submit()方法来启动任务,该方法会返回Future<?>
// 显然无法调用Future的get()方法,因为?是什么都不知道
// 在返回的Future<?> f上调用cancel(true)就可以单独的中断某个任务(前提是该任务可以被中断)
// 一般来说,sleep调用可以被中断,IO阻塞或者synchronized方法调用导致的阻塞是无法被中断的
// NIO或者使用ReentrantLock上阻塞的任务可以被中断
public class Interrupting {

	private static ExecutorService exec = Executors.newCachedThreadPool();

	public static void test(Runnable r) throws InterruptedException {

		// 通过submit一个Runnable任务,可以单独对该任务进行控制(是否可以中断该任务等)
		// 使用execute则是对很多任务一起控制
		Future<?> f = exec.submit( r );
		TimeUnit.MICROSECONDS.sleep( 100 );

		System.out.println( "[MAIN]: interrupting " + r.getClass().getName() );
		// 中断exec.submit(r)的r任务
		f.cancel( true );
		System.out.println( "[MAIN]: interuupt send to " + r.getClass().getName() );
	}

	public static void main(String[] args) throws InterruptedException {
		test( new SleepBlocked() );
		test( new IOBlocked( System.in ) );
		test( new SynchronizedBlocked() );

		TimeUnit.SECONDS.sleep( 3 );
		System.out.println( "Aborting with System.exit(0)" );
		System.exit( 0 ); // 强行终止JAVA虚拟机的运行
	}
}

// 因为sleep调用而进入的阻塞可以被中断
class SleepBlocked implements Runnable {
	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep( 10 );
		} catch( InterruptedException e ) {
			System.out.println( "[SleepBlocked]: InterruptedException..." );
		}
		System.out.println( "[SleepBlocked]: exiting SleepBlocked run()..." );
	}
}

// 由IO引起的阻塞一般来说是不可以被中断的
class IOBlocked implements Runnable {
	private InputStream in;

	public IOBlocked(InputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		try {
			System.out.println( "waiting for read..." );
			in.read();
		} catch( IOException e ) {
			if ( Thread.currentThread().isInterrupted() )
				System.out.println( "Interrupted from blocked I/O" );
			else {
				System.out.println( "Intertupt are NOT from blocked I/O" );
				throw new RuntimeException();
			}
		}
		System.out.println( "exiting IOBlocked run()" );
	}
}

// 由于调用synchronized方法上而导致阻塞(获取不到锁)也是不可以被中断的
class SynchronizedBlocked implements Runnable {

	public synchronized void f() {
	}

	public SynchronizedBlocked() {
		new Thread() {
			@Override
			public void run() {
				f();
			}
		}.start();
	}

	@Override
	public void run() {
		System.out.println( "Trying to call f()" );
		f();
		System.out.println( "Exiting SynchronizedBlocked.run()" );
	}
}