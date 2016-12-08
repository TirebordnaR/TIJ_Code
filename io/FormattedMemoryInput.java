package del;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Arrays;

//格式化内存输入
public class FormattedMemoryInput
{
	public static void main( String[] args ) throws Exception
	{
		// s在内存中用Unicode16 big endian表示,其中"中"字用2D 4E表示, "a"用 61 表示
		// 为了获取utf-8的编码,Java查找"中"字的utf-8编码(E4 B8 AD),获得3个字节
		// 查找 "a" 的UTF-8编码61,获得一个字节
		
		String s = "abc1中";
		byte[] buf = s.getBytes( "UTF-8" );

		// [97, 98, 99, 49, -28, -72, -83]
		System.out.println( Arrays.toString( buf ) );

		ByteArrayInputStream bai = new ByteArrayInputStream( buf );
		DataInputStream dis = new DataInputStream( bai );

		/*
		 * readByte()永远返回合法值,读到末尾会抛出异常,当然可以try catch EOFException来捕获
		 * try{
		 * while(true)
		 * 	System.out.println( (char)dis.readByte() );
		 * }catch(EOFException e){ }
		 * 
		 */
		System.out.println( "==========" );

		// 对于没有阻塞的情况,可以用avaiable()方法获取还有多少字节可以读
		while( dis.available() != 0 )
			System.out.println( (char) dis.readByte() );
		//关闭流
		dis.close();

	}
}
