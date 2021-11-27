package DBconn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DAO.DAO;
import movieList.Movie;

import javax.swing.*;

public class dbconn extends JFrame{
	private JButton btn1 = new JButton("디비연결하기");

	public dbconn() {
		setTitle("디비연결");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		
		btn1.addActionListener(new MyActionListener());
	
		add(btn1);
		
		
		setSize(400,400);
		setLocation(500,400);
		
		setVisible(true);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new dbconn();	
	}
}

class MyActionListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		Object btn1 = (Object)e.getSource();
		
		if(e.getSource()==btn1) {
			DAO.DAO();
			new Movie();
		}

	}
}

