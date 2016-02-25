package com.sidney.util;
import java.sql.*;

public class DbManager {
	public static Connection getConnectioin() throws SQLException{
		return getConnection("databaseWeb","root","sidney");
	}
	
	public static Connection getConnection(String dbName,String userName,String password) throws SQLException{
		String url ="jdbc:mysql://localhost:3306/"+dbName;
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		return DriverManager.getConnection(url,userName,password);
		
	}

	public static void setParams(PreparedStatement preStmt,Object...params) throws SQLException{
		if(params == null ||params.length==0){
			return;
		}
		for(int i = 1;i<=params.length;i++){
			Object param = params[i-1];
			if(param == null){
				preStmt.setNull(i, Types.NULL);
			}else if(param instanceof Integer){
				preStmt.setInt(i, (Integer)param);
			}else if (param instanceof String){
				preStmt.setString(i, (String)param);
			}else if(param instanceof Double){
				preStmt.setDouble(i, (Double)param);
			}else if(param instanceof Long){
				preStmt.setLong(i, (Long)param);
			}else if(param instanceof Timestamp){
				preStmt.setTimestamp(i, (Timestamp)param);
				
			}else if(param instanceof Boolean){
				preStmt.setBoolean(i, (Boolean)param);
			}else if(param instanceof Date){
				preStmt.setDate(i, (Date)param);
			}
		}
	}
	
	public static int executeUpdate(String sql) throws SQLException{
		return executeUpdate(sql,new Object[]{});
	}
	
	public static int executeUpdate(String sql,Object...params) throws SQLException{
		Connection conn = null;
		PreparedStatement preStmt = null;
		try{
			conn = getConnectioin();
			preStmt=conn.prepareStatement(sql);
			setParams(preStmt, params);
			return preStmt.executeUpdate();
		}finally{
			if(preStmt != null) preStmt.close();
			if(conn!=null) conn.close();
		}
	}
	
	/*
	 * 获取总数
	 */
	public static int getCount(String sql) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = getConnectioin();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
			    return rs.getInt(1); //返回查询到的第一列数据(记录总数)
			}else 
				return 0;
			
		}finally{
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
	}
	
	public static void showResultSet(ResultSet rs) {
		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for(int i = 1;i<=count;i++){
				System.out.print(rsmd.getColumnName(i)+"   ");
			}
			System.out.println();
			while (rs.next()){
				for(int i = 1;i<=count;i++){
					System.out.print(rs.getString(i)+"   ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args) throws SQLException{
		Connection conn = DbManager.getConnectioin();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from tb_person");
		showResultSet(rs);
		
		if(rs!=null) rs.close();
		if(stmt!=null) stmt.close();
		if(conn!=null) conn.close();
	}
}
