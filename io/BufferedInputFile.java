package del;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

//���������ļ�
public class BufferedInputFile
{
	public static void main( String[] args ) throws Exception
	{
		// 1.txt����Ϊ: ��, UTF-8��ʽ����
		FileInputStream fin = new FileInputStream( new File( "1.txt" ) );
		InputStreamReader fr = new InputStreamReader( fin, "utf-8" ); // �����ļ�utf-8���뱣��
		BufferedReader br = new BufferedReader( fr );
		// BufferedReader br = new BufferedReader(fr,bufferSize); //ָ����������С

		// avilable()�鿴�ж����ֽڿ��Զ�
		System.out.println( fin.available() );

		//mark�Ĳ���������д,ֻҪ����0����
		br.mark( 22 );
		// ��Ϊ�ļ���UTF-8��ʽ����,��FileInputStream��ȡ��ʱ��ԭ����ȡ�ֽ�(E4 B8 AD)���ڴ�
		// �����ֽ���תΪ�ַ�����ʱ��,����Ҫ����InputStreamReader���ֽ���ʲô��ʽ����,Ȼ��ת����ʱ�������ȷ��
		// ����ԭ�е��ֽ���,������ȷ��ת������ȷ���ַ���(Unicode16 big endian)
		// FileInputStream������ֽ���Ϊ E4 B8 AD,ת�����ַ�����ʱ��ͱ�Ϊ
		// 2D 4E, ��ʱ��ȡһ���ַ���10����ֵΪ20013
		System.out.println( br.read() );		//20013
		
		//ʹ��resetʱ,֮ǰ����Ҫ���ù�mark
		br.reset();
		// readLine()����ȥ���˶�ȡһ�еĻ��з���
		String s = null;
		while( (s = br.readLine()) != null )
			System.out.println( s + "$" );
		

		// ������������һ����װ������,ֻ��Ҫ�ر�����һ�㼴��
		br.close();
	}
}