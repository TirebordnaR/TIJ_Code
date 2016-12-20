package nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

// 缓冲器ByteBuffer存放的是字节,转换成字符,建议使用方法1
public class BufferToText {

	public static void main(String[] args) throws Exception {
		// Charset.availableCharsets(); //查看可用的字符集
		// 方法1:写入的时候getBytes时指定编码格式,然后读出数据,用前面指定的编码decode
		FileOutputStream fos = new FileOutputStream( "fc.txt" );
		FileChannel fc = fos.getChannel();
		fc.write( ByteBuffer.wrap( "A\n中①".getBytes( "UTF-8" ) ) );
		fos.close();

		FileInputStream fis = new FileInputStream( "fc.txt" );
		fc = fis.getChannel();
		ByteBuffer buff = ByteBuffer.allocate( 1024 );
		fc.read( buff );
		buff.flip();
		// decode返回charBuffer类型,它的toString方法返回字符串
		System.out.println( Charset.forName( "UTF-8" ).decode( buff ) );
		fis.close();
		buff.clear();

		/* 方法2 */
		fos = new FileOutputStream( "fc.txt" );
		fc = fos.getChannel();
		buff = ByteBuffer.allocate( 12 );
		// 把ByteBuffer看成字符缓冲区CharBuffer,一个个put进去
		buff.asCharBuffer().put( "A\n中国C" );
		fc.write( buff );
		fos.close();
		fis = new FileInputStream( "fc.txt" );
		fc = fis.getChannel();
		buff.clear();
		fc.read( buff );
		buff.flip();
		// buff里面多余的位置会被0填充,转化成字符也就是0,会被显示出来
		System.out.println( buff.asCharBuffer() + ":" + (int) buff.get( 11 ) );
		fis.close();

	}

}
