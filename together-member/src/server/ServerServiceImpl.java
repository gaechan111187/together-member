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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import global.Command;
import member.MemberVO;

public class ServerServiceImpl implements Runnable {
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	private StringBuffer buffer;
	private ServerDAO dao;
	private Thread thisThread;
	private static int roomNumber = 0;
	private static Map<Integer, ChatRoomVO> rooms;
	private StringBuffer room;
	private String phone;
	private static List<ServerServiceImpl> users = new Vector<ServerServiceImpl>();
	private boolean flag;
	private int tempNum;
	private String msg;
	private String names;
	private String user;
	private int length;
	private StringTokenizer secondToken;

	public void setThisThread(Thread thisThread) {
		this.thisThread = thisThread;

	}
	public ServerServiceImpl() {
	}

	public ServerServiceImpl(Socket clientSocket) {
		flag = true;
		tempNum = 0;
		user = "";
		secondToken = null;
		length = 0;
		msg = "";
		room = new StringBuffer();
		rooms = new HashMap<Integer, ChatRoomVO>();
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
				if (command.equals("")) {
					continue;
				}
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
					flag = true;
					break;
				case Command.SEND_MESSAGE: // 명령어|방번호|내이름>>메시지
					tempNum = Integer.parseInt(token.nextToken()); // 방번호
					msg = token.nextToken(); // 메시지
					System.out.println("방번호가 뭐길래 " + tempNum);
					names = rooms.get(tempNum).getClients();
					System.out.println("명단 " + names);
					secondToken = new StringTokenizer(names, Command.CONTENT_DELIMITER); // 친구목록
					length = secondToken.countTokens();
					for (int i = 0; i < length; i++) {
						user = secondToken.nextToken();
						System.out.println("유저는 " + user);
						for (int j = 0; j < users.size(); j++) {
							if (user.equals(users.get(j).phone)) {
								System.out.println(user + " 같습니다.");
								buffer.setLength(0);
								buffer.append(Command.DEFFUSION_MESSAGE + "|" + tempNum + "|" + msg);
								users.get(j).send(buffer.toString());
							}
						}
					}
					break;
				case Command.SEND_SEVER: // 명령어|방번호|내이름>>메시지
					tempNum = Integer.parseInt(token.nextToken()); // 방번호
					msg = token.nextToken(); // 메시지
					StringTokenizer realmsg = new StringTokenizer(msg, ">>");
					length = realmsg.countTokens();
					for (int i = 0; i < length; i++) {
						msg = realmsg.nextToken();
					}
					System.out.println("방번호가 뭐길래 " + tempNum);
					names = rooms.get(tempNum).getClients();
					System.out.println("명단 " + names);
					secondToken = new StringTokenizer(names, Command.CONTENT_DELIMITER); // 친구목록
					length = secondToken.countTokens();
					for (int i = 0; i < length; i++) {
						user = secondToken.nextToken();
						System.out.println("유저는 " + user);
						for (int j = 0; j < users.size(); j++) {
							if (user.equals(users.get(j).phone)) {
								System.out.println(user + " 같습니다.");
								buffer.setLength(0);
								buffer.append(Command.DEFFUSION_MESSAGE + "|" + tempNum + "|" + "<서버> " + msg);
								users.get(j).send(buffer.toString());
							}
						}
					}
					break;
				case Command.DEL_FRIEND: //명령어|내폰|친구폰들
					String myPhone = token.nextToken();
					secondToken = new StringTokenizer(token.nextToken(), Command.CONTENT_DELIMITER);
					length = secondToken.countTokens();
					int temp = 0;
					for (int i = 0; i < length; i++) {
						 temp = dao.deleteFriend(myPhone, secondToken.nextToken());
					}
					if (temp != 0) {
						buffer.setLength(0);
						buffer.append(Command.ALLOW_DEL);
						send(buffer.toString());
					} else {
						buffer.setLength(0);
						buffer.append(Command.DENY_DEL);
						send(buffer.toString());
					}
					break;
				case Command.SIGN_UP:
					secondToken = new StringTokenizer(token.nextToken(), Command.CONTENT_DELIMITER);
					System.out.println(secondToken);
					int result = dao.confirmSignUp(secondToken.nextToken(), secondToken.nextToken(),
							secondToken.nextToken(), secondToken.nextToken());
					if (result != 0) {
						buffer.setLength(0);
						buffer.append(Command.ALLOW_SIGN_UP);
						send(buffer.toString());
					} else {
						buffer.setLength(0);
						buffer.append(Command.DENY_SIGN_UP);
						send(buffer.toString());
					}
					secondToken = null;
					break;
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
					tempNum = dao.addFriend(token.nextToken().toString(), token.nextToken().toString());
					if (tempNum != 0) {
						buffer.setLength(0);
						buffer.append(Command.ALLOW_FRIENDS);
						send(buffer.toString());
					} else {
						buffer.setLength(0);
						buffer.append(Command.DENY_FRIENDS);
						send(buffer.toString());
					}
					break;
				case Command.CREATE_CHATROOM: // 명령어|사람목록
					names = token.nextToken(); // 사람들 목록
					secondToken = new StringTokenizer(names, Command.CONTENT_DELIMITER);
					length = secondToken.countTokens();
					int count = 0;
					for (int i = 0; i < length; i++) {
						System.out.println("수행함");
						user = secondToken.nextToken();
						System.out.println("방을만들 이름은 " + user); // 친구 스레드명
						for (int j = 0; j < users.size(); j++) {
							if (user.equals(users.get(j).phone)) {
								count++;
							}
						}
					}
					secondToken = new StringTokenizer(names, Command.CONTENT_DELIMITER);
					length = secondToken.countTokens();
					if (count < 2) {
						buffer.setLength(0);
						buffer.append(Command.DENY_CHATROOM + "|");
						send(buffer.toString());
					} else {
						for (int i = 0; i < length; i++) {
							user = secondToken.nextToken();
							System.out.println("카운트수 " + count);
							System.out.println("유저수는 : " + users.size());
							for (int j = 0; j < users.size(); j++) { // 유저목록에서
								System.out.println("유저 " + users.get(j).phone);
								if (user.equals(users.get(j).phone)) {
									System.out.println("같아서 수행 " + users.get(j).phone);
									buffer.setLength(0);
									buffer.append(Command.DEFFUSION_CHATROOM + "|" + roomNumber); // 방번호를
									users.get(j).send(buffer.toString());
									break;
								}
							}
						}
						rooms.put(roomNumber, new ChatRoomVO(names, length)); // 방을
						roomNumber++;
					}
					// 해당방의 정보를 가지고 있는 채트룸을 만듦
					break;
				case Command.EXIT_CHATROOM: // 명령어 | 방번호 | 폰번 | 내이름

					tempNum = Integer.parseInt(token.nextToken()); // 방번호
					phone = token.nextToken(); // 폰번호
					String myName = token.nextToken(); // 이름
					rooms.get(tempNum).delClients(phone); // 방번호의 명단에서 해당폰번호 삭제
					if (rooms.get(tempNum).getNumOfUser() == 0) {
						rooms.remove(tempNum);
						System.out.println("방이 폭파되었습니다.");
					} else {
						System.out.println("방번호가 뭐길래 " + tempNum);
						names = rooms.get(tempNum).getClients(); // 방번호의 명단을 가져옴
						System.out.println("명단 " + names);
						secondToken = new StringTokenizer(names, Command.CONTENT_DELIMITER); // 친구목록
						length = secondToken.countTokens();
						for (int i = 0; i < length; i++) {
							user = secondToken.nextToken();
							System.out.println("유저는 " + user);
							for (int j = 0; j < users.size(); j++) {
								if (user.equals(users.get(j).phone)) {
									System.out.println(user + " 같습니다.");
									buffer.setLength(0);
									buffer.append(Command.DEFFUSION_MESSAGE + "|" + tempNum + "|" + "<서버> " + myName
											+ "님이 퇴실하셨습니다.");
									users.get(j).send(buffer.toString());
								}
							}
						}
					}
					break;
				case Command.LOGOUT:
					String delphone = token.nextToken();
					System.out.println("삭제할 폰번");
					buffer.setLength(0);
					buffer.append(Command.LOGOUT);
					send(buffer.toString());
					length = users.size();
					for (int i = 0; i < length; i++) {
						if (users.get(i).phone.equals(delphone)) {
							release();
							users.remove(i);
							break;
						}
					}
					break;
				default:
					break;
				}
				Thread.sleep(200);
			}
		} catch (Exception e) {
			System.out.println("무슨문제냥 : " + e);
			e.printStackTrace();
		} finally {
			return;
		}
	}

	private StringTokenizer StringTokenizer(String nextToken, String contentDelimiter) {
		// TODO Auto-generated method stub
		return null;
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

	private void release() {
		if (thisThread != null) {
			thisThread = null;
		}
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
			if (client != null) {
				client.close();
			}
		} catch (Exception e) {
		} finally {
			out = null;
			in = null;
			client = null;
		}
	}
}
