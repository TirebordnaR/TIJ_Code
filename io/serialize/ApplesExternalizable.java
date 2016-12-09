package serialize.io;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

//ʵ��Externalizable�ӿ�ʱ��Ҫ��дwriteExternal/readExternal����
//����2����������,��������Ĵ���
//ע�⣺������������һ����public�����޲���

//��ʵ�ֽӿ�Serializable��ͬ����,ʵ��Externalizable�ӿڻ����ȵ��ù�����(�����ֶζ���ĳ�ʼ��)
//Ȼ��ŵ���readExternal,��ʵ��Serializable�ӿڵķ����л���ֱ���Զ���洢�Ķ�����λ������
class Apple1 implements Externalizable
{
	// ʵ��Externalizable�ӿ�ʱ,�������Ĭ�Ϲ�����,���ұ�����public��
	int val;
	String s = null;
	public Apple1()
	{
		System.out.println( "Apple1 cons...." );
	}
	
	public Apple1(int val, String s)
	{
		System.out.println( "Apple1 cons...." );
		this.val = val;
		this.s = s;
	}

	@Override
	public void writeExternal( ObjectOutput out ) throws IOException
	{
		System.out.println( "Apple1 writeExternal...." );
		//������д�����
		out.writeInt( val );
		out.writeObject( s );
	}

	@Override
	public void readExternal( ObjectInput in ) throws IOException, ClassNotFoundException
	{
		System.out.println( "Apple1 readExternal...." );
		//�����Ķ�������,��Ϊ�����������ֶγ�ʼ��,��������ֶ��ָ�������ֶ�
		val = in.readInt();
		s = (String) in.readObject();
	}
	
	@Override
	public String toString(){
		return val + ":" + s;
	}

}

class Apple2 implements Externalizable
{
	// ʵ��Externalizable�ӿ�ʱ,�������Ĭ�Ϲ�����(���޲�),���ұ�����public��
	// ��ͼ��д���������в�ͨ��
	public Apple2()
	{
		System.out.println( "Apple2 cons...." );
	}

	@Override
	public void writeExternal( ObjectOutput out ) throws IOException
	{
		System.out.println( "Apple2 writeExternal...." );
	}

	@Override
	public void readExternal( ObjectInput in ) throws IOException, ClassNotFoundException
	{
		System.out.println( "Apple2 readExternal...." );
	}
	
}

public class ApplesExternalizable
{
	public static void main( String[] args ) throws Exception
	{
		FileOutputStream fos = new FileOutputStream( "out" );
		ObjectOutputStream oos = new ObjectOutputStream( fos );

		Apple1 a1 = new Apple1(1, "Apple1");
		Apple2 a2 = new Apple2();

		oos.writeObject( a1 );
		oos.writeObject( a2 );

		FileInputStream fis = new FileInputStream( "out" );
		ObjectInputStream ois = new ObjectInputStream( fis );
		a1 = (Apple1) ois.readObject();
		a2 = (Apple2) ois.readObject();
		System.out.println( a1 );
		System.out.println( a2 );

		oos.close();
		ois.close();
	}
}