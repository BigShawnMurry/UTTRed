package com.ovation.uttdb;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UTTDBManager {
	private static String email;
	public UTTDBManager()
	{
		
	}
	public void setEmail(String em)
	{
		email=em;
	}
	public static String getEmail()
	{
		return email;
	}
	public void init() throws SQLException, ClassNotFoundException {
	
	Connection conn= openConnection();
	String SPsqlall="EXEC usp_UTT_Java ?";
	//String SPsqluse="EXEC sp_UTTUsed ?";
	//String SPsqlopen="EXEC sp_UTTOpen ?";
	//executeQuery(SPsqlall,conn);
	//executeQuery(SPsqluse,conn);
	executeQuery(SPsqlall,conn);
	closeConnection(conn);
	}
	public static Connection openConnection() throws SQLException, ClassNotFoundException
	{
		Connection con=null;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		con=DriverManager.getConnection("jdbc:sqlserver://SVR-SQL-01;user=OvationClient;password=0\\/at10Ncl13NT-01;databaseName=Ovation");
		return con;
	}
	private static void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
	public static ResultSet executeQuery(String query) {
		ResultSet set = null;
		
		try {
			Connection conn = openConnection();
			set = executeQuery(query, conn);
			
			closeConnection(conn);
		} catch (Exception e) {
			e.getMessage();
		}

		return set;
	}
	public static ResultSet executeQuery(String query, Connection c)
	{
		CallableStatement ps;
		ResultSet rs=null;
						try {
							ps=c.prepareCall(query);
							ps.setEscapeProcessing(true);
							ps.setQueryTimeout(180);
				String spvar=getEmail();
							ps.setString(1,spvar );
							rs=ps.executeQuery();
							
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
		
		
		return rs;
	}
}
