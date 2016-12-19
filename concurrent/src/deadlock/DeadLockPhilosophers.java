package deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 调整哲学家人数以及factor可以得出死锁的情况
// 哲学家人数很少,factor很小可以很容易出现死锁
public class DeadLockPhilosophers {

	public static void main(String[] args) throws InterruptedException {

		int phoSize = 2; // 哲学家人数 = 筷子总数
		int choSize = phoSize; // 筷子总数
		int factor = 0; // 影响因子,值越大,那么哲学家吃饭和思考需要的时间越大(统计上来说)

		Philosopher[] phos = new Philosopher[phoSize];
		Chopstick[] chops = new Chopstick[choSize];

		for( int i = 0; i < choSize; i++ ) {
			chops[i] = new Chopstick();
		}

		for( int i = 0; i < phoSize; i++ ) {
			// 每个哲学家先拿右边的筷子,再拿左边的筷子
			phos[i] = new Philosopher( chops[i], chops[(i + 1) % choSize], i, factor );
		}

		ExecutorService exec = Executors.newCachedThreadPool();
		for( int i = 0; i < phoSize; i++ ) {
			exec.execute( phos[i] );
		}

		Thread.sleep( 500000 );
		exec.shutdownNow();
	}
}
