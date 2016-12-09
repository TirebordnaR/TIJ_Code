package serialize.io;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ThawAlient
{
	public static void main( String[] args ) throws Exception
	{
		// 从文件中反序列化对象
		// 虚拟机必须还能够找到对象的Class文件
		FileInputStream fis = new FileInputStream( "Alien" );
		ObjectInputStream ois = new ObjectInputStream( fis );
		Object mystery = ois.readObject();
		//System.out.println( mystery.getClass() );
		System.out.println( mystery );
		Object mystery2 = ois.readObject();
		System.out.println( mystery2 );
		ois.close();
	}
}
