package chat;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatUI extends JFrame implements ActionListener, KeyListener{
	public static void main(String[] args) {
		new ChatUI();
	}

	private static final long serialVersionUID = 1L;
	private JPanel textField;
	private JButton jbOk,jbClear,jbExit;
	private JTextArea area;
	private JTextField field;

	
	public ChatUI() {
		super("테스트용 채팅");
		// 부품준비
		init();
		// 조립
		assembly();
	}

	private void init() {
		textField = new JPanel();
		jbOk = new JButton("확인");
		jbClear = new JButton("지우개");
		jbExit = new JButton("종료");
		area = new JTextArea();
		field = new JTextField(25);
	}

	private void assembly() {
		setLayout(new GridLayout(2, 1));
		field.addKeyListener(this);
		jbOk.addActionListener(this);
		jbClear.addActionListener(this);
		jbExit.addActionListener(this);
		textField.add(field);
		textField.add(jbOk);
		textField.add(jbClear);
		textField.add(jbExit);
		area.setEditable(false);
		add(area, "Center");
		add(textField, "South");
		pack();
		setBounds(100,100,400,500);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "확인":
			area.append(field.getText() + "\n");
			field.setText("");
			//서버로 전송
			break;

		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			jbOk.doClick();
			break;

		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}

