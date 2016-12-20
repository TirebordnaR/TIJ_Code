package zip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

// 解压/压缩 流
public class InflaterDeflater {

	public static void main(String[] args) throws Exception {

		// 被压缩的数据源
		FileInputStream fis = new FileInputStream( "x.txt" );
		// 创建一个x.zip的文件流,然后把数据写到该文件流
		// 但是在写入之前,这些数据以zip压缩算法压缩后才被写入到该文件流
		DeflaterOutputStream dos = new DeflaterOutputStream( new FileOutputStream( "x.zip" ) );
		int ch = -1;
		int count = 0;
		// 写数据到压缩文件
		while( (ch = fis.read()) != -1 ) {
			// 把数据压缩后写入到文件流
			dos.write( ch );
			++count;
		}
		fis.close();
		dos.close();

		// 从压缩文件中还原出数据,默认解压缩器是zip解压缩算法
		InflaterInputStream iis = new InflaterInputStream( new FileInputStream( "x.zip" ) );
		byte[] b = new byte[count];
		iis.read( b );
		System.out.println( Arrays.toString( b ) );
		iis.close();

		System.out.println( "========" );
		// 压缩文件x.txt(默认zip格式),然后把压缩文件做为流,从中读取字节
		DeflaterInputStream dis = new DeflaterInputStream( new FileInputStream( "x.txt" ) );
		while( (ch = dis.read()) != -1 )
			System.out.printf( "#:%x\n", ch );
		dis.close();

		// DeflaterInputStream没有mark/reset方法,必须重新创建一个压缩数据的流
		dis = new DeflaterInputStream( new FileInputStream( "x.txt" ) );
		// 解压dis数据流
		iis = new InflaterInputStream( dis );
		b = new byte[count];
		iis.read( b );
		System.out.println( Arrays.toString( b ) );
		iis.close();
	}
}
