package client;

/**
 * 클라이언트가 구동되면
 * 최초, 로그인 창이 로드된다. (확인, 회원가입)
 * 성공하면
 * 메인메뉴가 로드된다.
 * 메인메뉴에는 친구추가 / 채팅 기능이 존재한다.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import global.Command;

public class ClientServiceImpl implements Runnable {
	private static final int PORT = 2222;
	private static final String COMMAND_DELIMITER = "|";
	private static final String USER_DELIMETER = "'";
	private String serverIP;
	private ClientUI ui;
	private Thread thisThread;
	private Socket clientSocket;
	private DataInputStream in;
	private DataOutputStream out;
	private StringBuffer buffer;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClientServiceImpl() {
		try {
			serverIP = JOptionPane.showInputDialog("서버IP 설정");
			clientSocket = new Socket(serverIP, PORT);
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			buffer = new StringBuffer(4096); // 버퍼크기 지정
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "서버에 접속할 수 없습니다.");
			System.exit(0);
		} 
	}

	@Override
	public void run() {
		
		ui = new ClientUI(this); // 최초에 로그인 UI를 부름
		Thread currThread = Thread.currentThread();
		while (currThread == thisThread) { // 현재 스레드가 나와 일치하면
			try {
				String command = in.readUTF(); // 명령을 읽어옴
				switch (command) {
				case Command.LOGIN:
					break;
				case Command.SIGNUP:
					break;
				case Command.ADD:
					break;
				case Command.EXIT:
					break;
				default:
					break;
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void send(String sendData) {
		try {
			out.writeUTF(sendData);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setThisThread(Thread thisThread) {
		this.thisThread = thisThread;
	}
}
