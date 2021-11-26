package DAO;

import java.sql.*;

import DTO.DTO;


public class DAO {

	

	
	ResultSet rs = null; // 실행한 쿼리문의 값을 받는 객체
	Statement st = null; 
	
	PreparedStatement ps = null; 
	
	public static Connection DAO() { //생성자 - 디비 연결 역할을 함
		Connection conn=null; 
		try {
			// 데이터베이스와 연결하는 객체
			String user = "ysu";
			String pw = "1234";
			String url = "jdbc:mysql://localhost:3306/movielist";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url,user,pw);
			System.out.println("디비에 연결하셨습니다.");
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
			System.out.println("여러가진 이유의 에러");
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
			System.out.println("디비 닫기 실패");
		}	
	}
}
