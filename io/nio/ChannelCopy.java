package newio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class ChannelCopy
{
	// 使用Channel进行文件拷贝
	public static void main( String[] args ) throws Exception
	{
		FileInputStream fis = new FileInputStream( "in.txt" );
		FileChannel in = fis.getChannel();
		FileOutputStream fos = new FileOutputStream( "out.txt" );
		FileChannel out = fos.getChannel();
		ByteBuffer buff = ByteBuffer.allocate( 1024 );

		// 从文件当前的position开始读一块数据到buff
		// 此循环里面的read/write都会增加buff的position属性值
		while( in.read( buff ) != -1 )
		{
			buff.flip();
			out.write( buff ); // buff-->out
			buff.clear();
		}

		// 查看当前通道对应的文件的字节个数
		System.out.println( in.size() );

		// 或者一次性传送到out
		// or out.transFrom(in, 0, in.size());
		// in.transferTo( 0, in.size(), out );

		// 对于通道,只需要关闭对应的流即可,它会自动调用 channel.close()方法
		fis.close();
		fos.close();
	}
}
