package member;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MemberUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextField fieldPhone, fieldPass;
	private JButton b1, b2;
	private JPanel panel;
	public MemberUI() {
		super("로그인");
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		b1 = new JButton("로그인");
		b2 = new JButton("회원가입");
		
		panel.add(new JLabel("    핸드폰 번호:")); //판넬에다 "핸드폰 번호" 라벨 in 
		fieldPhone = new JTextField(10);
		panel.add(fieldPhone);
		
		panel.add(new JLabel("    패스워드: ")); //판넬에다 "패스워드" 라벨 in
		fieldPass = new JTextField(10);
		panel.add(fieldPass);
		
		panel.add(b1); //판넬에다 버튼1 붙임.
		panel.add(b2); //판넬에다 버튼2 붙임.
		add(panel); //프레임에다 판넬 붙임.
		b2.addActionListener(this);
		pack();
		setLocation(700,350);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	
	public static void main(String[] args) {
		MemberUI memberUI = new MemberUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "회원가입":
			this.dispose();
			JoinUsUI ui = new JoinUsUI();
			break;

		default:
			break;
		}
	}
	
}


