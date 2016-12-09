package newio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

//������ByteBuffer��ŵ����ֽ�,ת�����ַ�,����ʹ�÷���1
public class BufferToText
{
	public static void main( String[] args ) throws Exception
	{
		// Charset.availableCharsets(); //�鿴���õ��ַ���
		// ����1:д���ʱ��getBytesʱָ�������ʽ,Ȼ���������,��ǰ��ָ���ı���decode
		FileOutputStream fos = new FileOutputStream( "fc.txt" );
		FileChannel fc = fos.getChannel();
		fc.write( ByteBuffer.wrap( "A\n�Т�".getBytes( "UTF-8" ) ) );
		fos.close();

		FileInputStream fis = new FileInputStream( "fc.txt" );
		fc = fis.getChannel();
		ByteBuffer buff = ByteBuffer.allocate( 1024 );
		fc.read( buff );
		buff.flip();
		// decode����charBuffer����,����toString���������ַ���
		System.out.println( Charset.forName( "UTF-8" ).decode( buff ) );
		fis.close();
		buff.clear();

		/* ����2 */
		fos = new FileOutputStream( "fc.txt" );
		fc = fos.getChannel();
		buff = ByteBuffer.allocate( 12 );
		// �����ַ�����ȥһ����put��ȥ
		buff.asCharBuffer().put( "A\n�й�C" );
		fc.write( buff );
		fos.close();
		fis = new FileInputStream( "fc.txt" );
		fc = fis.getChannel();
		buff.clear();
		fc.read( buff );
		buff.flip();
		// buff��������λ�ûᱻ0���,ת�����ַ�Ҳ����0,�ᱻ��ʾ����
		System.out.println( buff.asCharBuffer() + ":" + (int) buff.get( 11 ) );
		fis.close();

	}

}
