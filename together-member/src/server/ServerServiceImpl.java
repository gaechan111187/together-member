package server;
/**
 * 서버 서비스에서 구현할 내용
 * 
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerServiceImpl implements Runnable{
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	private StringBuffer buffer;
	
	public ServerServiceImpl() {
	}
	
	public ServerServiceImpl(Socket clientSocket) {
		client = clientSocket;
		try {
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
			buffer = new StringBuffer(4096);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
