package DAO;

import java.sql.*;
import java.util.*;

import javax.swing.JTextField;

import DTO.DTO;


public class DAO {

	

	
	ResultSet rs = null; // 실행한 쿼리문의 값을 받는 객체
	Statement st = null; 
	
	PreparedStatement ps = null; 
	
	public static Connection DAO() { //생성자 - 디비 연결 역할을 함
		Connection conn=null; 
		try {
			// 데이터베이스와 연결하는 객체
			String user = "root";
			String pw = "wjdduq1101!";
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
			Connection conn=DAO();
			String sql = "INSERT INTO movie(title,director,summary,time,performer,score,date,rate) values(?,?,?,?,?,?,?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getTitle());
			ps.setString(2, data.getDirector());
			ps.setString(3, data.getSummary());
			ps.setInt(4, data.getTime());
			ps.setString(5, data.getPerformer());
			ps.setFloat(6, data.getScore());
			ps.setDate(7, data.getDate());
			ps.setString(8, data.getRate());
			ps.executeUpdate();
			
			
			ps.close();
			conn.close();
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
			Connection conn=DAO();
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
			
			
			ps.close();
			conn.close();
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
				Connection conn=DAO();
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
			Connection conn = DAO();
			String sql = "delete from movie where movie_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		}catch(Exception e) {
			
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
