package DAO;

import java.sql.*;

import DTO.DTO;


public class DAO {

	
	Connection conn =null; // 데이터베이스와 연결하는 객체
	
	ResultSet rs = null; // 실행한 쿼리문의 값을 받는 객체
	Statement st = null; 
	
	PreparedStatement ps = null; 
	
	public DAO() { //생성자 - 디비 연결 역할을 함
		
		try {
			String user = "ysu";
			String pw = "1234";
			String url = "jdbc:mysql://localhost:3306/instagram";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url,user,pw);

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
			
	}
	
	public void RegisterInsertData(DTO data) {
		try {
			String sql = "INSERT INTO member(id,passwd,name,reg_date,address,email) values(?,?,?,?,?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getId());
			ps.setString(2, data.getPasswd());
			ps.setString(3, data.getName());
			ps.setDate(4, data.getReg_date());
			ps.setString(5, data.getAddress());
			ps.setString(6, data.getEmail());
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
