package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import client.ClientServiceImpl;
import member.MemberVO;

public class MainUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JButton btnAddFriend, btnSetUp, btnSearch, btnChat, btnExit;
	// JPanel mainPanel;
	JPanel menuPanel, uMenuPanel, dMenuPanel; // 메뉴, 위, 아래
	JPanel friendsPanel, f1Panel, f2Panel, f3Panel, f4Panel, f5Panel; // 친구목록,
																		// 친구
	JPanel southPanel;
	JTextField tfSearch;
	JCheckBox ckFriend1, ckFriend2, ckFriend3, ckFriend4, ckFriend5;
	JLabel fname1, fname2, fname3, fname4, fname5;
	JLabel femail1, femail2, femail3, femail4, femail5;
	ClientServiceImpl client;
	List<MemberVO> vec;

	MainService service = MainServiceImpl.getService();

	// ImageIcon icon;
	// List<JButton> btns;

	public MainUI(ClientServiceImpl client) {
		vec = new Vector<MemberVO>();
		this.client = client;
		init();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void init() {

		this.setTitle("Together");

		// mainPanel = new JPanel(new GridLayout(3, 1));

		menuPanel = new JPanel(new GridLayout(2, 1));
		menuPanel.setBorder(LineBorder.createBlackLineBorder());
		uMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		uMenuPanel.setBorder(LineBorder.createBlackLineBorder());
		dMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dMenuPanel.setBorder(LineBorder.createBlackLineBorder());

		friendsPanel = new JPanel(new GridLayout(10, 1)); // 친구수에 따라 행 바뀌어야 함
		// friendsPanel.setLayout(new BoxLayout(friendsPanel,BoxLayout.Y_AXIS));
		friendsPanel.setBorder(LineBorder.createBlackLineBorder());
		// friendsPanel.setPreferredSize(new Dimension(350,500));
		// friendsPanel.
		// friendsPanel.setAutoscrolls(true);

		/*
		 * Scrollbar scrollbar = new Scrollbar(Scrollbar.VERTICAL, 0,20,0,0);
		 * scrollbar.setSize(15,500); scrollbar.setLocation(300, 30);
		 * friendsPanel.add(scrollbar);
		 */
		JScrollPane scrollPane = new JScrollPane(friendsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// scrollPane.setPreferredSize(new Dimension(100, 100));
		this.add(scrollPane, BorderLayout.EAST);
		// friendsPanel.add(scrollPane);

		friendsPanel.setAutoscrolls(true); // 자동스크롤생성
		f1Panel = new JPanel(new GridLayout(1, 3));
		f1Panel.setPreferredSize(new Dimension(320, 50));
		f1Panel.setBorder(LineBorder.createBlackLineBorder());
		f2Panel = new JPanel(new GridLayout(1, 3));
		f2Panel.setPreferredSize(new Dimension(320, 50));
		f2Panel.setBorder(LineBorder.createBlackLineBorder());
		f3Panel = new JPanel(new GridLayout(1, 3));
		f3Panel.setPreferredSize(new Dimension(320, 50));
		f3Panel.setBorder(LineBorder.createBlackLineBorder());
		f4Panel = new JPanel(new GridLayout(1, 3));
		f4Panel.setPreferredSize(new Dimension(320, 50));
		f4Panel.setBorder(LineBorder.createBlackLineBorder());
		f5Panel = new JPanel(new GridLayout(1, 3));
		f5Panel.setPreferredSize(new Dimension(320, 50));
		f5Panel.setBorder(LineBorder.createBlackLineBorder());

		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		southPanel.setBorder(LineBorder.createBlackLineBorder());
		// southPanel.setSize(MAXIMIZED_HORIZ, 50);

		ImageIcon addFriendIcon = new ImageIcon("src/images/addFriend.jpeg");
		ImageIcon setupIcon = new ImageIcon("src/images/setup.jpeg");
		ImageIcon searchIcon = new ImageIcon("src/images/search.jpeg");

		btnAddFriend = new JButton(addFriendIcon);
		btnAddFriend.setName("addFrined");
		btnAddFriend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// AddFriend addFriend = new AddFriend();

			}
		});
		btnSetUp = new JButton(setupIcon);
		btnSetUp.setName("setup");
		btnSetUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnSearch = new JButton(searchIcon);
		btnSearch.setName("search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				friendsPanel.removeAll();
				for (MemberVO mem : vec) {
					if (mem.getName().equals(tfSearch.getText())) {
						JPanel fPanel = new JPanel(new GridLayout(1, 3));
						fPanel.setPreferredSize(new Dimension(MAXIMIZED_HORIZ, 50));
						fPanel.setBorder(LineBorder.createBlackLineBorder());
						JLabel fname = new JLabel(mem.getName());
						JLabel femail = new JLabel(mem.getEmail());
						JCheckBox ckFriend = new JCheckBox(fname.getName());
						fPanel.add(fname);
						fPanel.add(femail);
						fPanel.add(ckFriend);
						friendsPanel.add(fPanel);
					}
				}
				// getRootPane().add(friendsPanel, "Center");
				// friendsPanel.setVisible(true);
				friendsPanel.repaint();
				init();
			}
		});
		btnChat = new JButton("채팅하기");
		btnExit = new JButton("종료");

		tfSearch = new JTextField(20);
		/*
		 * fname1 = new JLabel("홍길동"); fname2 = new JLabel("김유신"); fname3 = new
		 * JLabel("이순신"); fname4 = new JLabel("강감찬"); fname5 = new
		 * JLabel("고길동");
		 * 
		 * femail1 = new JLabel("01012341234"); femail2 = new
		 * JLabel("01011111111"); femail3 = new JLabel("01022222222"); femail4 =
		 * new JLabel("01033333333"); femail5 = new JLabel("01044444444");
		 * 
		 * ckFriend1 = new JCheckBox(fname1.getName()); ckFriend2 = new
		 * JCheckBox(fname2.getName()); ckFriend3 = new
		 * JCheckBox(fname3.getName()); ckFriend4 = new
		 * JCheckBox(fname4.getName()); ckFriend5 = new
		 * JCheckBox(fname5.getName());
		 */

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

		/*
		 * f1Panel.add(fname1); f1Panel.add(femail1); f1Panel.add(ckFriend1);
		 * 
		 * f2Panel.add(fname2); f2Panel.add(femail2); f2Panel.add(ckFriend2);
		 * 
		 * f3Panel.add(fname3); f3Panel.add(femail3); f3Panel.add(ckFriend3);
		 * 
		 * f4Panel.add(fname4); f4Panel.add(femail4); f4Panel.add(ckFriend4);
		 * 
		 * f5Panel.add(fname5); f5Panel.add(femail5); f5Panel.add(ckFriend5);
		 * 
		 * friendsPanel.add(f1Panel); friendsPanel.add(f2Panel);
		 * friendsPanel.add(f3Panel); friendsPanel.add(f4Panel);
		 * friendsPanel.add(f5Panel);
		 */

		southPanel.add(btnChat);
		southPanel.add(btnExit);

		vec = client.getVec(); // 친구목록 맨마지막은 나
		int size = vec.size() - 1;
		System.out.println("사이즈는 " + size);
		if (size != 0) {
			for (int i = 0; i < size; i++) { // 친구정보받아와서 실행
				for (MemberVO mem : vec) {
					JPanel fPanel = new JPanel(new GridLayout(1, 3));
					fPanel.setPreferredSize(new Dimension(MAXIMIZED_HORIZ, 50));
					fPanel.setBorder(LineBorder.createBlackLineBorder());
					JLabel fname = new JLabel(mem.getName());
					JLabel femail = new JLabel(mem.getEmail());
					JCheckBox ckFriend = new JCheckBox(fname.getName());
					fPanel.add(fname);
					fPanel.add(femail);
					fPanel.add(ckFriend);
					friendsPanel.add(fPanel);
				}
			}
		} else {
			JPanel fPanel = new JPanel();
			JLabel no = new JLabel("친구를 등록해주세요.");
			fPanel.add(no);
			friendsPanel.add(fPanel);
		}

		/*
		 * this.add(mainPanel); mainPanel.add(menuPanel);
		 * mainPanel.add(friendsPanel); mainPanel.add(southPanel);
		 */

		// JScrollPane scrollPane = new
		// JScrollPane(friendsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// scrollPane.setPreferredSize(getPreferredSize());
		// this.add(scrollPane);

		this.add(menuPanel, "North");
		this.add(friendsPanel, "Center");
		this.add(southPanel, "South");
		this.setBounds(1250, 0, 350, 700); // 300,400은 좌표값, 1200,300길이
		// this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/*
	 * // 화면 중앙에 스윙 띄우기 Dimension screen =
	 * Toolkit.getDefaultToolkit().getScreenSize(); Dimension frm =
	 * this.getSize(); int xpos = (int) (screen.getWidth()/2-frm.getWidth()/2);
	 * int ypos = (int) (screen.getHeight()/2-frm.getHeight()/2);
	 * this.setLocation(xpos,ypos); this.setResizable(false);
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "addFriendIcon":
			System.exit(0);
			break;
		case "setupIcon":
			break;
		case "search":
			System.exit(0);
			break;
		case "채팅하기":

			break;
		case "종료":
			System.exit(0);
			break;
		default:
			break;
		}
	}
}
/*
 * class AddFriend extends JFrame implements ActionListener { private static
 * final long serialVersionUID = 1L;
 * 
 * JButton btnAddFriend, btnSearch, btnExit; JPanel menuPanel, uMenuPanel,
 * dMenuPanel; // 메뉴, 위, 아래 JPanel friendsPanel; JPanel southPanel; JTextField
 * tfSearch; JLabel lbphone;
 * 
 * MainService service = MainServiceImpl.getService();
 * 
 * public AddFriend() { init(); } public void init(){ this.setTitle("친구추가");
 * 
 * menuPanel = new JPanel();
 * menuPanel.setBorder(LineBorder.createBlackLineBorder());
 * 
 * friendsPanel = new JPanel(new GridLayout(5, 1)); // 친구수에 따라 행 바뀌어야 함
 * friendsPanel.setBorder(LineBorder.createBlackLineBorder()); JScrollPane
 * scrollPane = new
 * JScrollPane(friendsPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.
 * HORIZONTAL_SCROLLBAR_NEVER); this.add(scrollPane,BorderLayout.EAST);
 * 
 * southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
 * southPanel.setBorder(LineBorder.createBlackLineBorder());
 * 
 * ImageIcon searchIcon = new ImageIcon("src/images/search.jpeg");
 * 
 * 
 * btnSearch = new JButton(searchIcon); btnSearch.setName("search");
 * btnSearch.addActionListener(new ActionListener() {
 * 
 * @Override public void actionPerformed(ActionEvent e) { int searchPhone =
 * Integer.parseInt(tfSearch.getText()); System.out.println(searchPhone); MainVO
 * temp = service.searchFriend(searchPhone); JPanel fPanel = new JPanel(new
 * GridLayout(1, 3)); fPanel.setPreferredSize(new
 * Dimension(MAXIMIZED_HORIZ,50));
 * fPanel.setBorder(LineBorder.createBlackLineBorder()); JLabel fname = new
 * JLabel(temp.getName()); fPanel.add(fname); friendsPanel.add(fPanel);
 * friendsPanel.repaint(); } }); btnAddFriend = new JButton("추가"); btnExit = new
 * JButton("나가기");
 * 
 * tfSearch = new JTextField(20);
 * 
 * lbphone = new JLabel("Phone");
 * 
 * // 조립단계 => 작은것부터 큰것 순으로
 * 
 * // btnSearch.addActionListener(this); btnAddFriend.addActionListener(this);
 * btnExit.addActionListener(this);
 * 
 * menuPanel.add(lbphone); menuPanel.add(tfSearch); menuPanel.add(btnSearch);
 * 
 * menuPanel.add(btnAddFriend);
 * 
 * southPanel.add(btnAddFriend); southPanel.add(btnExit);
 * 
 * this.add(menuPanel,"North"); this.add(friendsPanel,"Center");
 * this.add(southPanel,"South");
 * 
 * this.setBounds(1250, 0, 350, 350); // 300,400은 좌표값, 1200,300길이
 * this.setResizable(false); this.setVisible(true);
 * this.setDefaultCloseOperation(EXIT_ON_CLOSE); }
 * 
 * @Override public void actionPerformed(ActionEvent e) { String command =
 * e.getActionCommand(); switch (command) { case "search": break; case "추가":
 * 
 * break; case "나가기":
 * 
 * break; default: break; } } }
 */
/*
 * class Test extends JFrame{ public Test(){ setBounds(0,800,300,200); JButton[]
 * btns = new JButton[5]; JPanel p = new JPanel(); p.setLayout(new
 * BoxLayout(p,BoxLayout.Y_AXIS)); for(int i=0;i<btns.length;i++){ btns[i] = new
 * JButton("btn"+i); btns[i].setPreferredSize(new Dimension(30*(i+1),40));
 * btns[i].setMaximumSize(new Dimension(30*(i+1),40)); p.add(btns[i]); } add(new
 * JScrollPane(p)); setVisible(true); } public static void main(String[] args){
 * new Test(); } }
 */