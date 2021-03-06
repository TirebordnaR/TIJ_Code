package stdio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

//重定向io流
public class Redirect {
	public static void main(String[] args) throws Exception {

		PrintStream console = System.out;

		BufferedInputStream in = new BufferedInputStream( new FileInputStream( "Redirecting.in" ) );
		PrintStream out = new PrintStream( new FileOutputStream( "Redirecting.out" ), true );

		// 重定向in out err流
		System.setIn( in );
		System.setOut( out );
		System.setErr( out );

		BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
		String s = null;
		while( (s = br.readLine()) != null )
			System.out.println( s );
		out.close();
		System.setOut( console ); // 恢复输出
	}
}
