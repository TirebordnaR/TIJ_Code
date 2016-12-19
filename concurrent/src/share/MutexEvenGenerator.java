package share;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 使用Lock对象来同步线程
public class MutexEvenGenerator extends IntGenerator {

	private int value = 0;
	private Lock lock = new ReentrantLock();

	@Override
	public int next() {
		// lock尝试获取锁,如果获取不到,就休眠,直到获取到锁
		lock.lock();
		try {
			++value;
			Thread.yield();
			++value;
			return value;
		} finally {
			// 返回值在try语句块,而unlock在finally语句块
			// 因为return语句之后一般不能加上别的语句,所以采用try-finally语句来实现
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		EvenChecker.test( new MutexEvenGenerator() );
	}
}
