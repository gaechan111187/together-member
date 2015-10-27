package member;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ClientServiceImpl;

public class MemberUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	ClientServiceImpl client;
	JPanel buttonPanel;
	JTextField field, field1;
	JButton b1, b2;
	
	public MemberUI(ClientServiceImpl client) {
		super("로그인");
		this.client = client;
		setLayout(new GridLayout(0, 1));
		buttonPanel = new JPanel();
		field = new JTextField(10);
		b1 = new JButton("로그인");
		b2 = new JButton("회원가입");
		buttonPanel.add(new JLabel("핸드폰 번호:     "));
		buttonPanel.add(field);
		field1 = new JTextField(10);
		buttonPanel.add(new JLabel("  패스워드:   "));
		buttonPanel.add(field1);
		b1.addActionListener(this);
		buttonPanel.add(b1);
		buttonPanel.add(b2);
		add(buttonPanel);
		pack();
		setLocation(300,200);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "로그인":
			client.requestLogin(field.getText(), field1.getText());
			dispose();
			break;
		case "회원가입":
			new JoinUsUI(client);
			break;
		default:
			break;
		}
	}
	
}


