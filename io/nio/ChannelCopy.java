package newio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class ChannelCopy
{
	// ʹ��Channel�����ļ�����
	public static void main( String[] args ) throws Exception
	{
		FileInputStream fis = new FileInputStream( "in.txt" );
		FileChannel in = fis.getChannel();
		FileOutputStream fos = new FileOutputStream( "out.txt" );
		FileChannel out = fos.getChannel();
		ByteBuffer buff = ByteBuffer.allocate( 1024 );

		// ���ļ���ǰ��position��ʼ��һ�����ݵ�buff
		// ��ѭ�������read/write��������buff��position����ֵ
		while( in.read( buff ) != -1 )
		{
			buff.flip();
			out.write( buff ); // buff-->out
			buff.clear();
		}

		// �鿴��ǰͨ����Ӧ���ļ����ֽڸ���
		System.out.println( in.size() );

		// ����һ���Դ��͵�out
		// or out.transFrom(in, 0, in.size());
		// in.transferTo( 0, in.size(), out );

		// ����ͨ��,ֻ��Ҫ�رն�Ӧ��������,�����Զ����� channel.close()����
		fis.close();
		fos.close();
	}
}
