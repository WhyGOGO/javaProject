package DAO;

import java.sql.*;

import DTO.DTO;


public class DAO {

	

	
	ResultSet rs = null; // ������ �������� ���� �޴� ��ü
	Statement st = null; 
	
	PreparedStatement ps = null; 
	
	public static Connection DAO() { //������ - ��� ���� ������ ��
		Connection conn=null; 
		try {
			// �����ͺ��̽��� �����ϴ� ��ü
			String user = "ysu";
			String pw = "1234";
			String url = "jdbc:mysql://localhost:3306/movielist";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url,user,pw);
			System.out.println("��� �����ϼ̽��ϴ�.");
		}
		
		catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.out.println("DB ����̹� �ε� ����");
		}
		catch (SQLException e) {
			// TODO: handle exception
			System.out.println("DB ���ӽ���");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("�������� ������ ����");
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
			System.out.println("��� �ݱ� ����");
		}	
	}
}
