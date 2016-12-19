package deadlock;

public class Chopstick {

	// 筷子是否被使用中,false未被使用
	private boolean taken = false;

	// 筷子被使用
	public synchronized void take() {
		try {
			while( taken )
				wait();
			taken = true;
		} catch( InterruptedException e ) {
			System.out.println( "chopstick interrupted" );
		}
	}

	// 筷子被放下
	public synchronized void drop() {
		taken = false;
		notifyAll();
	}
}
