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
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

import chat.ChatUI;
import global.Command;
import main.MainUI;
import member.MemberUI;
import member.MemberVO;

public class ClientServiceImpl implements Runnable {
	private String serverIP;
	private Thread thisThread;
	private Socket clientSocket;
	private DataInputStream in;
	private DataOutputStream out;
	private StringBuffer buffer;
	private String name;
	private List<MemberVO> vec;
	MemberUI memUI;
	MainUI mainUI;
	ChatUI chatUI;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClientServiceImpl() {
		try {
			vec = new Vector<MemberVO>();
			serverIP = JOptionPane.showInputDialog("서버IP 설정", "192.168.0.67");
			clientSocket = new Socket(serverIP, Command.PORT);
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
		memUI = new MemberUI(this); // 최초에 로그인 UI를 부름
		Thread currThread = Thread.currentThread();
		while (currThread == thisThread) { // 현재 스레드가 나와 일치하면
			try {
				String command = in.readUTF(); // 명령을 읽어옴
				StringTokenizer token = new StringTokenizer(command, Command.COMMAND_DELIMITER);
				switch (token.nextToken()) {
				case Command.SIGN_UP: // 회원가입
					break;
				case Command.ALLOW_LOGIN: // 로그인 허가
					String content = token.nextToken();
					content = content.replace("[", "");
					content = content.replace("]", "");
					System.out.println("콘텐트 " + content);
					StringTokenizer userToken = new StringTokenizer(content, Command.USER_DELIMETER);
					int size = userToken.countTokens();
					for (int i = 0; i < size; i++) { // 토큰수만큼
						MemberVO temp = new MemberVO();
						StringTokenizer contentToken = new StringTokenizer(userToken.nextToken(),
								Command.CONTENT_DELIMITER);
						temp.setName(contentToken.nextToken().trim());
						temp.setPhone(contentToken.nextToken().trim());
						temp.setPassword(contentToken.nextToken().trim());
						temp.setEmail(contentToken.nextToken().trim());
						vec.add(temp); // 벡터에 추가함
					}
					// 인석이형 UI실행
					memUI.dispose();
					mainUI = new MainUI(this);
					break;
				case Command.DENY_LOGIN: // 로그인 거부
					JOptionPane.showMessageDialog(null, "로그인이 실패하였습니다.");
					break;
				case Command.ADD_FRIENDS:
					break;
				case Command.RECEIVE_MESSAGE:
					break;
				case Command.DEFFUSION_CHATROOM: // 방을만들라는 명령이 오면
					System.out.println("방만들라고 하십니다.");
					int roomNum = Integer.parseInt(token.nextToken());
					mainUI.setRooms(roomNum, new ChatUI(this,roomNum)); // 채팅창을 띄우고 수행함
					break;
				case Command.ALLOW_SIGN_UP:
					JOptionPane.showMessageDialog(null, "회원가입을 성공했습니다.");
					new MemberUI(this);
					break;
				case Command.DENY_SIGN_UP:
					JOptionPane.showMessageDialog(null, "회원가입을 실패했습니다.");
					break;
				case Command.DEFFUSION_MESSAGE: // 메시지를 받음 명령어|룸넘버|내용
					System.out.println("여기까진들어옴");
					mainUI.getRooms().get(Integer.parseInt(token.nextToken())).setArea(token.nextToken() + "\n");
					break;
				case Command.EXIT:
					// 종료버튼 누를시 종료
					break;
				default:
					break;
				}
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String msg, int roomNumber) {
		buffer.setLength(0);
		buffer.append(Command.SEND_MESSAGE + "|" + roomNumber + "|" + msg + "|" + mainUI.getFriends()); // 123은 유저아이디
		send(buffer.toString());
	}

	public void requestLogin(String phone, String password) {
		buffer.setLength(0);
		buffer.append(Command.REQUEST_LOGIN + "|" + phone + "|" + password);
		String temp = buffer.toString();
		System.out.println(temp);
		send(temp);
	}

	// 이름 번호 비번 이메일
	// 회원가입을 위해서
	public void requestSignUp(String name, String phone, String password, String email) {
		buffer.setLength(0);
		buffer.append(Command.SIGN_UP + "|" + name + "`" + phone + "`" + password + "`" + email);
		String temp = buffer.toString();
		System.out.println(temp);
		send(temp);
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

	public List<MemberVO> getVec() {
		return vec;
	}

	public void creatChatRoom(String friends) {
		// 서버로 채팅창 만들겠다고 전송함
		buffer.setLength(0);
		buffer.append(Command.CREATE_CHATROOM + "|" + friends); // 명령어와 친구목록을 전송해줌
		send(buffer.toString());
	}
}
