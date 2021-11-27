package movieList;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.DAO;
import DTO.DTO;

public class Movie extends JFrame implements ActionListener{
	private String colNames[] = {"영화번호","제목","감독","장르","상영시간","출연자","평점(총10점)","개봉날짜","상영등급"};
	DefaultTableModel model = new DefaultTableModel(colNames,0);
	JTable table = new JTable(model);
	
	private JMenuItem save, view, exit;
	private JFileChooser fc;
	private JMenuBar menuBar;
	private JMenu menu;
	private JLabel imageLabel;
	private JLabel textLabel;
	private int numberOfRecord;
	private JButton nextButton;
	private ResultSet viewRS=null;
	private ImageIcon img=null;

	

	public Movie() {

		setTitle("영화 리스트");
		setPreferredSize(new Dimension(750,800));
		setLocation(500,100);
		Container contentPane = getContentPane();
		
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

	
		panel.add(button1);
		panel.add(b1);
		panel.add(button2);
		contentPane.add(panel,BorderLayout.NORTH);
		
		printAll();	//일단 전체보여주기	
		button1.addActionListener(new AddActionListener(table,title,director,summary,time,performer,score,date,rate));
		button2.addActionListener(new RemoveActionListener(table));
		
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
		
		//================ 여기부터  이미지 기능 ===========
		fc = new JFileChooser(); // JFileChooser 객체 생성
		menuBar = new JMenuBar(); // JMenuBar 컴포넌트를 생성
		menu = new JMenu("메뉴");
		menuBar.add(menu); // 메뉴를 메뉴바에 붙인다

		// 서브 메뉴
		save = new JMenuItem("사진 저장");
		save.addActionListener(this); // save 메뉴에 대한 이벤트 리스너를 등록
		menu.add(save); // 메뉴 아이템을 메뉴에 붙인다
		view = new JMenuItem("모든 사진 보기");
		menu.add(view); // 메뉴 아이템을 메뉴에 붙인다
		view.addActionListener(this); // view 메뉴에 대한 이벤트 리스너를 등록
		menu = new JMenu("종료");
		exit = new JMenuItem("나가기");
		exit.addActionListener(this); // exit 메뉴에 대한 이벤트 리스너를 등록
		menu.add(exit); // 메뉴 아이템을 메뉴에 붙인다
		menuBar.add(menu); // 메뉴를 메뉴바에 붙인다
		setJMenuBar(menuBar); // 메뉴바를 프레임에 붙인다
		
		imageLabel = new JLabel(); // 이미지를 표시할 레이블 컴포넌트
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // 중앙정렬
		textLabel = new JLabel(); // 파일 이름을 표시할 레이블 컴포넌트
		textLabel.setHorizontalAlignment(SwingConstants.CENTER); // 중앙정렬
		JScrollPane spane = new JScrollPane(imageLabel); // 큰 사이즈의 이미지를 위해  ScrollPane에서 표시
		
		JPanel panel2 = new JPanel();
		panel2.add(spane);
		panel2.add(textLabel);
		panel2.setPreferredSize(new Dimension(800, 400));
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	//================ 여기까지 이미지 기능 ===========

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
			model.addRow(list2);
		}

		
		//=============================================== 여기까지 Default select =============================	
		
	}


}

	
	
	
