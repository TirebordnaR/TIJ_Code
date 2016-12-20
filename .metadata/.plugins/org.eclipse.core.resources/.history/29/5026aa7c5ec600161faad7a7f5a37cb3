package ipc.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class OSExec
{
	public static void command( String cmd ) throws IOException
	{
		Process process = new ProcessBuilder( cmd.split( " " ) ).start();
		// getInputStream返回的流和process的输出相连接
		BufferedReader results = new BufferedReader( new InputStreamReader( process.getInputStream() ) );
		String s;
		while( (s = results.readLine()) != null )
			System.out.println( s );

		BufferedReader errors = new BufferedReader( new InputStreamReader( process.getErrorStream() ) );
		while( (s = errors.readLine()) != null )
			System.err.println( s );

		System.out.println( "end" );
	}
}
