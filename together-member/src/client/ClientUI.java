package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class ClientUI extends JFrame implements ActionListener{
	public static void main(String[] args) {
		ClientUI ui = new ClientUI();
	}
	private static final long serialVersionUID = 1L;
	private ClientServiceImpl user;
	private JPanel jpMain;
	private JButton jbLogin, jbAdd, jbExit;
	
	public ClientUI(ClientServiceImpl service) {
		super("클라이언트 데모");
		user = service;
		// 부품준비
		init();
		// 조립
		assembly();
	}
	public ClientUI(){
		super("테트스용 버전");
		init();
		assembly();
	}
	
	private void init() {
		jpMain = new JPanel();
		jbLogin = new JButton("로그인");
		jbAdd = new JButton("친구추가");
		jbExit = new JButton("종료");
		
		jbLogin.addActionListener(this);
		jbAdd.addActionListener(this);
		jbExit.addActionListener(this);
	}
	
	private void assembly() {
		jpMain.add(jbLogin);
		jpMain.add(jbAdd);
		jpMain.add(jbExit);
		add(jpMain);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "로그인":
			String phone = JOptionPane.showInputDialog("아이디");
			String password = JOptionPane.showInputDialog("비밀번호");
			user.requestLogin(phone, password); // 로그인 요청
			break;
		case "친구추가":
			break;
		case "종료":
			setVisible(false);
			System.exit(0);
			break;

		default:
			break;
			
		}
	}
}
