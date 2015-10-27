package member;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MemberUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	public MemberUI() {
		
		super("로그인");
		
		setLayout(new GridLayout(0, 1));
		JPanel buttonPanel = new JPanel();
		JTextField field = new JTextField(10);
		JButton b1 = new JButton("로그인");
		JButton b2 = new JButton("회원가입");
		buttonPanel.add(new JLabel("핸드폰 번호:     "));
		buttonPanel.add(field);
		JTextField field1 = new JTextField(10);
		buttonPanel.add(new JLabel("패스워드:      "));
		buttonPanel.add(field1);
		
		buttonPanel.add(b1);
		buttonPanel.add(b2);
		
		add(buttonPanel);
		pack();
		setLocation(300,200);
		setVisible(true);
	}

	
	public static void main(String[] args) {
		MemberUI memberUI = new MemberUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}


