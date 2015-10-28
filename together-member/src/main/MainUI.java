package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
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
import javax.swing.border.LineBorder;

import chat.ChatUI;
import client.ClientServiceImpl;
import member.MemberVO;

public class MainUI extends JFrame implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	JButton btnAddFriend, btnSetUp, btnSearch, btnChat, btnExit;
	JPanel menuPanel, uMenuPanel, dMenuPanel; // 메뉴, 위, 아래
	JPanel friendsPanel; // 친구목록
	JPanel southPanel;
	JTextField tfSearch;
	JCheckBox ckFriend1, ckFriend2, ckFriend3, ckFriend4, ckFriend5;
	JLabel fname1, fname2, fname3, fname4, fname5;
	JLabel femail1, femail2, femail3, femail4, femail5;
	ClientServiceImpl client;
	List<MemberVO> vec, chatList;
	List<MainVO> friendsList;
	private StringBuffer friends;
	public StringBuffer getFriends() {
		return friends;
	}

	List<ChatUI> rooms;

	public Vector<ChatUI> getRooms() {
		return (Vector<ChatUI>) rooms;
	}

	public void setRooms(int index, ChatUI rooms) {
		this.rooms.add(index, rooms); // 해당 인덱스에 채팅방을 생성함
	}

	
	int[] check;
	MainService service = MainServiceImpl.getService();

	public MainUI(ClientServiceImpl client) {
		rooms = new Vector<ChatUI>();
		vec = new Vector<MemberVO>();
		friends = new StringBuffer();
		this.client = client;
		this.init();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void init() {

		this.setTitle("Together");

		menuPanel = new JPanel(new GridLayout(2, 1));
		menuPanel.setBorder(LineBorder.createBlackLineBorder());
		uMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		uMenuPanel.setBorder(LineBorder.createBlackLineBorder());
		dMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dMenuPanel.setBorder(LineBorder.createBlackLineBorder());

		friendsPanel = new JPanel(new GridLayout(10, 1)); // 친구수에 따라 행 바뀌어야 함	
		friendsPanel.setBorder(LineBorder.createBlackLineBorder());
		// friendsPanel.setLayout(new BoxLayout(friendsPanel,BoxLayout.Y_AXIS));
		// friendsPanel.setPreferredSize(new Dimension(350,500));

		JScrollPane scrollPane = new JScrollPane(friendsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.EAST);

		friendsPanel.setAutoscrolls(true); // 자동스크롤생성

		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		southPanel.setBorder(LineBorder.createBlackLineBorder());

		ImageIcon addFriendIcon = new ImageIcon("src/images/addFriend.jpeg");
		ImageIcon setupIcon = new ImageIcon("src/images/setup.jpeg");
		ImageIcon searchIcon = new ImageIcon("src/images/search.jpeg");

		btnAddFriend = new JButton(addFriendIcon);
		btnAddFriend.setName("addFrined");
		btnAddFriend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				AddFriend addFriend = new AddFriend(vec.get(vec.size()-1),client);
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
						JCheckBox ckFriend = new JCheckBox(femail.getText());
					//	ckFriend.addItemListener();
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

		// 조립단계 => 작은것부터 큰것 순으로

		btnChat.addActionListener(this);
		btnExit.addActionListener(this);

		uMenuPanel.add(btnAddFriend);
		uMenuPanel.add(btnSetUp);

		dMenuPanel.add(tfSearch);
		dMenuPanel.add(btnSearch);

		menuPanel.add(uMenuPanel);
		menuPanel.add(dMenuPanel);

		southPanel.add(btnChat);
		southPanel.add(btnExit);

		vec = client.getVec(); // 친구목록 맨마지막은 나
		friendsList = service.getFriends(vec.get(vec.size()-1).getPhone());
		check = new int[vec.size()]; // 체크박스목록
		int size = vec.size() - 1;
		System.out.println("사이즈는 " + size);
		if (size != 0) {
			for (int i = 0; i < friendsList.size(); i++) {
				JPanel fPanel = new JPanel(new GridLayout(1, 3));
				fPanel.setPreferredSize(new Dimension(MAXIMIZED_HORIZ, 50));
				fPanel.setBorder(LineBorder.createBlackLineBorder());
				JLabel fname = new JLabel(friendsList.get(i).getName());
				JLabel femail = new JLabel(friendsList.get(i).getEmail());
				JCheckBox ckFriend = new JCheckBox();
				ckFriend.setText(friendsList.get(i).getPhone());
				ckFriend.addItemListener(this);
				fPanel.add(fname);
				fPanel.add(femail);
				fPanel.add(ckFriend);
				friendsPanel.add(fPanel);
			}
		} else {
			JPanel fPanel = new JPanel();
			JLabel no = new JLabel("친구를 등록해주세요.");
			fPanel.add(no);
			friendsPanel.add(fPanel);
		}

		this.add(menuPanel, "North");
		this.add(friendsPanel, "Center");
		this.add(southPanel, "South");
		this.setBounds(1250, 0, 350, 700);

		// 화면 중앙에 스윙 띄우기
		/*
		 * Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		 * Dimension frm = this.getSize(); int xpos = (int) (screen.getWidth() /
		 * 2 - frm.getWidth() / 2); int ypos = (int) (screen.getHeight() / 2 -
		 * frm.getHeight() / 2); this.setLocation(xpos, ypos);
		 */
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "채팅하기":
			chatList = new ArrayList<MemberVO>();
			for (int i = 0; i < check.length; i++) {
				if (check[i] == 1) {
					chatList.add(vec.get(i));
				}
			}
			chatList.add(vec.get(vec.size()-1));
			System.out.println(chatList);
			client.creatChatRoom(chatList.toString());
			int roomNum = 0;				// 임의 생성
			new ChatUI(client, roomNum);	// 임의 생성
			break;
		case "종료":
			System.exit(0);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		String source = e.paramString();
		for (int i = 0; i < vec.size(); i++) {
			if (vec.get(i).getPhone().equals(service.getSource(source)) && check[i] == 0) {
				check[i] = 1;
			} else if (vec.get(i).getPhone().equals(service.getSource(source)) && check[i] == 1) {
				check[i] = 0;
			}
		}
	}
	/*
	public void createFPanel(List<MemberVO> flist){
		if (flist.size() != 0) {
			for (int i = 0; i < flist.size(); i++) {
				JPanel fPanel = new JPanel(new GridLayout(1, 3));
				fPanel.setPreferredSize(new Dimension(MAXIMIZED_HORIZ, 50));
				fPanel.setBorder(LineBorder.createBlackLineBorder());
				JLabel fname = new JLabel(flist.get(i).getName());
				JLabel femail = new JLabel(flist.get(i).getEmail());
				JCheckBox ckFriend = new JCheckBox();
				ckFriend.setText(flist.get(i).getPhone());
				ckFriend.addItemListener(this);
				fPanel.add(fname);
				fPanel.add(femail);
				fPanel.add(ckFriend);
				friendsPanel.add(fPanel);
			}
		} else {
			JPanel fPanel = new JPanel();
			JLabel no = new JLabel("친구를 등록해주세요.");
			fPanel.add(no);
			friendsPanel.add(fPanel);
		}
	}
*/
}

