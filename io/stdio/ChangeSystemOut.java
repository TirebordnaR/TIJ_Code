package standard.io;

import java.io.IOException;
import java.io.PrintWriter;

public class ChangeSystemOut
{
	public static void main( String[] args ) throws IOException
	{
		// �ڶ�����������true,�����Զ�flush����,������Ҫ�ֶ�flush()
		PrintWriter out = new PrintWriter( System.out, true );
		out.println( "hello" );
		out.flush();
	}
}
