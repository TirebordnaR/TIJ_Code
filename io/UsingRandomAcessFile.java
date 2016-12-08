package del;

import java.io.*;

//读写随机访问文件
public class UsingRandomAcessFile
{
	public static void main( String[] args ) throws Exception
	{
		RandomAccessFile raf = new RandomAccessFile( "randomAccess", "rw" );
		// 写入4个double值和一个UTF String
		for( int i = 0; i < 4; ++i )
			raf.writeDouble( i * 3.1415 );
		raf.writeUTF( "end pi" );
		raf.close();

		// 用seek定位并修改第3个double值
		raf = new RandomAccessFile( "randomAccess", "rw" );
		raf.seek( 2 * 8 );
		raf.writeDouble( 99999.2 );
		raf.close();

		raf = new RandomAccessFile( "randomAccess", "r" );
		for( int i = 0; i < 4; ++i )
			System.out.println( raf.readDouble() );
		System.out.println( raf.readUTF() );
		raf.close();
	}
}
