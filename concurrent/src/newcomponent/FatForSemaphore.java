package newcomponent;

public class FatForSemaphore {
	private volatile double d;
	private static int count;
	private final int id = count++;

	public FatForSemaphore() {
		// 做一些代价比较大的事情
		for( int i = 0; i < 10000; i++ ) {
			d += (Math.E + Math.PI) / (double) i;
		}
	}

	@Override
	public String toString() {
		return "Fat " + id;
	}
}
