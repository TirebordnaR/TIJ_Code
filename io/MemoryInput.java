package del;

import java.io.StringReader;

public class MemoryInput
{
	public static void main( String[] args ) throws Exception
	{

		StringReader sr = new StringReader( "abcdefghijkl" );

		// read()�������ص���int����,����-1��ʾ����ĩβ
		int c;
		sr.skip( 2 );
		System.out.println( (char) sr.read() );
		
		/*
		 * while( (c = sr.read()) != -1)
		 * System.out.println( (char)c + "#");
		 */
		
		sr.mark( 0 ); // ���λ��,�´ε���reset()��Ϳ�����������ǵ�λ�ü�����
		System.out.println( (char) sr.read() );
		System.out.println( (char) sr.read() );
		sr.reset();
		System.out.println( (char) sr.read() );

		sr.close();
	}
}
