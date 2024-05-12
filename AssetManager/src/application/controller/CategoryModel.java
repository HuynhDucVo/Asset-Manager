package application.controller;

import java.sql.*;
import java.util.ArrayList;

import application.SqlConnect;

public class CategoryModel {

	Connection connection;

	public CategoryModel() {

		connection = SqlConnect.Connector();

		if (connection == null)

		{
			System.out.print("not connected");
			System.exit(1);
		}

	}

	public Connection getConnection() {
		return connection;
	}

	public void createTable() {
		try {
			Statement st = connection.createStatement();
			st.execute("CREATE TABLE IF NOT EXISTS Category(name TEXT)");
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}

	/*
	 * Creates a new category
	 */
	public String saveCategory(String s) {
		
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO Category (name) VALUES(?)");
			ps.setString(1, s);

			ps.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex);

			return "didn't save";
		}

		return "saved";

	}

	/*
	 * Returns all categories
	 */
	public ArrayList<String> showAll() {
		ArrayList<String> categories = new ArrayList<>();
		try {

			String inst = "SELECT * FROM Category";

			Statement st = connection.createStatement();
			ResultSet re = st.executeQuery(inst);
			while (re.next()) {
				//result += re.getString("name") + ", ";
				categories.add(re.getString("name"));
			}
			return categories;

		} catch (SQLException ex) {
			System.out.println(ex);
			//return "didn't save"
			return categories;
		}

	}

	public boolean isDbConnected() {

		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
