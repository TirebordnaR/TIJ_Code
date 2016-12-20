package nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class GetData {

	public static void main(String[] args) {
		ByteBuffer bb = ByteBuffer.allocate( 60 );
		IntBuffer ib = bb.asIntBuffer();

		ib.put( -2 );
		ib.put( 3 );
		ib.put( new int[] { 1, 2, 34, 5, 9 } );
		// InBuffer的get(int index)方法不改变position的值
		// InBuffer的get()方法改变position的值,可以使用rewind()返回到开始
		System.out.println( "ib.get(3):" + ib.get( 3 ) );
		ib.put( 4 );

		// 上面每put一个int元素,position就加1
		System.out.println( "pos:" + ib.position() );

		ib.rewind();
		System.out.println( "ib.get():" + ib.get() );
		System.out.println( ib.limit() );
		// flip()作用：
		/*
		 * limit = pos pos = 0 mark = -1
		 */
		ib.flip();
		System.out.println( "ib.get():" + ib.get() );
		// System.out.println( "ib.get():" + ib.get() );
		System.out.println( ib.limit() );
	}

}
