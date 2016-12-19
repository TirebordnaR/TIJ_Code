package interrupt;

import java.util.concurrent.locks.*;

// 在ReentranLock上阻塞的任务可以被中断,而在synchronized方法或者临界区上阻塞的任务则不能被中断
public class ReentrantLockInterrupt {

	public static void main(String[] args) throws Exception {

		// new该任务的时候是在MAIN线程,因此MAIN线程首先拿到了lock
		Runnable r = new Block2();
		Thread t = new Thread( r );
		t.interrupt();
		// 该任务启动,调用run方法,但是获取不到锁,因为MAIN线程在构造该任务的时候就拿到了锁
		// 也就是说MAIN线程拿到了锁,而该线程没有拿到锁
		t.start();

		// 简单的睡眠1s,假设在blocked.f()调用后MAIN线程才调用t.interrupt()
		Thread.sleep( 1000 );
		System.out.println( "MAIN : issuing t.interrupt()" );
		t.interrupt();
	}

}

class BlockedMutex {
	private Lock lock = new ReentrantLock();

	public BlockedMutex() {
		// 获得锁,但是不释放锁
		lock.lock();
	}

	public void f() {
		try {
			lock.lockInterruptibly(); // 可以被中断
			System.out.println( "lock acquired in f()" );
		} catch( InterruptedException e ) {
			System.out.println( "INT from lock acquisition in f()" );
		}
	}
}

class Block2 implements Runnable {

	// new这个对象时,该对象就得到了锁,但是一直没有释放
	BlockedMutex blocked = new BlockedMutex();

	@Override
	public void run() {
		System.out.println( "waiting for f() in BlockedMutex" );
		blocked.f();
		System.out.println( "Broken out of blocked call" );
	}
}
