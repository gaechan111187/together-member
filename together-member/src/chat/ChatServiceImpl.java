package chat;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

	public class ChatServiceImpl extends JFrame implements ActionListener, Runnable, ItemListener {
		public static void main(String[] args) {
			try {
				ChatServiceImpl cc = new ChatServiceImpl();
				Thread tr = new Thread(cc);
				tr.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private static final long serialVersionUID = 1L;
		JList list;
		Canvas canvas;
		BufferStrategy strategy;
		JPanel jp1, jp2, jp3, jp4, jp5, jp6, jp7, jp8, jp9, jp10, jp11, jp12, jp13;
		JLabel lblName, lblConnector, lblCount, lblNum, lblServerIp, lblServerSelect;
		JTextField txtName, txtChange, txtServer, txtMsg;
		JButton btnEnter, btnChange, btnCancel, btnSend, btnExit;
		CheckboxGroup group;
		JTextArea area;
		Choice choiceMsg, choiceIP;
		Checkbox boxPublic,boxPrivate;
		Calendar now;
		String time;
		int hh,mm,ss,count,ran; // 시, 분, 초, 접속자수
		OutputStream out;
		BufferedReader in;
		// 서버는 소켓과 연관관계를 맺지 않지만
		// 클라이언트는 반드시소켓과 한몸이어야 한다.(연관관계가 필요 클라이언트가 죽으면, 소켓도 죽음)
		Socket socket;
		

		public ChatServiceImpl() throws IOException {
			super("채팅프로그램");
			// 부품준비
			init();
			// 조립과정
			assembly();
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임에 닫기버튼 장착
			this.pack();
			this.setBounds(50, 100, 420, 540); // x, y, 가로, 세로
			this.setVisible(true);
		}

		private void assembly() {
			jp1.add(txtName);
			jp1.add(btnEnter);
			jp4.add(area, "Center");
			jp3.add(txtMsg);
			jp3.add(btnSend);
			jp4.add(jp3, "South");
			jp9.add(jp4);
			jp6.add(list);
			jp8.add(jp6, "Center");
			jp7.add(btnExit);
			jp8.add(jp7, "South");
			jp11.add(jp12, "North");
			jp11.add(jp13, "South");
			jp12.add(lblServerIp);
			jp12.add(txtServer);
			jp13.add(lblServerSelect);
			// jp13.add(choiceIP);
			jp10.add(jp11, "North");
			jp10.add(jp8, "South");
			this.getContentPane().add(jp9, "West");
			this.getContentPane().add(canvas, "Center");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false); // 창 크기 변경 불가
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			jp2.setBackground(Color.WHITE);
			jp3.setBackground(Color.WHITE);
			jp4.setBackground(Color.WHITE);
			jp5.setBackground(Color.WHITE);
			jp6.setBackground(Color.WHITE);
			jp7.setBackground(Color.WHITE);
			jp8.setBackground(Color.WHITE);
			area.setBackground(Color.WHITE);
			area.setFont(new Font("굴림체", Font.BOLD, 16)); // 글자크기 16 진하게
			btnEnter.addActionListener(this);
			btnSend.addActionListener(this);
			btnExit.addActionListener(this);
			txtMsg.addActionListener(this);
			txtName.addActionListener(this);
			txtChange.addActionListener(this);
			txtServer.addActionListener(this);
			this.pack();
			this.setLocationRelativeTo(null);
		}

		public void init() throws IOException {
			list = new JList();
			canvas = new Canvas(); // 캔버스는 버퍼를 생산하는 객체
			canvas.setSize(400, 0); // width, height
			// canvas.createBufferStrategy(2); // 2개의 버퍼를 생산하고
			strategy = canvas.getBufferStrategy(); // 만들어진 버퍼를 가져와라.
			for (int i = 0; i < 20; i++) {
				ran = (int) (Math.random() * 20 + 1);
				// image = ImageIO.read(new File("src/image" + ran + ".jpg"));
			}

			// choiceMsg.addItem("안녕하세요");
			// choiceMsg.addItem("Hello");
			// choiceMsg.addItem("방가방가");
			// choiceIP.add("192.168.57.2");
			jp1 = new JPanel();
			jp2 = new JPanel();
			jp3 = new JPanel();
			jp4 = new JPanel(new BorderLayout());
			jp5 = new JPanel();
			jp6 = new JPanel();
			jp7 = new JPanel();
			jp8 = new JPanel(new BorderLayout());
			jp9 = new JPanel();
			jp10 = new JPanel(new BorderLayout());
			jp11 = new JPanel(new BorderLayout());
			jp12 = new JPanel();
			jp13 = new JPanel();
			lblServerIp = new JLabel("서버IP");
			
			lblServerSelect = new JLabel("서버주소선택");
			txtName = new JTextField(50);
			txtChange = new JTextField(10);
			txtServer = new JTextField(50);
			txtMsg = new JTextField(30);
			btnEnter = new JButton("입장");
			btnSend = new JButton("전송");
			btnExit = new JButton("나가기");
			boxPublic = new Checkbox("PUBLIC", group, true); // 모두에게
			area = new JTextArea(25, 1);
			OutputStream out;
			BufferedReader in;
			Socket socket;
			JTextArea txt;
			JButton btn;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "입장":
				this.connect();
				break;
			case "대화명변경":
				this.change();
				break;
			case "지우개":
				area.setText("");
				break;
			case "전송":
				this.sendProcess();
				break;
			case "나가기":
				this.exit();
				break;

			default:
				break;
			}
		}

		////////////////////////////////////////////

		public void fileSave() {
			FileDialog fd = new FileDialog(this, "대화내용 저장", FileDialog.SAVE);
			fd.show();
			String dir = fd.getDirectory();
			String file = fd.getFile();
			if (dir == null || file == null)
				return;
			File f = new File(dir + file);
			try {
				PrintWriter pw = new PrintWriter(f);
				pw.println(area.getText());
				pw.close();
				area.append("대화내용이 저장되었습니다\r\n");
			} catch (Exception e) {
			}
		}// end

		public void fileOpen() {
			FileDialog fd = new FileDialog(this, "파일 열기", FileDialog.LOAD);
			fd.show();
			String dir = fd.getDirectory();
			String file = fd.getFile();
			if (dir == null || file == null)
				return;
			try {
				FileReader fr = new FileReader(dir + file);
				BufferedReader br = new BufferedReader(fr);
				while (true) {
					String data = "";
					data = br.readLine();
					if (data == null)
						break;
					// area.append(data+"\n");
					out.write((txtName.getText() + data + "\n").getBytes());
				}
			} catch (Exception e) {
			}
		}// end

		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == choiceMsg && e.getStateChange() == ItemEvent.SELECTED) {
				String msg = txtMsg.getText();
				try {
					if (boxPublic.getState() == true) {
						out.write((msg + e.getItem() + "\n").getBytes());
					} else if (boxPrivate.getState() == true) {
					//	String name = list.getSelectedItem();
						String name = null;
						out.write(("/s" + name + "-" + msg + e.getItem() + "\n").getBytes());
						area.append("(귓속말)" + name + "님께>> " + msg + e.getItem() + "\r\n");
					}
				} catch (Exception ex) {
				}
				txtMsg.setText("");
				txtMsg.requestFocus();
			}
			if (e.getSource() == choiceIP && e.getStateChange() == ItemEvent.SELECTED) {
				txtServer.setText("");
				String sv = txtServer.getText();
				try {
					txtServer.setText((sv + e.getItem()));
				} catch (Exception ex) {
				}
				txtName.requestFocus();
			}
		}// end

		public void exit() {
			try {
				out.write(("/q" + txtName.getText() + "\n").getBytes());
				System.out.println("보냄 : " + txtName.getText());
				area.append("****" + txtName.getText() + "님이 퇴장하셨습니다****\r\n");
				in.close();
				out.close();
				socket.close();
			//	setList();
			} catch (Exception ex) {
				System.out.println("보내기 오류 " + ex.getMessage());
			}
			area.setEnabled(false);
			txtMsg.setEnabled(false);
			btnExit.setEnabled(false);
			txtName.setEnabled(true);
			btnEnter.setEnabled(true);
			txtName.setText("");
			txtName.requestFocus();
			System.exit(1);
		}// end

		public void sendProcess() {
			String str = txtMsg.getText();
			try {
				if (str.charAt(0) == 'q') { // q이면 서버 종료??뭐야 이게.....
					out.write(("/q" + txtName.getText() + "\n").getBytes()); // 서버로
																				// 대화명
																				// 보내고
																				// in,
																				// out,
																				// socket
																				// 다
																				// 닫음
					System.out.println("서버로 보냄 >> " + txtName.getText() + " \n");
					in.close();
					out.close();
					socket.close();
					System.exit(0);
				}
				if (boxPublic.getState() == true) { // 모두에게 메시지 보내기
					out.write((txtMsg.getText() + "\n").getBytes());
					System.out.println("메시지를 서버로 보냄 >> " + txtMsg.getText());
				} else if (boxPrivate.getState() == true) { // 한사람에게 귓속말 보내기
					try {
				//		String name = list.getSelectedItem();
						String name = null;
						if (name == null || name == "") {
							area.append(">>>대상을 찾을 수 없습니다" + "\r\n");
						} else {
							out.write(("/s" + name + "-" + txtMsg.getText() + "\n").getBytes());
							System.out.println("귓속말전달 >> /s" + name + "-" + txtMsg.getText());
							area.append("(귓속말)" + name + "님께>> " + txtMsg.getText() + "\r\n");
						}
					} catch (Exception e) {
						area.append(e.getMessage());
					}
				}
				txtMsg.setText("");
				txtMsg.requestFocus();
			} catch (Exception e) {
				area.append(e.getMessage());
			}
		}// end

		public void connect() { // 대화명을 서버로 보내서 접속자에 등록.
			area.setEnabled(true);
			txtMsg.setEnabled(true);
			btnExit.setEnabled(true);
			try {
				String serverIP = txtServer.getText();
				socket = new Socket(serverIP, 5555); // 소켓지정
				in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 서버에서stream(접속자들 메시지등등을 가져옴
				out = socket.getOutputStream(); // 서버로 보냄
				out.write((txtName.getText() + "\n").getBytes()); // 접속자 대화명을 바이트로해서 서버에 내보냄
				System.out.println("서버로 보냄 >> " + txtName.getText());
				txtName.setEnabled(false);
				btnEnter.setEnabled(false);
				new Thread(this).start(); // 다른 클라이언트들의 메시지를 서버로부터 가져옴
				txtMsg.requestFocus();
			} catch (Exception e) {
				area.append(e.getMessage());
			} // 소켓에서 발생한 예외오류메시지를 출력하는듯...
		}// end

		public void change() {
			try {
				String msg = txtChange.getText();
				out.write(("/n " + msg + "\n").getBytes());
				txtChange.setText("");
				txtMsg.requestFocus();
			} catch (Exception ex) {
			}
		}// end

		public void run() {
			while (true) {
				now = Calendar.getInstance();
				hh = now.get(now.HOUR_OF_DAY);
				mm = now.get(now.MINUTE);
				ss = now.get(now.SECOND);
				time = hh + ":" + mm + ":" + ss;
				// 서버로 내 메시지를 보내고, 서버에서 다른 클라이언트들의 메시지를 가져옴
				try {
					String msg = in.readLine();
					System.out.println("서버로부터 읽음 : " + msg);

					if (msg == null)
						return;
					if (msg.charAt(0) == '/') {
						if (msg.charAt(1) == 'c') { // c, s, 이런건 서버에서 설정해주는거임
						//	list.replaceItem(msg.substring(2), count); // list에 msg.substring(2)값을 넣고, list index자리 지정
							count++;
						//	num.setText(String.valueOf(count)); // 인원 수 변경
							area.append("****" + msg.substring(2) + "님이 입장하셨습니다****\r\n"); // 모든 클라이언트에게 입장을 알림
							txtName.setEnabled(false); // 대화명 label 비활성
							btnEnter.setEnabled(false); // 접속버튼 비활성
						}

						else if (msg.charAt(1) == 'q') { // 나가기버튼 누르거나 /q를 입력하면 서버에 q가 입력이 돼. 왜 그렇게 되지? 퇴장
							String str = msg.substring(2);
							area.append("****" + str + "님이 퇴장하셨습니다****\r\n");
//							for (int i = 0; i < list.getItemCount(); i++) { // list목록에서 퇴장하는 대화명찾아 돌려서 remove
//								if (str.equals(list.getItem(i))) {
//									list.remove(i);
//									count--;
//									num.setText(String.valueOf(count));
//									break;
//								}
//							}
							// return; 얘 때문에 누군가가 퇴장을 하면 내 메시지가 서버로만 가고 클라이언트로 안
							// 뿌려줌...
						} else if (msg.charAt(1) == 'n') { // 대화명변경
							String oldname = msg.substring(2, msg.indexOf('-'));
							String newname = msg.substring(msg.indexOf('-') + 1);
							area.append("*" + oldname + "님의 이름이  " + newname + "으로 변경되었습니다.*\r\n");

//							for (int j = 0; j < count; j++) {
//								if (oldname.equals(list.getItem(j))) {
//									list.replaceItem(newname, j);
//									break;
//								}
//							}
						}
					} else
						area.append("[" + time + "] " + msg + "\r\n");
				} catch (Exception e) {
					area.append(e.getMessage());
				}
			}
		}// end

	}