class AddFriend extends JFrame implements ActionListener {			// 친구추가창
	private static final long serialVersionUID = 1L;
	MainService service = MainServiceImpl.getService();
	MemberVO myVO;
	MainVO tempAddFriend;
	ClientServiceImpl client;

	JButton btnAddFriend, btnSearch, btnExit;
	JPanel menuPanel, uMenuPanel, dMenuPanel; // 메뉴, 위, 아래
	JPanel friendsPanel;
	JPanel southPanel;
	JTextField tfSearch;
	JLabel lbPhone;

	public AddFriend(MemberVO myVO, ClientServiceImpl client) {
		this.myVO = myVO;
		this.client = client;
		init();
	}

	public void init() {
		this.setTitle("친구추가");

		menuPanel = new JPanel();
		menuPanel.setBorder(LineBorder.createBlackLineBorder());

		friendsPanel = new JPanel(new GridLayout(5, 1)); // 친구수에 따라 행 바뀌어야 함
		friendsPanel.setBorder(LineBorder.createBlackLineBorder());
		JScrollPane scrollPane = new JScrollPane(friendsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.EAST);

		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		southPanel.setBorder(LineBorder.createBlackLineBorder());

		ImageIcon searchIcon = new ImageIcon("src/images/search.jpeg");

		btnSearch = new JButton(searchIcon);
		btnSearch.setName("search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchPhone = tfSearch.getText();
				tempAddFriend = service.searchFriend(searchPhone);
				JPanel fPanel = new JPanel(new GridLayout(1, 3));
				fPanel.setPreferredSize(new Dimension(MAXIMIZED_HORIZ, 50));
				fPanel.setBorder(LineBorder.createBlackLineBorder());
				JLabel fname = new JLabel(tempAddFriend.getName());
				fPanel.add(fname);
				friendsPanel.removeAll();
				friendsPanel.add(fPanel);
				friendsPanel.repaint();
				init();
			}
		});
		btnAddFriend = new JButton("추가");
		btnExit = new JButton("나가기");

		tfSearch = new JTextField(20);

		lbPhone = new JLabel("Phone");

		// 조립단계 => 작은것부터 큰것 순으로

		btnAddFriend.addActionListener(this);
		btnExit.addActionListener(this);

		menuPanel.add(lbPhone);
		menuPanel.add(tfSearch);
		menuPanel.add(btnSearch);

		menuPanel.add(btnAddFriend);

		southPanel.add(btnAddFriend);
		southPanel.add(btnExit);

		this.add(menuPanel, "North");
		this.add(friendsPanel, "Center");
		this.add(southPanel, "South");

		this.setBounds(1250, 0, 350, 350); // 300,400은 좌표값, 1200,300길이
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "search":
			break;
		case "추가":
			JDialog dialog = new JDialog(this, service.addFriend(myVO, tempAddFriend));
			dialog.setSize(250, 200);
			dialog.setVisible(true);
			break;
		case "나가기":
			this.dispose();
			MainUI mainUI = new MainUI(client);
			break;
		default:
			break;
		}
	}
}

/*
 * class Test extends JFrame{ public Test(){ setBounds(0,800,300,200); JButton[]
 * btns = new JButton[5]; JPanel p = new JPanel(); p.setLayout(new
 * BoxLayout(p,BoxLayout.Y_AXIS)); for(int i=0;i<btns.length;i++){ btns[i] = new
 * JButton("btn"+i); btns[i].setPreferredSize(new Dimension(30*(i+1),40));
 * btns[i].setMaximumSize(new Dimension(30*(i+1),40)); p.add(btns[i]); } add(new
 * JScrollPane(p)); setVisible(true); } public static void main(String[] args){
 * new Test(); } }
 */