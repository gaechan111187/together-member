package member;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JoinUsUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public JoinUsUI() {
		super("회원가입");
		setLayout(new GridLayout(0, 1));
		JPanel jPanel = new JPanel();
		
		JButton b1 = new JButton("회원가입");
		
		jPanel.add(new JLabel("이름:     "));
		JTextField field = new JTextField(7);
		jPanel.add(field);
		
		jPanel.add(new JLabel("핸드폰 번호:     "));
		JTextField field1 = new JTextField(10);
		jPanel.add(field1);
		
		jPanel.add(new JLabel("비밀번호:     "));
		JTextField field2 = new JTextField(10);
		jPanel.add(field2);
		
		jPanel.add(new JLabel("이메일: "));
		JTextField field3 = new JTextField(20);
		jPanel.add(field3);
		
		jPanel.add(b1);
		add(jPanel);
		pack();
		setLocation(300,200);
		setVisible(true);

	
}
	
	public static void main(String[] args) {
		JoinUsUI joinUsUI = new JoinUsUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

