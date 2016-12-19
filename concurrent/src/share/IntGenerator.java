package share;

public abstract class IntGenerator {

	// volatile强制多线程读取该变量时同步主存和线程的工作内存
	private volatile boolean canceled = false;

	public abstract int next();

	public void cancel() {
		canceled = true;
	}

	public boolean isCanceled() {
		return canceled;
	}
}
