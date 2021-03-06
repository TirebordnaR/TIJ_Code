package basic;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// 从任务中产生返回值
// 只需要任务实现Callable<T>接口即可,必须用ExecutorService.submit()调用该任务
public class CallableDemo {
	public static void main(String[] args) {

		ExecutorService exec = Executors.newCachedThreadPool();

		List<Future<String>> rs = new ArrayList<>();

		for( int i = 0; i < 5; i++ ) {
			TaskWithResult t = new TaskWithResult( i );
			// submit()方法返回与任务结果相关联的Future<T>对象,在这里不阻塞
			Future<String> f = exec.submit( t );
			rs.add( f );
		}

		for( Future<String> f : rs ) {
			try {
				// 任务如果没有完成,get()方法会阻塞
				// 可以调用isDone()来查询是否完成,还可以调用带超时的get()
				// System.out.println( f.isDone() );
				String result = f.get();
				System.out.println( result );
			} catch( InterruptedException e ) {
				e.printStackTrace();
			} catch( ExecutionException e ) {
				e.printStackTrace();
			} finally {
				exec.shutdown();
			}
		}
	}
}

// Callable<String>中String代表任务的返回值类型
class TaskWithResult implements Callable<String> {

	private final int taskId;

	public TaskWithResult(int id) {
		this.taskId = id;
	}

	@Override
	public String call() throws Exception {
		TimeUnit.SECONDS.sleep( 1 );
		TimeUnit.SECONDS.sleep( 1 );
		return "result :" + taskId + "-->" + Thread.currentThread();
		// Thread[pool-1-thread-3,5,main] 5代表优先级
	}
}
