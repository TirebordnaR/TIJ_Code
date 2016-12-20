package basic;

import java.io.PrintWriter;

public class FileOutputShortcut
{
	public static void main( String[] args ) throws Exception
	{
		// 如果文件存在,则覆盖掉原有的文件,没有该文件,则创建该文件,设置默认的缓冲区大小
		// 还可设置是否自动缓冲
		PrintWriter pw = new PrintWriter( "temp.txt", "utf-8" );

		// pw.append('X');		//追加写

		pw.print( "helloworld絵えもじ①𡘾☝" );
		pw.println(); // 写入换行符
		//刷新缓冲区,关闭流
		pw.flush();
		pw.close();

		System.out.println( "end" );
	}
}
