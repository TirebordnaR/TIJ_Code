package nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

//内存映射文件
public class LargeMapFiles
{
	public static void main( String[] args ) throws Exception
	{
		int length = 0x20; // 20 bytes
		// 对于又需要读又需要写,建议使用RandomAccessFile
		RandomAccessFile raf = new RandomAccessFile( "1.txt", "rw" );
		MappedByteBuffer mbb = raf.getChannel().map( FileChannel.MapMode.READ_WRITE, 0, length );

		for( int i = 0; i < length; ++i )
			mbb.put( (byte) 'x' );
		System.out.println( "end" );
		for( int i = 0; i < 10; ++i )
			System.out.println( (char) mbb.get( i ) );
		// 对于通道,只需要关闭对应的流即可,它会自动调用 channel.close()方法
		raf.close();
	}

}
