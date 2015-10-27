package practice;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Scrollbar extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Scrollbar() {
		JPanel jp = new JPanel();
		Container ct = getContentPane();
		jp.setLayout(new GridLayout(20, 5));
		int b = 1;
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				jp.add(new JButton(b+"번"));
				b++;
			}
			int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
			int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
			JScrollPane jsp = new JScrollPane(jp,v,h);
			ct.add(jsp, BorderLayout.CENTER);
		}
		
	}
	public static void main(String[] args) {
		Scrollbar s= new Scrollbar();
		s.setSize(400,300);
		s.setVisible(true);
		s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
