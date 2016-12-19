package newcomponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

// 只有放入到Delay队列中的对象过了指定的时间差(getDelay返回值)才会从队列中取走
// 切勿将null放入这种队列中
public class DelayQueueDemo {
	public static void main(String[] args) throws InterruptedException {

		ExecutorService exec = Executors.newCachedThreadPool();
		Random rand = new Random( 67 );
		DelayQueue<DelayedTask> q = new DelayQueue<>();

		for( int i = 0; i < 6; i++ ) {
			q.put( new DelayedTask( rand.nextInt( 5000 ) ) );
		}
		q.put( new DelayedTask.EndSentinel( 9000, exec ) );

		exec.execute( new DelayedTaskCosumer( q ) );

		Thread.sleep( 10000 );
		System.out.println( q.size() );
	}
}

class DelayedTask implements Runnable, Delayed {

	private static int count = 0;
	private final int id = count++;

	private final int delta; // 距离任务发生的时间
	private final long triggerTime; // 任务触发的时间

	// 记录创建的顺序,用于和实际顺序做比较
	protected static List<DelayedTask> sequenceTasks = new ArrayList<>();

	// 参数是距离要发生的时间有多少Ms
	public DelayedTask(int delayInMs) {
		delta = delayInMs;
		triggerTime = System.nanoTime() + NANOSECONDS.convert( delayInMs, MILLISECONDS );
		sequenceTasks.add( this );
	}

	@Override
	public void run() {
		System.out.println( this );
	}

	// 返回值：距离任务触发还有多少ns,一旦过了指定的时间差,就会从队列中取出某个元素
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert( triggerTime - System.nanoTime(), NANOSECONDS );
	}

	@Override
	// 重写Delay的父接口的compareTo方法,便于Delay接口的getDelay方法判断
	public int compareTo(Delayed thatT) {
		DelayedTask that = (DelayedTask) thatT;
		if ( this.triggerTime < that.triggerTime )
			return -1;
		if ( this.triggerTime > that.triggerTime )
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		return "Task " + id + " 创建任务到触发任务的时间间隔(ms)：" + delta;
	}

	// 惯用方法,必须确保该任务是队列中最后一个元素,好对前面的任务进行管理
	public static class EndSentinel extends DelayedTask {

		private ExecutorService exec;

		public EndSentinel(int delayInMs, ExecutorService exec) {
			super( delayInMs );
			this.exec = exec;
		}

		@Override
		public void run() {
			for( DelayedTask t : sequenceTasks )
				System.out.println( "创建队列顺序：" + t );
			System.out.println( "Call shutdownNow() " + sequenceTasks.size() );
			exec.shutdownNow();
		}
	}
}

class DelayedTaskCosumer implements Runnable {

	private DelayQueue<DelayedTask> q;

	public DelayedTaskCosumer(DelayQueue<DelayedTask> q) {
		this.q = q;
	}

	@Override
	public void run() {
		try {
			// 仅仅是调用run方法,并没有启动线程
			// 按照Delay Comparable接口中的compareTo/getDelay来判断,getDelay最小的先执行
			while( !Thread.interrupted() )
				q.take().run(); // 从取出队列头元素,如果时间没有到,那么就等待
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
		System.out.println( "end run() in comsumer" );
	}

}