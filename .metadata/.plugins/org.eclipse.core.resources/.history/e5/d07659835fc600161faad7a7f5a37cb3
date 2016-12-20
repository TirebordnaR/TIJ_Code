package zip.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
// 压缩类库是按照字节方式来处理的,而不是按照字符方式处理
// GZIPOutputStream/GZIPInputStream --对单个数据源压缩以及解压
// ZipOutputStream/ZipInputStream --可以对多个数据源进行压缩解压
public class GzipCompress
{
	public static void main( String[] args ) throws Exception
	{
		
		int c;
		FileInputStream fis = new FileInputStream( "x.txt" );
		
		//Gzip压缩数据源,然后写入到1.gz
		GZIPOutputStream gos = new GZIPOutputStream( new FileOutputStream( "1.gz" ) );
		BufferedOutputStream bos = new BufferedOutputStream( gos );
		while( (c = fis.read()) != -1)
			bos.write( c );
		bos.flush();
		bos.close();
		fis.close();
		
		//Gzip解压后,然后读取内容
		GZIPInputStream gis = new GZIPInputStream( new FileInputStream( "1.gz" ) );
		BufferedInputStream bis = new BufferedInputStream( gis );
		while( (c = bis.read()) != -1)
			System.out.print( (char)c );
		bis.close();
	}

}
