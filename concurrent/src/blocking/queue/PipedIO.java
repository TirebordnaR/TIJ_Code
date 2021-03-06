package blocking.queue;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PipedIO {
	public static void main(String[] args) throws Exception {

		Sender s = new Sender();
		Reciever r = new Reciever( s );

		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute( s );
		exec.execute( r );

		Thread.sleep( 4000 );
		exec.shutdownNow();
	}
}

class Sender implements Runnable {

	private Random rand = new Random( 67 );
	private PipedWriter pipe = new PipedWriter();

	public PipedWriter getPipe() {
		return pipe;
	}

	@Override
	public void run() {
		try {
			while( true ) {
				for( int ch = 'a'; ch < 'z'; ch++ ) {
					pipe.write( (char) ch );
					Thread.sleep( rand.nextInt( 500 ) );
				}
			}
		} catch( IOException e ) {
			System.out.println( "Sender write() exception" );
		} catch( InterruptedException e ) {
			System.out.println( "sender interrupted" );
		}
	}
}

class Reciever implements Runnable {

	private PipedReader pipe; // PipeReader可以被中断
	private Sender sender; // 与Reciever相关联的Sender

	public Reciever(Sender sender) throws IOException {
		this.sender = sender;
		pipe = new PipedReader( this.sender.getPipe() );
	}

	@Override
	public void run() {
		try {
			while( true ) {
				// PipeReader可以被中断,而System.in则不可被中断
				System.out.print( "" + (char) pipe.read() + " " );
				// System.out.print( "" + (char)System.in.read() + " " );
			}
		} catch( IOException e ) {
			System.out.println( "\nread() exception..." );
		}
	}
}