package server;

import java.io.BufferedReader;
/**
 * 서버 서비스에서 구현할 내용
 * 
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import chat.ChatVO;
import global.Command;
import member.MemberVO;

public class ServerServiceImpl implements Runnable {
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	private StringBuffer buffer;
	private ServerDAO dao;
	private Thread thisThread;
	private int roomNumber;
	private List<StringBuffer> rooms;
	private StringBuffer room;
	private String phone;
	private static List<ServerServiceImpl> users = new Vector<ServerServiceImpl>();

	public void setThisThread(Thread thisThread) {
		this.thisThread = thisThread;
	}

	public ServerServiceImpl() {
	}

	public ServerServiceImpl(Socket clientSocket) {
		room = new StringBuffer();
		rooms = new Vector<StringBuffer>();
		roomNumber = 0;
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
					System.out.println("로그인 요청이 들어왔습니다.");
					phone = token.nextToken();
					String password = token.nextToken();
					users.add(this); // 해당 유저를 유저목록에 추가함
					System.out.println("폰번 : " + phone + " 패스워드 " + password);
					List<MemberVO> temp = dao.confirmLogin(phone, password);
					System.out.println("템프수 : " + temp.size());
					System.out.println(temp);
					if (!temp.isEmpty()) {
						respondLogin(temp.toString());
					} else {
						respondLogin(null);
					}
					break;
				case Command.SEND_MESSAGE:
					String roomNum = token.nextToken(); // 방번호
					String msg = token.nextToken(); // 메시지
					StringTokenizer user = new StringTokenizer(token.nextToken(), Command.CONTENT_DELIMITER); // 친구목록
					int length = user.countTokens();
					for (int i = 0; i < length; i++) {
						String cli = user.nextToken();
						System.out.println("유저는 " + cli);
						for (int j = 0; j < users.size(); j++) {
							if (cli.equals(users.get(j).phone)) {
								System.out.println(cli + " 같습니다.");
								buffer.setLength(0);
								buffer.append(Command.DEFFUSION_MESSAGE + "|" + roomNum + "|" + msg);
								users.get(j).send(buffer.toString());
							}
						}
					}
					break;
				case Command.SIGN_UP:
					StringTokenizer content = new StringTokenizer(token.nextToken(), Command.CONTENT_DELIMITER);
					System.out.println(content);
					int result = dao.confirmSignUp(content.nextToken(), content.nextToken(), content.nextToken(),
							content.nextToken());
					if (result != 0) {
						buffer.setLength(0);
						buffer.append(Command.ALLOW_SIGN_UP);
						send(buffer.toString());
					} else {
						buffer.setLength(0);
						buffer.append(Command.DENY_SIGN_UP);
						send(buffer.toString());
					}
				case Command.CREATE_CHATROOM: // 방을만들겠다고 클라이언트가 신청을하면
					StringTokenizer numbers = new StringTokenizer(token.nextToken(), Command.CONTENT_DELIMITER);
					int size = numbers.countTokens();
					for (int i = 0; i < size; i++) {
						System.out.println("수행함");
						String num = numbers.nextToken();
						System.out.println("방을만들 이름은 " + num); // 친구 스레드명
						for (int j = 0; j < users.size(); j++) { // 유저목록에서
							System.out.println("유저 " + users.get(j).phone);
							if (num.equals(users.get(j).phone)) {
								System.out.println("같아서 수행 " + users.get(j).phone);
								buffer.setLength(0);
								buffer.append(Command.DEFFUSION_CHATROOM + "|" + roomNumber); // 방번호를 전송
								users.get(j).send(buffer.toString());
							}
						}
					}
					rooms.add(roomNumber, new StringBuffer()); // 방을만들면서 대화를 기록할 공간을 만듦
					roomNumber++;
					break;
				default:
					break;
				}
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void respondLogin(String str) {
		if (str != null) {
			buffer.setLength(0);
			buffer.append(Command.ALLOW_LOGIN + "|" + str); // 로그인 허가
			send(buffer.toString());
		} else {
			send(Command.DENY_LOGIN); // 로그인 거부 전송
		}
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
