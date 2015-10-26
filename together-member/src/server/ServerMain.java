package server;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerMain {
	private static final int PORT = 2222;
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("서버 소켓을 생성했습니다.");
			while (true) {
				Socket clientSocket = serverSocket.accept(); // 클라이언트의 접속을 대기
				ServerServiceImpl service = new ServerServiceImpl(clientSocket); // 서버 서비스 생성
				Thread server = new Thread(service);
				server.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "서버가 이미 가동중입니다.");
			System.exit(0);
		}
	}
}
