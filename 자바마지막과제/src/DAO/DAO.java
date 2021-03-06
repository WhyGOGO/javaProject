package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import DTO.DTO;


public class DAO {

	

	
	ResultSet rs = null; // 실행한 쿼리문의 값을 받는 객체
	Statement st = null; 
	PreparedStatement ps = null; 
	static Connection conn;
	
	public static Connection DAO() { //생성자 - 디비 연결 역할을 함
		conn = null; 

		try {
			// 데이터베이스와 연결하는 객체
			String user = "ysu";
			String pw = "1234";
			String url = "jdbc:mysql://localhost:3306/movielist";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url,user,pw);
			System.out.println("디비에 연결하였습니다.");
		}
		
		catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.out.println("DB 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			// TODO: handle exception
			System.out.println("DB 접속실패");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("여러가지 이유의 에러");
		}
		return conn;
	}
	
	
	public void movieInsertData(DTO data) {
		try {
			String sql = "INSERT INTO movie(title,director,summary,time,performer,score,date,rate,image) values(?,?,?,?,?,?,?,?,?)";
			FileInputStream fin = new FileInputStream(data.getImage());
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getTitle());
			ps.setString(2, data.getDirector());
			ps.setString(3, data.getSummary());
			ps.setInt(4, data.getTime());
			ps.setString(5, data.getPerformer());
			ps.setFloat(6, data.getScore());
			ps.setDate(7, data.getDate());
			ps.setString(8, data.getRate());
			ps.setBinaryStream(9,fin,(int)data.getImage().length());
			ps.executeUpdate();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	//제목검색으로 조회
	public ArrayList<DTO> movieSelectData(String title,String director,String summary,String performer,String rate) {
		
		ArrayList<DTO> list = new ArrayList<DTO>();
		
		try {
			String sql = "select * from movie where title like '"+title+"%' and \n"
					+"director like '"+director+"%' and \n"
					+"summary like '"+summary+"%' and \n"
					+"performer like '"+performer+"%' and \n"
					+"rate like '"+rate+"%'";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				DTO dto = new DTO();
				dto.setMovieNum(rs.getInt(1));
				dto.setTitle(rs.getString(2));
				dto.setDirector(rs.getString(3));
				dto.setSummary(rs.getString(4));
				dto.setTime(rs.getInt(5));
				dto.setPerformer(rs.getString(6));
				dto.setScore(rs.getFloat(7));
				dto.setDate(rs.getDate(8));
				dto.setRate(rs.getString(9));
				

				
				list.add(dto);
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
		return list;
	}
	//전체 조회
		public ArrayList<DTO> selectAll_movielist() {
			
			ArrayList<DTO> list = new ArrayList<DTO>();
			
			try {
				String sql = "select * from movie";
				
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					DTO dto = new DTO();
					dto.setMovieNum(rs.getInt(1));
					dto.setTitle(rs.getString(2));
					dto.setDirector(rs.getString(3));
					dto.setSummary(rs.getString(4));
					dto.setTime(rs.getInt(5));
					dto.setPerformer(rs.getString(6));
					dto.setScore(rs.getFloat(7));
					dto.setDate(rs.getDate(8));
					dto.setRate(rs.getString(9));

					Blob b = rs.getBlob(10);
					ImageIcon img = new ImageIcon(b.getBytes(1, (int) b.length())); 
					dto.setIco(img);
					
					list.add(dto);
				}

			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally {
				dbClose();
			}
			return list;
		}
		
	//삭제메소드
	public void movieDeleteData(int num) {
		try {
			String sql = "delete from movie where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			ps.executeUpdate();
			
		}catch(Exception e) {
			
		}
		finally {
			dbClose();
		}
	}
	//이미지 삽입 메소드
	public void insertImage(File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			String sql = "insert into movielist (image) values (?)";
			ps = conn.prepareStatement(sql);
			ps.setBinaryStream(1, fin,(int)file.length());
			ps.executeUpdate();
			

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	
	//이미지 수정
	public void updateImage(File file,int num) {
		try {
			System.out.println(num);

			FileInputStream fin = new FileInputStream(file);
			String sql = "update movie set image = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setBinaryStream(1, fin,(int)file.length());
			ps.setInt(2, num);
			ps.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	
	
	//사용자가 행을 클리간 이미지만 불러오기 
	public ArrayList<DTO> select_image(int num) {
		ArrayList<DTO> list = new ArrayList<DTO>();
		
		try {
			DTO dto = new DTO();
			String sql = "select image from movie where movie_id=?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,num);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Blob b = rs.getBlob(1);
				ImageIcon img = new ImageIcon(b.getBytes(1, (int) b.length())); 
				dto.setIco(img);	
				list.add(dto);
			}

		}
		catch (Exception e) {
			System.out.println("지정된 이미지가 없습니다.");
		}
		finally {
			dbClose();
		}
		return list;
	}
	//제목을 수정 title,director,summary,time,performer,score,date,rate,imageName;
	public void updateTitle(String str, int num) {
		try {
			String sql = "update movie set title = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, str);
			ps.setInt(2, num);
			ps.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	//감독 수정
	public void updateDirect(String str, int num) {
		try {
			String sql = "update movie set director = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, str);
			ps.setInt(2, num);
			ps.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	//장르수정
	public void updateSummary(String str, int num) {
		try {
			String sql = "update movie set summary = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, str);
			ps.setInt(2, num);
			ps.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	//상영시간 수정
	public void updateTime(int time, int num) {
		try {
			String sql = "update movie set time = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, time);
			ps.setInt(2, num);
			ps.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	//배우수정
	public void updateActor(String str, int num) {
		try {
			String sql = "update movie set performer = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, str);
			ps.setInt(2, num);
			ps.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	//평점수정
	public void updateScore(float score, int num) {
		try {
			String sql = "update movie set score = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setFloat(1, score);
			ps.setInt(2, num);
			ps.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	//개봉일 수정
	public void updateDate(Date date, int num) {
		try {
			String sql = "update movie set date = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setDate(1, date);
			ps.setInt(2, num);
			ps.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	//상영등급 수정
	public void updateRate(String str, int num) {
		try {
			String sql = "update movie set rate = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, str);
			ps.setInt(2, num);
			ps.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			dbClose();
		}
	}
	
	private void dbClose() {
		// TODO Auto-generated method stub
		try {	
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			System.out.println("디비닫기 실패");
		}	
	}
}
