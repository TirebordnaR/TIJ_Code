package terminate.garden;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 大门
public class Entrance implements Runnable {

	// 通过所有大门的总人数
	private static Counter count = new Counter();
	// 记录下所有的大门
	private static List<Entrance> entrances = new ArrayList<Entrance>();

	// 每个大门的计数器值
	private int eachCount = 0;
	// 大门标志,每个大门的id都不一样
	private final int id;

	// 是否取消所有大门的计数过程
	// 由于对canceled只有赋值和读取操作,因此表示为volatile就可以实现同步互斥
	private static volatile boolean canceled = false;

	public Entrance(int id) {
		this.id = id;
		// 记录下每个大门的计数器,避免线程终止时,被垃圾回收掉
		entrances.add( this );
	}

	public static void cancele() {
		canceled = true;
	}

	@Override
	public void run() {
		while( !canceled ) {
			++eachCount;
			System.out.println( this + "--> TOTAL COUNT:" + count.increment() );
			try {
				TimeUnit.MILLISECONDS.sleep( 1 );
			} catch( InterruptedException e ) {
				System.out.println( "sleep() was interrupted..." );
			}
		}
		System.out.println( "Stopping..." + this );
	}

	@Override
	public String toString() {
		return "大门:" + id + "\t计数值:" + this.getCount();
	}

	// 返回该大门的计数器值
	public int getCount() {
		return this.eachCount;
	}

	// 直接返回记录总人数的计数器值
	public static int getToatalCount() {
		return count.getCount();
	}

	// 把每个大门的计数器值相加得到通过所有大门的总人数
	public static int sumAllEntrances() {
		int sum = 0;
		for( Entrance e : entrances ) {
			sum += e.getCount();
		}
		return sum;
	}
}
