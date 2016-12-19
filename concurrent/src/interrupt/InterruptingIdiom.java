package interrupt;

import java.util.concurrent.TimeUnit;

public class InterruptingIdiom {
	public static void main(String[] args) throws InterruptedException {

		Thread t = new Thread( new Blocked() );
		t.start();

		// 调整时间,可以在t线程的不同地方被中断,而t线程会通过InterruptedException来处理
		// 因此t线程要注意资源的清理
		Thread.sleep( 1222 );
		t.interrupt();
	}
}

// 模拟需要清理的资源
class NeedsCleanup {
	private final int id;

	public NeedsCleanup(int id) {
		this.id = id;
		System.out.println( "NeedsCleanup " + id );
	}

	public void cleanup() {
		System.out.println( "Cleaning up " + id );
	}
}

// 线程如果经过异常离开循环时,要特别注意资源的清理
class Blocked implements Runnable {
	private double d = 0.0;

	@Override
	// run方法有2条路径结束
	// ①:里面的sleep方法睡眠过程中被中断了,然后产生异常.由于sleep在一个try-finally语句块,因此可以清理掉资源
	// ②:由于Thread.interrupted()返回true.(即正常被中断)
	public void run() {
		// try语句块包围着while循环,如果挪到里面,则永远都不会退出,除非在里面catch
		// InterruptedException并重新调用interrupt()
		try {
			while( !Thread.interrupted() ) {
				// point1:中断点在point1和point2之间,经由InterruptedExceptio退出
				NeedsCleanup n1 = new NeedsCleanup( 1 );
				try {
					System.out.println( "sleeping..." );
					TimeUnit.SECONDS.sleep( 1 );

					// point2
					NeedsCleanup n2 = new NeedsCleanup( 2 );
					try {
						System.out.println( "do some calculating" );
						for( int i = 0; i < 2500000; i++ ) {
							d = d + (Math.PI + Math.E) / d;
						}
						System.out.println( "Finished time-consuming operation..." );
					} finally {
						n2.cleanup();
					}
				} finally {
					n1.cleanup();
					// Thread.currentThread().interrupt();
				}
			}
			System.out.println( "Exiting via while() test" );
		} catch( InterruptedException e ) {
			System.out.println( "Exiting via InteruuptedException" );
		}
	}
}
