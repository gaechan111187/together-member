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

public class JoinUsUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	ClientServiceImpl client;
	JPanel buttonPanel;
	JButton b1;
	JTextField field, field1, field2, field3;
	
	public JoinUsUI(ClientServiceImpl client) {
		super("회원가입");
		this.client = client;
		setLayout(new GridLayout(0, 1));
		buttonPanel = new JPanel();
		
		b1 = new JButton("회원가입");
		
		buttonPanel.add(new JLabel("이름:     "));
		field = new JTextField(7);
		buttonPanel.add(field);
		
		buttonPanel.add(new JLabel("핸드폰 번호:     "));
		field1 = new JTextField(10);
		buttonPanel.add(field1);
		
		buttonPanel.add(new JLabel("비밀번호:     "));
		field2 = new JTextField(10);
		buttonPanel.add(field2);
		
		buttonPanel.add(new JLabel("이메일: "));
		field3 = new JTextField(20);
		buttonPanel.add(field3);
		
		buttonPanel.add(b1);
		add(buttonPanel);
		pack();
		setLocation(300,200);
		setVisible(true);

	
}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "회원가입":
			//이름 번호 비번 이메일
			client.requestSignUp(field.getText(), field1.getText(), field2.getText(), field3.getText()); 
			break;
		default:
			break;
		}
	}
}

