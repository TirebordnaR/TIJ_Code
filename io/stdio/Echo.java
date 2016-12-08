package standard.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Echo
{
	public static void main( String[] args ) throws IOException
	{
		//��׼������ת�����ַ���,����ʹ��readLine����ֱ�Ӷ�ȡһ��
		InputStreamReader isr = new InputStreamReader( System.in );
		BufferedReader stdin = new BufferedReader( isr );

		String s;
		while( ((s = stdin.readLine()) != null) && (s.length() != 0) )
			System.out.println( s );

		stdin.close();
	}
}
