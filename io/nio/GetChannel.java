package newio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

// nio 对IO的效率有所提升
public class GetChannel
{
	public static void main( String[] args ) throws Exception
	{
		FileOutputStream fos = new FileOutputStream( "fc.txt" );
		// 拿到流的Channel通道
		FileChannel fc = fos.getChannel();
		fc.write( ByteBuffer.wrap( "some text \nanother text".getBytes( "utf-8" ) ) );
		// 只需要关闭流即可,流的close()会调用channel.close()
		fos.close();

		RandomAccessFile raf = new RandomAccessFile( "fc.txt", "rw" );
		fc = raf.getChannel();
		fc.position( fc.size() ); // 定位到文件尾开始写
		fc.write( ByteBuffer.wrap( "\nmore contents\n".getBytes( "utf-8" ) ) );
		raf.close();

		FileInputStream fos2 = new FileInputStream( "fc.txt" ); // 1024 bytes缓冲区
		fc = fos2.getChannel();
		ByteBuffer buff = ByteBuffer.allocate( 1024 );
		// FileChannel读数据到ByteBuffer里面时,ByteBuffer的position会自动增加,每读入一个字节position加1
		fc.read( buff );
		// flip的作用：更新limit的值为position的值,同时把position设置为0
		// read的时候可以从ByteBuffer的第一个字节开始读,结合hasRemaining可以限制读到limit位置
		// 这样就可以读取出所有的有效字节
		buff.flip();
		// rewind()的作用: 把position设置为0,但是不更新limit的值,limit默认值是capacity大小
		// read的时候如果使用hasRemaining,那么就无法避免读到缓冲区后面的无效内容(除非用反射直接修改limit的值...)
		// buff.rewind();
		while( buff.hasRemaining() )
			System.out.println( "#" + (char) buff.get() + "#" );
		fos2.close();
	}
}
