package deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 当以下四个条件同时满足时,会发生死锁
// ①：有互斥条件,任务使用的资源中至少有一个不能被共享
// ②：至少有一个任务持有一个资源A,且正在等待一个别的任务持有的资源B
// ③：资源不能被任务抢占
// ④：必须有循环等待,A任务等待B任务的持有资源,B任务等待C任务持有的资源.... N任务等待A任务持有的资源
// 想要防止死锁,只需要破坏这4个条件中至少一个,一般来说破坏④比较容易
public class FixDeadLockPhilosophers {

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
			// 每个哲学家先拿右边的筷子,再拿左边的筷子,这可能会导致死锁
			// 可以打破循环等待,比如最后一个哲学家不按照先拿右边再拿左边筷子的规则来,改为先拿左边再拿右边
			if ( i != phoSize - 1 )
				phos[i] = new Philosopher( chops[i], chops[(i + 1) % choSize], i, factor );
			else
				phos[i] = new Philosopher( chops[(i + 1) % choSize], chops[i], i, factor );
		}

		ExecutorService exec = Executors.newCachedThreadPool();
		for( int i = 0; i < phoSize; i++ ) {
			exec.execute( phos[i] );
		}

		Thread.sleep( 500000 );
		exec.shutdownNow();
	}
}
