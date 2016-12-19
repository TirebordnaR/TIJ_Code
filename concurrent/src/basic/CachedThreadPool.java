package basic;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {

	// 线程池,只要有可能都会被复用
	public static void main(String[] args) {

		ExecutorService exec = Executors.newCachedThreadPool();

		// 一次性支付10个线程的代价,newFixedThreadPool的参数限制了最大的活动线程的数量
		// 如果多余10个线程,那么多余的线程将会进行排队
		exec = Executors.newFixedThreadPool( 10 );

		// 等同于newFixedThreadPool(1)
		exec = Executors.newSingleThreadExecutor();

		exec = Executors.newCachedThreadPool();
		for( int i = 0; i < 5; i++ ) {
			exec.execute( new Task( i ) );
		}
	}
}

class Task implements Runnable {

	int id;

	public Task(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		try {
			Thread.sleep( 1000 );
			System.out.println( "Task" + id );
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
	}
}
