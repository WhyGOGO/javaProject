package DBconn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import DAO.DAO;
// 1�� ����

public class dbconn extends JFrame{
	public dbconn() {
		setTitle("��� �����ϱ�");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		JTextField tf = new JTextField("YES");
		JButton btn = new JButton("��� �����ϱ�");
		JLabel la = new JLabel("��� �����Ͻðڽ��ϱ�? YES OR NO");
		
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
		new dbconn();	//	https://dinae.tistory.com/27 ���Ŀ� ȭ����ȯ�� ���⼭ �����ϸ� ������ ����
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
	
		if(answer.equals("NO") || answer.equals("no")) {la.setText("�ȳ��� ������");}
		else if(answer.equals("YES") || answer.equals("yes")) { DAO da = new DAO();	la.setText("�ݰ����ϴ�. �����ͺ��̽��� ����Ǽ̽��ϴ�.");}		
	}
}

