package DBconn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import DAO.DAO;
// 1번 문제

public class dbconn extends JFrame{
	public dbconn() {
		setTitle("디비에 연결하기");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		JTextField tf = new JTextField("YES");
		JButton btn = new JButton("디비 연결하기");
		JLabel la = new JLabel("디비에 연결하시겠습니까? YES OR NO");
		
		c.add(tf,BorderLayout.CENTER);
		c.add(btn,BorderLayout.EAST);
		c.add(la,BorderLayout.SOUTH);
		btn.addActionListener(new MyActionListener(tf,la));
	
		setSize(400,400);
		setLocation(500,400);
		
		setVisible(true);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new dbconn();	//	https://dinae.tistory.com/27 추후에 화면전환은 여기서 참고하면 좋을것 같음
	}
}

class MyActionListener implements ActionListener{
	JTextField tf;
	JLabel la;
	public MyActionListener(JTextField tf,JLabel la) {
		this.tf=tf;
		this.la=la;
	}
	
	public void actionPerformed(ActionEvent e) {
		String answer = tf.getText();
	
		if(answer.equals("NO") || answer.equals("no")) {la.setText("안녕히 가세요");}
		else if(answer.equals("YES") || answer.equals("yes")) { DAO da = new DAO();	la.setText("반갑습니다. 데이터베이스의 연결되셨습니다.");}		
	}
}

