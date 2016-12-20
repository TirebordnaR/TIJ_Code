package nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

public class ExchangeNeighbourChar {
	private static void symmtricsScramble(CharBuffer buffer) {
		while( buffer.hasRemaining() ) {
			buffer.mark();
			char c1 = buffer.get();
			char c2 = buffer.get();
			buffer.reset();
			buffer.put( c2 ).put( c1 );
		}
	}

	public static void main(String[] args) {
		char[] data = "ABCDEFGH�д�".toCharArray();
		System.out.println( data[8] ); // ��
		ByteBuffer bb = ByteBuffer.allocate( data.length * 2 );

		CharBuffer cb = bb.asCharBuffer();
		// CharBuffer cb = CharBuffer.wrap( data ); // OK

		cb.put( data );
		System.out.println( cb.flip() );
		symmtricsScramble( cb );
		System.out.println( cb.flip() );
		symmtricsScramble( cb );
		System.out.println( cb.flip() );

	}

}
