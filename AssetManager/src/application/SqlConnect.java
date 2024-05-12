package application;

import java.sql.*;

public class SqlConnect {

	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:Category.sqlite");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
