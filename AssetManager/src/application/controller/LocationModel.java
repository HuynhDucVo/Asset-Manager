package application.controller;

import java.sql.*;
import java.util.ArrayList;

import application.SqlConnect;

public class LocationModel {

	Connection connection;

	public LocationModel() {

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
			st.execute("CREATE TABLE IF NOT EXISTS Location(locationName TEXT NOT NULL PRIMARY KEY, description TEXT)");
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}

	/*
	 * Adds location to database
	 */
	public String saveLocation(String s, String des) {
		
		
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement("INSERT INTO Location (locationName, description) VALUES(?, ?)");
			ps.setString(1, s);
			ps.setString(2, des);

			ps.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex);

			return "didn't save";
		}
		 
		
		return "saved";

	}

	/*
	 * Returns all locations
	 */
	public ArrayList<String> showAll() {
		ArrayList<String> locations = new ArrayList<>();
		//String result = "";
		try {

			String inst = "SELECT * FROM Location";

			Statement st = connection.createStatement();
			ResultSet re = st.executeQuery(inst);

			while (re.next()) {
				//result += re.getString("name") + "-->" +  re.getString("description") + ", ";
				locations.add(re.getString("locationName"));

			}
			return locations;

		} catch (SQLException ex) {
			System.out.println(ex);

			return locations;
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
