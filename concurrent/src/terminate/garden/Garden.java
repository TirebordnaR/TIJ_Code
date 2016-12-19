package terminate.garden;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Garden {
	public static void main(String[] args) throws InterruptedException {

		ExecutorService exec = Executors.newCachedThreadPool();
		for( int i = 0; i < 4; i++ ) {
			exec.execute( new Entrance( i ) );
		}

		// 让每个大门都运行一会儿
		TimeUnit.SECONDS.sleep( 2 );

		// 停止计数
		Entrance.cancele();

		exec.shutdown();
		// awaitTermination等待每个任务结束,如果所有的任务都在超时时间到达之前全部结束,则返回true
		// 如果有某些任务还在运行,返回false
		if ( !exec.awaitTermination( 250, TimeUnit.MILLISECONDS ) )
			System.out.println( "Some task are NOT terminated!" );

		System.out.println( "Total : " + Entrance.getToatalCount() );
		System.out.println( "Sum : " + Entrance.sumAllEntrances() );
	}
}
