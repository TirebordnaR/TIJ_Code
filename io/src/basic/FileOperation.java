package basic;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileOperation
{

	// 文件名过滤器
	static class DirFilter implements FilenameFilter
	{
		// 选择出名字中包含.txt的文件名
		@Override
		public boolean accept( File dir, String name )
		{
			if( name.indexOf( ".txt" ) != -1 )
				return true;
			return false;
		}
	}

	public static void main( String[] args ) throws IOException
	{

		// System.out.println( File.separator );
		// 创建一个File对象,但是并没有打开文件
		File f = new File( "test" ).getAbsoluteFile();
		System.out.println( "exits: " + f.exists() + "\n" + 
				"Abs path:" + f.getAbsolutePath() + "\n" + 
				"is Abs path:" + f.isAbsolute() + "\n" + 
				"read:" + f.canRead() + "\n" + 
				"write:" + f.canWrite() + "\n" + 
				"name:" + f.getName() + "\n" + 
				"path:" + f.getPath() + "\n" + 
				"len:" + f.length() + "\n" + 
				"last Mod:" + f.lastModified() + "\n" + 
				"isFile : " + f.isFile() + "\n" + 
				"isDir: " + f.isDirectory() + "\n" + 
				"Parent:" + f.getParent()+ "\n"
		);

		// 类似mv命令,把f改为新的文件名,与平台相关
		System.out.println( f.renameTo( new File( "H:/TT" ) ) );

		// 创建目录,递归的进行
		System.out.println( "mkdirs:" + f.mkdirs() );

		// 删除f(文件或者目录),如果f是目录,那么必须是空目录才能成功删除
		// System.out.println(f.delete());

		f = new File( "1/2" ).getAbsoluteFile();
		if( f.isDirectory() )
		{
			// list()取得文件名字String[],不带路径
			for( String s : f.list( new DirFilter() ) )
				System.out.println( "File Name:" + s );
			// listFiles()取得File[],然后调用文件的getAbsolutePath()可以取得带绝对路径的名字
			for( File cf : f.listFiles() )
				System.out.println( "File Name Abs path:" + cf.getAbsolutePath() );
		}

		// 创建临时文件,可以指定临时文件目录
		// C:\Users\ADMINI~1\AppData\Local\Temp\PrefixName4010352564483158698SuffixName
		File tf = File.createTempFile( "PrefixName", "SuffixName" );
		tf = File.createTempFile( "PrefixName", "SuffixName", new File( "H:/" ) );
		System.out.println( tf.getAbsolutePath() );

		// 设置RWX属性
		tf.setExecutable( true );
		tf.setReadOnly();

		// 转化成URL格式,File的toURL已经标记为Deprecated
		System.out.println( tf.toURI().toURL() );
	}
}
