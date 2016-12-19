package active.object;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActiveObjectDemo {

	// 使用了SingleThread,每次只有一个任务在执行,后续的任务则等待
	private ExecutorService exec = Executors.newSingleThreadExecutor();
	private Random rand = new Random( 67 );

	private void pause(int factor) {
		try {
			Thread.sleep( 100 + rand.nextInt( factor ) );
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
	}

	public Future<Integer> calculateInt(final int x, final int y) {
		return exec.submit( new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println( "开始: " + x + " + " + y );
				pause( 2000 );
				return x + y;
			}
		} );
	}

	public Future<Float> calculateFloat(final float x, final float y) {
		return exec.submit( new Callable<Float>() {
			@Override
			public Float call() throws Exception {
				System.out.println( "开始: " + x + " + " + y );
				pause( 2000 );
				return x + y;
			}
		} );
	}

	public void shutdown() {
		exec.shutdown();
	}

	public static void main(String[] args) {

		ActiveObjectDemo ad = new ActiveObjectDemo();
		// 使用CopyOnWrite类型的List方便后面的移除
		List<Future<?>> rs = new CopyOnWriteArrayList<>();

		for( int i = 0; i < 10; i++ ) {
			rs.add( ad.calculateInt( i, i ) );
		}

		for( float f = 0.0f; f < 5.0f; f += 0.2f ) {
			rs.add( ad.calculateFloat( f, f ) );
		}

		System.out.println( "所有异步调用开始" );

		// 不断的轮询有没有完成的任务,有的话就取得其返回值,并移除该任务
		while( rs.size() > 0 ) {
			for( Future<?> f : rs ) {
				if ( f.isDone() ) {
					try {
						System.out.println( f.get() );
					} catch( InterruptedException | ExecutionException e ) {
						e.printStackTrace();
					}
					rs.remove( f );
				}
			}
		}

		ad.shutdown();
	}
}
