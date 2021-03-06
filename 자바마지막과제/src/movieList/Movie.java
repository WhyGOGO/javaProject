package movieList;

import java.awt.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.DAO;
import DTO.DTO;

public class Movie extends JFrame{
	private String colNames[] = {"영화번호","제목","감독","장르","상영시간","출연자","평점(총10점)","개봉날짜","상영등급"};
	DefaultTableModel model = new DefaultTableModel(colNames,0);
	JTable table = new JTable(model);
	private JLabel imageLabel;
    File selectedFile;
    JFileChooser fileChooser;
	private JTextField title,director,summary,time,performer,score,date,rate;
	private JButton images;
	private JTextField imageName;
	JButton btn_selectedValue;
	
	
	public Movie() {

		setTitle("영화 리스트");
		setPreferredSize(new Dimension(880,1000));
		setLocation(500,100);
		Container contentPane = getContentPane();
		
		contentPane.add(new JScrollPane(table),BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800,70));
		
		imageLabel = new JLabel();
		imageLabel.setPreferredSize(new Dimension(800,400));
		JScrollPane spane = new JScrollPane(imageLabel);
		contentPane.add(spane,BorderLayout.SOUTH);

		
		
		 title = new JTextField(6);
		 director = new JTextField(6);
		 summary = new JTextField(6);
		 time = new JTextField(6);
		 performer = new JTextField(6);
		 score = new JTextField(6);
		 date = new JTextField(6);
		 rate = new JTextField(6);
		 imageName = new JTextField(6);
		 images = new JButton("이미지고르기");
		

		
		
		JButton button1 = new JButton("추가");
		btn_selectedValue = new JButton("값 가져오기");
		JButton btn_update = new JButton("수정");
		JButton b1 = new JButton("검색");
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
		panel.add(new JLabel("이미지"));		
		panel.add(imageName);
		panel.add(images);
	
		panel.add(button1);
		panel.add(btn_update);
		panel.add(btn_selectedValue);
		panel.add(b1);
		panel.add(button2);
		contentPane.add(panel,BorderLayout.NORTH);
		
		printAll();	//일단 전체보여주기	
		button1.addActionListener(new AddActionListener(table,title,director,summary,time,performer,score,date,rate));
		button2.addActionListener(new RemoveActionListener(table));
		btn_selectedValue.addActionListener(new select_recordActionListener());
		images.addActionListener(new AddImageListener());

//===================================마우스 클릭했을때 이미지 불러오기 ==================
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				DAO da = new DAO();
				int index = table.getSelectedRow();
				Object movienum = table.getValueAt(index, 0);
				ArrayList<DTO> at = da.select_image(Integer.parseInt(movienum.toString()));
				
				
				for (int i=0;i<at.size();i++) {
					DTO dt = at.get(i);
					imageLabel.setIcon(dt.getIco());
				}
							
			}
		});
		
		
		
		
		
	
											//================ 여기 부터 검색기능 ===========
		b1.addActionListener(new ActionListener() {
			DateFormat sdFormat = new SimpleDateFormat("yyyy-mm-dd");
												
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel models = (DefaultTableModel)table.getModel();
				models.setNumRows(0);
				
				ArrayList<DTO> at = new ArrayList<DTO>();
				DAO da = new DAO();
				
	
				String movieTitle= title.getText();
				String movieDirector = director.getText();
				String movieSummary = summary.getText();
				String moviePerformer = performer.getText();
				String movieRate = rate.getText();
				at = da.movieSelectData(movieTitle,movieDirector,movieSummary,moviePerformer,movieRate);
				
				if (at.size()==0) {
					String [] list2 = {"검색된 결과가 없습니다."};
					model.addRow(list2);
				}else {
					for (int i=0;i<at.size();i++) {
						DTO dt = at.get(i);
						int movieNum = dt.getMovieNum();
						String title2 = dt.getTitle();
						String director2 = dt.getDirector();
						String summary2 = dt.getSummary();
						int time2 = dt.getTime();
						String performer2 = dt.getPerformer();
						float score2 = dt.getScore();
						Date date2 = dt.getDate();
						String rate2 = dt.getRate();
						
						String [] list2 = {Integer.toString(movieNum), title2, director2, summary2, Integer.toString(time2), performer2, Float.toString(score2), sdFormat.format(date2), rate2};
						model.addRow(list2);
					}
					
				}
				
			}
		});
									//================ 여기까지 검색기능 ===========	
		
		btn_update.addActionListener(new ModifyActionListener(title,director,summary,time,performer,score,date,rate,imageName));
		
		
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
							//================ 여기부터 추가 기능 ===========	

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
			dt.setImage(selectedFile);


			da.movieInsertData(dt);
			printAll();		
	}
						//================ 여기까지 추가 기능 ===========	

	
		
	
