package basic;

import java.util.concurrent.ThreadFactory;

// 产生后台线程的线程工程
public class DaemonThreadFactory implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread( r );
		t.setDaemon( true );
		return t;
	}
}
