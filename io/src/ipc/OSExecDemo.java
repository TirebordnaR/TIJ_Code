package ipc;

import java.io.IOException;

public class OSExecDemo {
	
	public static void main(String[] args) throws IOException {
		OSExec.command("javap OSExecDemo");
	}
}
