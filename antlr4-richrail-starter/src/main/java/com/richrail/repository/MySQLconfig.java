package com.richrail.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLconfig {
	
	public final static Connection getConnection() {
		Connection con = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/richraildb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");

		}catch(Exception e){
			System.out.println(e);
		}
		return con;
	}
}