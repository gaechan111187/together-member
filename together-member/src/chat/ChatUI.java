package chat;

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

	private static final long serialVersionUID = 1L;
	private JPanel textField;
	private JButton jbOk;
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
		area = new JTextArea();
		field = new JTextField(25);
	}

	private void assembly() {
		field.addKeyListener(this);
		jbOk.addActionListener(this);
		textField.add(field);
		textField.add(jbOk);
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

