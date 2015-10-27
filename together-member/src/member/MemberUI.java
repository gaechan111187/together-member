package member;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import global.Constants;
import global.DatabaseFactory;
import global.Vendor;

public class MemberUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JLabel l1,l2,l3;
    JButton b1,b2;
    JTextField tf1,tf2;
    JPanel p1,p2,p3,p4,p5;
    String str1,str2;
    MemberUI(String f) throws SQLException {
        super(f);
        Connection conn= DatabaseFactory.getDatabase(Vendor.ORACLE, Constants.ORACLE_ID, Constants.ORACLE_PASSWORD)
				.getConnection();
        Statement stmt = conn.createStatement();
        String sql="select id,pw from member";
        ResultSet RS=stmt.executeQuery(sql); RS.next();
        str1=RS.getString(1);
        str2=RS.getString(2);
        
        p1=new JPanel();p2=new JPanel();p3=new JPanel();
        p4=new JPanel();p5=new JPanel();
        l1=new JLabel("Login");l2=new JLabel("ID");l3=new JLabel("PW");
        b1=new JButton("Login");
        b1.addActionListener(this);
        tf1=new JTextField(10);tf2=new JTextField(10);
        p1.add(l1);
        p4.add(l2);p4.add(tf1);p5.add(l3);p5.add(tf2);
        p3.add(b1);
        p2.add(p4,"Center");p2.add(p5,"South");
        add(p1,"North");add(p2,"Center");add(p3,"South");
    }
    public void actionPerformed(ActionEvent e){
        Sub s=new Sub();
        
    }
    class Sub extends JFrame implements ActionListener{
        JPanel p1,p2;JLabel l; JButton b;
        Sub(){
            p1=new JPanel();p2=new JPanel();
            if(str1.equals(tf1.getText()) && str2.equals(tf2.getText()))
                l=new JLabel("Welcome");
            else if    (str1.equals(tf1.getText()) || str2.equals(tf2.getText()))    
                l=new JLabel("Not Identify ID or Password");
            else
                l=new JLabel("No Such Your Information Our MemberList");
            b=new JButton("Ok");
            b.addActionListener(this);
            p1.add(l);p2.add(b);
            add(p1,"North");add(p2,"Center");
            setBounds(300,300,200,100);
            setVisible(true);
        }
        public void actionPerformed(ActionEvent e){
            setVisible(false);
        }
    }
        
    
    public static void main(String args[]) throws SQLException{
        MemberUI lo=new MemberUI("Login");
        lo.setVisible(true);
        lo.setBounds(200,200,300,200);
    }
    
}


