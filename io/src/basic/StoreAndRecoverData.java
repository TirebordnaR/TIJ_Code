package basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//写入数据和恢复数据
public class StoreAndRecoverData {
	public static void main(String[] args) throws Exception {
		// 写入数据
		FileOutputStream fos = new FileOutputStream( "Data.txt" );
		BufferedOutputStream bos = new BufferedOutputStream( fos );
		DataOutputStream dos = new DataOutputStream( bos );
		dos.writeDouble( 3.1415 );
		dos.writeUTF( "中国技校大学" );
		dos.writeDouble( 2.3333 );
		dos.writeFloat( (float) 2.111 );
		dos.flush();
		// 只需要关闭最外层的流
		dos.close();

		// 读取数据
		FileInputStream fis = new FileInputStream( "Data.txt" );
		BufferedInputStream bis = new BufferedInputStream( fis );
		DataInputStream dis = new DataInputStream( bis );
		System.out.println( dis.readDouble() );
		System.out.println( dis.readUTF() );
		System.out.println( dis.readDouble() );
		System.out.println( dis.readFloat() );
		dis.close();

		System.out.println( "end" );
	}
}
