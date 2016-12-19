package share;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadLocal implements Runnable {
	private final int id;

	public SimpleThreadLocal(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		// 结合shutdownNow()来终止线程
		while( !Thread.currentThread().isInterrupted() ) {
			ThreadLocalVarHolder.increment();
			System.out.println( id + "-->" + ThreadLocalVarHolder.get() );
			Thread.yield();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for( int i = 0; i < 4; i++ ) {
			exec.execute( new SimpleThreadLocal( i ) );
		}

		Thread.sleep( 3 );
		exec.shutdownNow(); // 强制产生interrupt
	}
}

// 线程本地存储：ThreadLocal value对象
class ThreadLocalVarHolder {

	private static int initValue = 0;
	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
		// 设置一个ThreadLocal对象(value)的初始值,synchronized保证初始值都不一样
		protected synchronized Integer initialValue() {
			return initValue++;
		}
	};

	public static void increment() {
		value.set( value.get() + 1 );
	}

	public static int get() {
		return value.get();
	}

}
