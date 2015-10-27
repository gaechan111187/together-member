package server;

/**
 * 서버 서비스에서 구현할 내용
 * 
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import global.Command;
import member.MemberVO;

public class ServerServiceImpl implements Runnable {
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	private StringBuffer buffer;
	private ServerDAO dao;

	public ServerServiceImpl() {
	}

	public ServerServiceImpl(Socket clientSocket) {
		dao = new ServerDAO();
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
				String command = in.readUTF();
				System.out.println(command);
				StringTokenizer token = new StringTokenizer(command, Command.COMMAND_DELIMITER);
				switch (token.nextToken()) {
				case Command.REQUEST_LOGIN:
					//System.out.println("로그인 요청이 들어왔습니다.");
					String phone = token.nextToken();
					String password = token.nextToken();
					MemberVO temp = dao.confirmLogin(phone, password);
					respondLogin(temp.toString());
					break;
				case Command.SEND_MESSAGE:
					String msg = token.nextToken();
					
				default:
					break;
				}
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String respondLogin(String str) {
		if (str != null) {
			buffer.setLength(0);
			buffer.append(Command.ALLOW_LOGIN + "|" + str); // 로그인 허가
			send(buffer.toString());
		} else {
			send(Command.DENY_LOGIN); // 로그인 거부 전송
		}
		return null;
	}
	public void send(String sendData) {
		try {
			out.writeUTF(sendData);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
