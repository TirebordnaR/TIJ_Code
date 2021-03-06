package lock;

import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.concurrent.*;

public class FileLocking {
	public static void main(String[] args) throws Exception {

		FileOutputStream fos = new FileOutputStream( "fileLock.txt" );

		// tryLock是非阻塞式加锁,对该通道对应的文件进行加锁(此处是独占锁,当然操作系统要支持独占锁,可以通过isShared查询)
		// tryLock的重载方法还可以设置对文件的一部分进行加锁以及是否是共享锁(使用内存映射文件的方式的时候常用)

		FileLock fl = fos.getChannel().tryLock();
		if ( fl != null ) {
			System.out.println( "locked file" );
			TimeUnit.MILLISECONDS.sleep( 1000 );
			fl.release();
			System.out.println( "release" );
		}

		fos.close();
	}
}
