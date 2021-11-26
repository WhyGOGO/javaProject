package movieList;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.DAO;
import DTO.DTO;

public class Movie extends JFrame{
	private String colNames[] = {"영화번호","제목","감독","장르","상영시간","출연자","평점(총10점)","개봉날짜","상영등급"};

	public Movie() {

		setTitle("영화 리스트");
		setPreferredSize(new Dimension(750,800));
		setLocation(500,100);
		Container contentPane = getContentPane();
		
		DefaultTableModel model = new DefaultTableModel(colNames,0);
		JTable table = new JTable(model);
		contentPane.add(new JScrollPane(table),BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800,70));
		
		JTextField title = new JTextField(6);
		JTextField director = new JTextField(6);
		JTextField summary = new JTextField(6);
		JTextField time = new JTextField(6);
		JTextField performer = new JTextField(6);
		JTextField score = new JTextField(6);
		JTextField date = new JTextField(6);
		JTextField rate = new JTextField(6);

		JButton button1 = new JButton("추가");
		JButton button2 = new JButton("삭제");
		panel.add(new JLabel("제목"));
		panel.add(title);	
		panel.add(new JLabel("감독"));
		panel.add(director);
		panel.add(new JLabel("장르"));
		panel.add(summary);
		panel.add(new JLabel("상영시간"));
		panel.add(time);
		panel.add(new JLabel("출연자"));
		panel.add(performer);
		panel.add(new JLabel("평점(총10점)"));
		panel.add(score);
		panel.add(new JLabel("개봉날짜"));
		panel.add(date);
		panel.add(new JLabel("상영등급"));
		panel.add(rate);

	
		panel.add(button1);
		panel.add(button2);
		contentPane.add(panel,BorderLayout.NORTH);
		
		button1.addActionListener(new AddActionListener(table,title,director,summary,time,performer,score,date,rate));
		//button2.addActionListener(new RemoveActionListener(table));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	class AddActionListener implements ActionListener{
		JTable table;
		JTextField title,director,summary,time,performer,score,date,rate;
		AddActionListener(JTable table,JTextField title,
				JTextField director,JTextField summary
				,JTextField time,JTextField performer
				,JTextField score,JTextField date
				,JTextField rate){
			
			this.table=table;
			this.title=title;
			this.director=director;
			this.summary=summary;
			this.time=time;
			this.performer=performer;
			this.score=score;
			this.date=date;
			this.rate=rate;
		}
		public void actionPerformed(ActionEvent e) {
			
			DTO dt = new DTO();
			DAO da = new DAO();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String title1=title.getText();
			String director1=director.getText();
			String summary1=summary.getText();
			int time1=Integer.parseInt(time.getText());
			String performer1=performer.getText();
			float score1=Float.parseFloat(score.getText());
			Date date1 = null;
			try {
				date1 = new Date(sdf.parse(date.getText()).getTime());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String rate1=rate.getText();

						
			dt.setTitle(title1);	
			dt.setDirector(director1);	
			dt.setSummary(summary1);
			dt.setTime(time1);	
			dt.setPerformer(performer1);	
			dt.setScore(score1);
			dt.setDate(date1);
			dt.setRate(rate1);

		
			da.movieInsertData(dt);

			
	}
//		public void actionPerformed(ActionEvent e) {
//			String arr[] = new String[3];
//			arr[0] = text1.getText();
//			arr[1] = text2.getText();
//			arr[2] = text3.getText();
//			DefaultTableModel model = (DefaultTableModel) table.getModel();
//			model.addRow(arr);
//		}
	}
	
//	class RemoveActionListener implements ActionListener{
//		JTable table;
//		RemoveActionListener(JTable table){
//			this.table = table;
//		}
//		public void actionPerformed(ActionEvent e) {
//			int row = table.getSelectedRow();
//			if(row == -1)
//				return;
//			DefaultTableModel model = (DefaultTableModel)table.getModel();
//			model.removeRow(row);
//		}
	}

	
	
	