//		public void actionPerformed(ActionEvent e) {
//			String arr[] = new String[3];
//			arr[0] = text1.getText();
//			arr[1] = text2.getText();
//			arr[2] = text3.getText();
//			DefaultTableModel model = (DefaultTableModel) table.getModel();
//			model.addRow(arr);
//		}
	}
						

	//================ 여기부터 삭제 기능 ===========================================================	
	class RemoveActionListener implements ActionListener{
		
		JTable table;
		DAO del = new DAO();
		
		RemoveActionListener(JTable table){
			this.table = table;
		}
		public void actionPerformed(ActionEvent e) {
			int row = table.getSelectedRow();//선택한 행의 인덱스를 저장
			if(row == -1)
				return;
			
			Object val = table.getValueAt(row, 0);//행의 첫번째 열인 id를 읽어오기
			int num = Integer.parseInt(val.toString());//int 타입의 값으로 저장
			
			try {
				del.movieDeleteData(num);
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			
			
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.removeRow(row);		
		}
	}
	
	
	//================ 여기까지 삭제 기능 ===========================================================	
	
	// select 찍어주는 메소드
	
	public void printAll() {
		//=============================================== 여기부터 Default select =============================
		DefaultTableModel models = (DefaultTableModel)table.getModel();
		models.setNumRows(0);	// 테이블 초기화
		
		DAO da = new DAO();
		ArrayList<DTO> dt=da.selectAll_movielist();
		for (int i=0;i<dt.size();i++) {
			DTO dt1 = dt.get(i);		
			String [] list2 = {String.valueOf(dt1.getMovieNum()),dt1.getTitle(), dt1.getDirector(), dt1.getSummary(),String.valueOf(dt1.getTime()),dt1.getPerformer(),String.valueOf(dt1.getScore()),String.valueOf(dt1.getDate()),String.valueOf(dt1.getRate())};
			imageLabel.setIcon(dt1.getIco());
			model.addRow(list2);
		}

		
		//=============================================== 여기까지 Default select =============================	
		
	}
	
	
	
	
	//=========================== 이미지 입력하는 버튼 ====================================
	public class AddImageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			JFrame window = new JFrame();
			 fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(window);
			selectedFile = fileChooser.getSelectedFile();
			
			if (result == JFileChooser.APPROVE_OPTION) {
				imageName.setText(String.valueOf(selectedFile));
			}
			
		}
	}
	//=========================== 여기까지 이미지 입력하는 버튼 ====================================
	
	//=========================== 여기부터 수정기능 버튼 ====================================
	
	public class ModifyActionListener implements ActionListener {
		
		DAO da = new DAO();
		JTextField title,director,summary,time,performer,score,date,rate,imageName;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		File selectedFile;
		
		ModifyActionListener(JTextField title,
				JTextField director,JTextField summary
				,JTextField time,JTextField performer
				,JTextField score,JTextField date
				,JTextField rate,JTextField imageName){
			
			this.title=title;
			this.director=director;
			this.summary=summary;
			this.time=time;
			this.performer=performer;
			this.score=score;
			this.date=date;
			this.rate=rate;
			this.imageName = imageName;

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String m_title = "";
			String m_director = "";
			String m_summary = "";
			int m_time = 0;
			String m_actor = "";
			float m_score = 0;
			Date m_date = null;
			String m_rate = "";
			
			int row = table.getSelectedRow();//선택한 행의 인덱스를 저장
			if(row == -1)
				return;
			
			Object val = table.getValueAt(row, 0);//행의 첫번째 열인 id를 읽어오기
			int num = Integer.parseInt(val.toString());//int 타입의 값으로 저장
			try {
				m_title = title.getText();
				m_director = director.getText();
				m_summary = summary.getText();
				if(time.getText().length()>0) {
					m_time = Integer.parseInt(time.getText());
				}
				m_actor = performer.getText();
				if (score.getText().length()>0) {
					m_score = Float.parseFloat(score.getText());
				}
				m_date = null;
				m_rate = rate.getText();
				if(date.getText().length()>0) {
					m_date = new Date(sdf.parse(date.getText()).getTime());
				}
				
				if (m_title.length()>0) {
					da.updateTitle(m_title, num);
				}else {return;}
				if (m_director.length()>0) {
					da.updateDirect(m_director, num);
				}else {return;}
				if (m_summary.length()>0) {
					da.updateSummary(m_summary, num);
				}else {return;}
				if (m_time>0) 
					da.updateTime(m_time, num);
				if (m_actor.length()>0) {
					da.updateActor(m_actor, num);
				}else {return;}
				if (m_score>0) {
					da.updateScore(m_score, num);
				}else {return;}
				if (m_date!=null) {
					da.updateDate(m_date, num);
				}else {return;}
				if (m_rate.length()>0) {
					da.updateRate(m_rate, num);
				}else {return;}
				if (imageName.getText().length() >0) {
					da.updateImage(fileChooser.getSelectedFile(),num);
					
				}else {System.out.println("이미지 못읽음"); return; }

			}catch (Exception e2) {
				e2.printStackTrace();
			}
			finally {
				printAll();	//일단 전체보여주기	
			}

		}
		
	}

	
	//=========================== 값가져오는 부분 ====================================

	public class select_recordActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object o = e.getSource();
			if(o == btn_selectedValue) {
				int index = table.getSelectedRow();
				
				
				String title_u = (String) table.getValueAt(index, 1);
				title.setText(title_u);
				
				String director_u = (String) table.getValueAt(index, 2);
				director.setText(director_u);

				String summary_u = (String) table.getValueAt(index, 3);
				summary.setText(summary_u);
				
				String time_u = (String) table.getValueAt(index, 4);
				time.setText(time_u);
				
				String performer_u = (String) table.getValueAt(index, 5);
				performer.setText(performer_u);
				
				String score_u = (String) table.getValueAt(index, 6);
				score.setText(score_u);
				
				String date_u = (String) table.getValueAt(index, 7);
				date.setText(date_u);
				
				String rate_u = (String) table.getValueAt(index, 8);
				rate.setText(rate_u);
				
			
			}
		}

	}

	//=========================== 여기까지 값가져오는 부분 ====================================

}

	
	
	
