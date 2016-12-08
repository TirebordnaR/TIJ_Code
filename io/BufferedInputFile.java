package del;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

//缓冲输入文件
public class BufferedInputFile
{
	public static void main( String[] args ) throws Exception
	{
		// 1.txt内容为: 中, UTF-8格式保存
		FileInputStream fin = new FileInputStream( new File( "1.txt" ) );
		InputStreamReader fr = new InputStreamReader( fin, "utf-8" ); // 假设文件utf-8编码保存
		BufferedReader br = new BufferedReader( fr );
		// BufferedReader br = new BufferedReader(fr,bufferSize); //指定缓冲区大小

		// avilable()查看有多少字节可以读
		System.out.println( fin.available() );

		//mark的参数随意填写,只要大于0即可
		br.mark( 22 );
		// 因为文件是UTF-8格式编码,用FileInputStream读取的时候原样读取字节(E4 B8 AD)到内存
		// 但是字节流转为字符流的时候,则需要告诉InputStreamReader是字节流什么格式编码,然后转化的时候才能正确的
		// 解析原有的字节流,进而正确的转化成正确的字符流(Unicode16 big endian)
		// FileInputStream读入的字节流为 E4 B8 AD,转化成字符流的时候就变为
		// 2D 4E, 此时读取一个字符的10进制值为20013
		System.out.println( br.read() );		//20013
		
		//使用reset时,之前必须要设置过mark
		br.reset();
		// readLine()方法去掉了读取一行的换行符号
		String s = null;
		while( (s = br.readLine()) != null )
			System.out.println( s + "$" );
		

		// 类似这样把流一层层包装起来的,只需要关闭最外一层即可
		br.close();
	}
}
