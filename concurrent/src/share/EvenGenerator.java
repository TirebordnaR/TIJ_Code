package share;

public class EvenGenerator extends IntGenerator {

	// 使用并发时,将Field设置为private是必要的
	// 不然synchronized实例方法与直接访问域就会冲突
	// 即某个线程没有获得锁,但是仍然可以通过Object.xx = yy来直接改写该资源的值或者其他属性
	// 以下面的next()为例子,假设有10个线程,9个线程都是调用synchronized next(),那么这9个线程不会出现同时调用next()方法
	// 假设剩余的那个线程B通过g.value来直接访问,那么当9个线程中的某个线程A在执行第一个++value操作时
	// B线程同样可以访问此事的g.value值,而不用等到A线程完成synchronized next()方法
	private int value = 0;

	@Override
	public synchronized int next() {
		++value;
		++value;
		return value;
	}

	public static void main(String[] args) {

		// 测试EvenGenerator在多线程下生成的下一个数是不是偶数
		IntGenerator g = new EvenGenerator();
		EvenChecker.test( g );
	}
}
