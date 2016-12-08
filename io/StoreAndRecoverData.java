package del;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//д�����ݺͻָ�����
public class StoreAndRecoverData
{
	public static void main( String[] args ) throws Exception
	{
		// д������
		FileOutputStream fos = new FileOutputStream( "Data.txt" );
		BufferedOutputStream bos = new BufferedOutputStream( fos );
		DataOutputStream dos = new DataOutputStream( bos );
		dos.writeDouble( 3.1415 );
		dos.writeUTF( "�й���У��ѧ" );
		dos.writeDouble( 2.3333 );
		dos.writeFloat( (float) 2.111 );
		dos.flush();
		//ֻ��Ҫ�ر���������
		dos.close();

		//��ȡ����
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
