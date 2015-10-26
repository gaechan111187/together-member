package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class MainUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		MainUI mt = new MainUI();
	}
	
	JButton btnAddFriend, btnSetUp, btnSearch, btnChat, btnExit;
	//JPanel mainPanel;
	JPanel menuPanel, uMenuPanel, dMenuPanel;	// 메뉴, 위, 아래
	JPanel friendsPanel, f1Panel, f2Panel, f3Panel, f4Panel, f5Panel;	// 친구목록, 친구
	JPanel southPanel;
	JTextField tfSearch;
	JCheckBox ckFriend1, ckFriend2, ckFriend3, ckFriend4, ckFriend5;
	JLabel fname1, fname2, fname3, fname4, fname5;
	JLabel femail1, femail2, femail3, femail4, femail5;
	
	//ImageIcon icon;
	//List<JButton> btns;
	
	public MainUI() {
		init();
	}
	
	private void init() {
		this.setTitle("Together");
		
		//mainPanel = new JPanel(new GridLayout(3, 1));
		
		menuPanel = new JPanel(new GridLayout(2, 1));
		menuPanel.setBorder(LineBorder.createBlackLineBorder());
		uMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		uMenuPanel.setBorder(LineBorder.createBlackLineBorder());
		dMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dMenuPanel.setBorder(LineBorder.createBlackLineBorder());
		
		friendsPanel = new JPanel();		// 친구수에 따라 행 바뀌어야 함
		friendsPanel.setBorder(LineBorder.createBlackLineBorder());
		//Scrollbar scrollbar = new Scrollbar(Scrollbar.VERTICAL, 0,20,0,100);
		//scrollbar.setSize(15,100);
		//scrollbar.setLocation(30, 30);
		//friendsPanel.add(scrollbar);
		
		
	//	friendsPanel.setAutoscrolls(true);						// 자동스크롤생성
		f1Panel = new JPanel(new GridLayout(1, 3));
		f1Panel.setPreferredSize(new Dimension(340,50));
		f1Panel.setBorder(LineBorder.createBlackLineBorder());
		f2Panel = new JPanel(new GridLayout(1, 3));
		f2Panel.setPreferredSize(new Dimension(340,50));
		f2Panel.setBorder(LineBorder.createBlackLineBorder());
		f3Panel = new JPanel(new GridLayout(1, 3));
		f3Panel.setPreferredSize(new Dimension(340,50));
		f3Panel.setBorder(LineBorder.createBlackLineBorder());
		f4Panel = new JPanel(new GridLayout(1, 3));
		f4Panel.setPreferredSize(new Dimension(340,50));
		f4Panel.setBorder(LineBorder.createBlackLineBorder());
		f5Panel = new JPanel(new GridLayout(1, 3));
		f5Panel.setPreferredSize(new Dimension(340,50));
		f5Panel.setBorder(LineBorder.createBlackLineBorder());
		
		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		southPanel.setBorder(LineBorder.createBlackLineBorder());
		//southPanel.setSize(MAXIMIZED_HORIZ, 50);
		
		ImageIcon addFriendIcon = new ImageIcon("src/images/addFriend.jpeg");
		ImageIcon setupIcon = new ImageIcon("src/images/setup.jpeg");
		ImageIcon searchIcon = new ImageIcon("src/images/search.jpeg");
		btnAddFriend = new JButton(addFriendIcon);
		btnSetUp = new JButton(setupIcon);
		btnSearch = new JButton(searchIcon);
		btnChat = new JButton("채팅하기");
		btnExit = new JButton("종료");
		
		tfSearch = new JTextField("Search Name", 20);
		
		fname1 = new JLabel("홍길동");
		fname2 = new JLabel("김유신");
		fname3 = new JLabel("이순신");
		fname4 = new JLabel("강감찬");
		fname5 = new JLabel("고길동");
		
		femail1 = new JLabel("01012341234");
		femail2 = new JLabel("01011111111");
		femail3 = new JLabel("01022222222");
		femail4 = new JLabel("01033333333");
		femail5 = new JLabel("01044444444");
		
		ckFriend1 = new JCheckBox(fname1.getName());
		ckFriend2 = new JCheckBox(fname2.getName());
		ckFriend3 = new JCheckBox(fname3.getName());
		ckFriend4 = new JCheckBox(fname4.getName());
		ckFriend5 = new JCheckBox(fname5.getName());
		
		
		// 조립단계 => 작은것부터 큰것 순으로
		
		
		btnAddFriend.addActionListener(this);
		btnSetUp.addActionListener(this);
		btnSearch.addActionListener(this);
		btnChat.addActionListener(this);
		btnExit.addActionListener(this);
		
		uMenuPanel.add(btnAddFriend);
		uMenuPanel.add(btnSetUp);
		
		dMenuPanel.add(tfSearch);
		dMenuPanel.add(btnSearch);
		
		menuPanel.add(uMenuPanel);
		menuPanel.add(dMenuPanel);
		////////////////////////////////////////
		f1Panel.add(fname1);
		f1Panel.add(femail1);
		f1Panel.add(ckFriend1);
		
		f2Panel.add(fname2);
		f2Panel.add(femail2);
		f2Panel.add(ckFriend2);
		
		f3Panel.add(fname3);
		f3Panel.add(femail3);
		f3Panel.add(ckFriend3);
		
		f4Panel.add(fname4);
		f4Panel.add(femail4);
		f4Panel.add(ckFriend4);
		
		f5Panel.add(fname5);
		f5Panel.add(femail5);
		f5Panel.add(ckFriend5);
		
		friendsPanel.add(f1Panel);
		friendsPanel.add(f2Panel);
		friendsPanel.add(f3Panel);
		friendsPanel.add(f4Panel);
		friendsPanel.add(f5Panel);
		
		southPanel.add(btnChat);
		southPanel.add(btnExit);
		
		for (int i = 0; i < 4; i++) {								// 친구정보받아와서 실행
				JPanel fPanel = new JPanel(new GridLayout(1, 3));
				fPanel.setPreferredSize(new Dimension(340,50));
				fPanel.setBorder(LineBorder.createBlackLineBorder());
				JLabel fname = new JLabel("친구추가");
				JLabel femail = new JLabel("01012341234");
				JCheckBox ckFriend = new JCheckBox(fname.getName());
				fPanel.add(fname);
				fPanel.add(femail);
				fPanel.add(ckFriend);
				friendsPanel.add(fPanel);
					
		}
		
		
		
		/*
		this.add(mainPanel);
		mainPanel.add(menuPanel);
		mainPanel.add(friendsPanel);
		mainPanel.add(southPanel);
		*/
/*
		JScrollPane scrollPane = new JScrollPane(friendsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(getPreferredSize());
		this.add(scrollPane);
*/
		
		this.add(menuPanel,"North");
		this.add(friendsPanel,"Center");
		this.add(southPanel,"South");
		
		this.setBounds(1250, 0, 350, 700); // 300,400은 좌표값, 1200,300길이
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), getHeight());
	}
	

}
