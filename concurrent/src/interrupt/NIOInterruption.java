package interrupt;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// NIO的通道被阻塞时,可以响应中断
public class NIOInterruption {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();

		ServerSocket server = new ServerSocket( 8080 );
		InetSocketAddress isa = new InetSocketAddress( "localhost", 8080 );

		SocketChannel sc1 = SocketChannel.open( isa );
		SocketChannel sc2 = SocketChannel.open( isa );

		Future<?> f = exec.submit( new NIOBlocked( sc1 ) );
		exec.execute( new NIOBlocked( sc2 ) );

		exec.shutdown();

		Thread.sleep( 1000 ); // 1s
		f.cancel( true ); // 使用submit()启动的任务只需要调用cancel即可发出中断

		Thread.sleep( 1000 );
		sc2.close(); // 释放阻塞在sc2通道上的资源
	}
}

class NIOBlocked implements Runnable {
	private final SocketChannel sc;

	public NIOBlocked(SocketChannel channel) {
		this.sc = channel;
	}

	@Override
	public void run() {
		try {
			System.out.println( "Waiting for read() in " + this );
			sc.read( ByteBuffer.allocate( 20 ) );
		} catch( ClosedByInterruptException e ) {
			System.out.println( "ClosedByInterruptException" );
		} catch( AsynchronousCloseException e ) {
			System.out.println( "AsynchronousCloseException" );
		} catch( IOException e ) {
			System.out.println( "IOException" );
		}

		System.out.println( "Exiting NIOBlocked.run() " + this );
	}
}
