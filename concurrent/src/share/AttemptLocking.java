package share;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {
	private ReentrantLock lock = new ReentrantLock();

	public void untimed() {
		// tryLock()尝试获取锁,获取不到就返回
		boolean captured = lock.tryLock();
		try {
			System.out.println( "tryLock() " + captured );
		} finally {
			if ( captured )
				lock.unlock();
		}
	}

	public void timed() {
		// tryLock(xx)尝试在xx秒内获取锁,如果指定已经到达时间,仍然获取不到就返回
		boolean captured = false;
		try {
			captured = lock.tryLock( 2, TimeUnit.SECONDS );
		} catch( InterruptedException e ) {
			throw new RuntimeException();
		}

		try {
			System.out.println( "tryLock( 2, TimeUnit.SECONDS ) " + captured );
		} finally {
			if ( captured )
				lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		AttemptLocking al = new AttemptLocking();
		al.untimed();
		al.timed();

		System.out.println( "other thread join...." );

		new Thread() {
			@Override
			public void run() {
				al.lock.lock();
				System.out.println( "another thread lock...but NOT release lock!" );
			}
		}.start();

		// 粗略的确保另外一个线程启动起来
		Thread.sleep( 1000 );
		al.untimed();
		al.timed();
	}
}
