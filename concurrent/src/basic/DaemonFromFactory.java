package basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 结合产生线程的工厂来创建后台线程
public class DaemonFromFactory implements Runnable {
	@Override
	public void run() {
		try {
			while( true ) {
				TimeUnit.SECONDS.sleep( 1 );
				System.out.println( Thread.currentThread() );
			}
		} catch( InterruptedException e ) {
			System.out.println( "Interrupted..." );
		}
	}

	public static void main(String[] args) throws InterruptedException {

		ExecutorService exec = Executors.newCachedThreadPool( new DaemonThreadFactory() );
		for( int i = 0; i < 4; i++ ) {
			Runnable r = new DaemonFromFactory();
			exec.execute( r );
		}

		System.out.println( "All daemons started" );
		TimeUnit.SECONDS.sleep( 4 );
		TimeUnit.MILLISECONDS.sleep( 200 );
		System.out.println( "Main Thread is going die..." );
	}
}
