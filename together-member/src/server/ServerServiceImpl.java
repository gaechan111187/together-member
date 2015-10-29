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
	private static int roomNumber;
	private static List<ChatRoomVO> rooms;
	private StringBuffer room;
	private String phone;
	private static List<ServerServiceImpl> users = new Vector<ServerServiceImpl>();
	private boolean flag;

	public void setThisThread(Thread thisThread) {
		this.thisThread = thisThread;
	}

	public ServerServiceImpl() {
	}

	public ServerServiceImpl(Socket clientSocket) {
		flag = true;
		room = new StringBuffer();
		rooms = new Vector<ChatRoomVO>();
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

					for (int i = 0; i < users.size(); i++) {
						if (users.get(i).phone.equals(phone)) { // 이미 로그인상태이면
							flag = false;
							buffer.setLength(0);
							buffer.append(Command.DENY_LOGIN);
							send(buffer.toString());
						}
					}
					if (flag) {
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
					}
					break;
				case Command.SEND_MESSAGE:
					int roomNum = Integer.parseInt(token.nextToken()); // 방번호
					String msg = token.nextToken(); // 메시지
					System.out.println("방번호가 뭐길래 " + roomNum);
					String names = rooms.get(roomNum).getClients();
					System.out.println("명단 " + names);
					StringTokenizer user = new StringTokenizer(names, Command.CONTENT_DELIMITER); // 친구목록
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
				case Command.SEND_SEVER:
					int roomNums = Integer.parseInt(token.nextToken()); // 방번호
					String msgs = token.nextToken(); // 메시지
					StringTokenizer realmsg = new StringTokenizer(msgs, ">>");
					int lengthkk = realmsg.countTokens();
					for (int i = 0; i < lengthkk; i++) {
						msgs = realmsg.nextToken();
					}
					System.out.println("방번호가 뭐길래 " + roomNums);
					String namess = rooms.get(roomNums).getClients();
					System.out.println("명단 " + namess);
					StringTokenizer userss = new StringTokenizer(namess, Command.CONTENT_DELIMITER); // 친구목록
					int lengths = userss.countTokens();
					for (int i = 0; i < lengths; i++) {
						String cli = userss.nextToken();
						System.out.println("유저는 " + cli);
						for (int j = 0; j < users.size(); j++) {
							if (cli.equals(users.get(j).phone)) {
								System.out.println(cli + " 같습니다.");
								buffer.setLength(0);
								buffer.append(Command.DEFFUSION_MESSAGE + "|" + roomNums + "|" + "<서버> " + msgs);
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
				case Command.SEARCH_FRIENDS: // 친구검색 명령어 | 친구번호
					MemberVO target = dao.searchFriend(token.nextToken());
					// System.out.println("디비찾아온친구 " + target.toString());
					if (target != null) {
						buffer.setLength(0);
						buffer.append(Command.ALLOW_SEARCH + "|" + target.toString());
						send(buffer.toString());
					} else {
						buffer.setLength(0);
						buffer.append(Command.DENY_SEARCH);
						send(buffer.toString());
					}
					break;
				case Command.ADD_FRIENDS:
					// 내전화번호 , 친구전화번호
					System.out.println("친구추가하고싶어 죽겄당!!!");
					int resultNum = dao.addFriend(token.nextToken().toString(), token.nextToken().toString());
					if (resultNum != 0) {
						buffer.setLength(0);
						buffer.append(Command.ALLOW_FRIENDS);
						send(buffer.toString());
					} else {
						buffer.setLength(0);
						buffer.append(Command.DENY_FRIENDS);
						send(buffer.toString());
					}
					break;
				case Command.CREATE_CHATROOM: // 방을만들겠다고 클라이언트가 신청을하면
					String clients = token.nextToken(); // 사람들 목록
					StringTokenizer numbers = new StringTokenizer(clients, Command.CONTENT_DELIMITER);
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
								buffer.append(Command.DEFFUSION_CHATROOM + "|" + roomNumber); // 방번호를
																								// 전송
								users.get(j).send(buffer.toString());
							}
						}
					}
					// 해당방의 정보를 가지고 있는 채트룸을 만듦
					rooms.add(roomNumber, new ChatRoomVO(clients));
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
