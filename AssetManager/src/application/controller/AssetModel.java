package application.controller;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import application.SqlConnect;

public class AssetModel {

	Connection connection;

	public AssetModel() {

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

	/*
	 * Creates a table if none exists
	 */
	public void createTable() {
		try {
			Statement st = connection.createStatement();
			st.execute(
					"CREATE TABLE IF NOT EXISTS Asset(name TEXT NOT NULL, category TEXT NOT NULL, locationName TEXT NOT NULL, purchaseDate TEXT, description TEXT, purchasedValue TEXT, expDate TEXT, FOREIGN KEY (locationName) REFERENCES Persons(locationName))");
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}

	/*
	 * Deletes the passed asset
	 */
	public void deleteAsset(TableAsset asset) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"DELETE FROM Asset WHERE ((name=?) AND (category=?) AND (locationName=?) AND (purchaseDate=?) AND (description=?) AND (purchasedValue=?) AND (expDate=?))");

			ps.setString(1, asset.getName());
			ps.setString(2, asset.getCategory());
			ps.setString(3, asset.getLocation());
			ps.setString(4, asset.getPurchase_date());
			ps.setString(5, asset.getDescription());
			ps.setString(6, asset.getValue());
			ps.setString(7, asset.getExp_date());

			ps.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex);

		}
	}

	/*
	 * Updates oldAsset to the newAsset's new attributes
	 */
	public void updateAsset(TableAsset oldAsset, TableAsset newAsset) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE Asset "
					+ "SET name=?, category=?, locationName=?, purchaseDate=?, description=?, purchasedValue=?, expDate=?"
					+ "WHERE ((name=?) AND (category=?) AND (locationName=?) AND (purchaseDate=?) AND (description=?) AND (purchasedValue=?) AND (expDate=?))");

			ps.setString(1, newAsset.getName());
			ps.setString(2, newAsset.getCategory());
			ps.setString(3, newAsset.getLocation());
			ps.setString(4, newAsset.getPurchase_date());
			ps.setString(5, newAsset.getDescription());
			ps.setString(6, newAsset.getValue());
			ps.setString(7, newAsset.getExp_date());

			ps.setString(8, oldAsset.getName());
			ps.setString(9, oldAsset.getCategory());
			ps.setString(10, oldAsset.getLocation());
			ps.setString(11, oldAsset.getPurchase_date());
			ps.setString(12, oldAsset.getDescription());
			ps.setString(13, oldAsset.getValue());
			ps.setString(14, oldAsset.getExp_date());

			ps.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex);

		}
	}

	/*
	 * Adds asset with given parameters
	 */
	public String saveAsset(String a, String b, String c, String d, String e, String f, String g) {
		System.out.println(" in asset working" + a + b + c + d + e + f + g);
		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"INSERT INTO Asset (name, category, locationName, purchaseDate, description, purchasedValue, expDate) VALUES(?, ?, ?, ?, ?, ?, ?)");

			ps.setString(1, a);
			ps.setString(2, b);
			ps.setString(3, c);
			ps.setString(4, d);

			ps.setString(5, e);
			ps.setString(6, f);
			ps.setString(7, g);

			ps.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex);

			return "didn't save";
		}

		return "saved";

	}

	/*
	 * Returns all assets
	 */
	public ArrayList<String> showAll() {
		ArrayList<String> assetsNames = new ArrayList<>();
		// String result = "";
		try {

			String inst = "SELECT * FROM Asset";

			Statement st = connection.createStatement();
			ResultSet re = st.executeQuery(inst);

			// name, category, locationName, purchaseDate, description, purchasedValue,
			// expDate

			while (re.next()) {
				// result += re.getString("name") + "-->" + re.getString("description") + ", ";
				assetsNames.add(re.getString("name"));

			}
			return assetsNames;

		} catch (SQLException ex) {
			System.out.println(ex);

			return assetsNames;
		}

	}
	
	/*
	 * Gets all expired assets
	 */
	public ArrayList<TableAsset> getExpiredAssets() {
	    ArrayList<TableAsset> expiredAssets = new ArrayList<>();
	    // Ensure that expDate is not null and less than the current date
	    String query = "SELECT * FROM Asset WHERE expDate IS NOT NULL AND expDate < ?";
	    try {
	    	PreparedStatement ps = connection.prepareStatement(query);
	    	ps.setString(1, LocalDate.now().toString());  // This should match the format in your DB.
	    	ResultSet rs = ps.executeQuery();
	    	while (rs.next()) {
	    	    String expDate = rs.getString("expDate");
	    	    System.out.println("Expiry Date: " + expDate);  // Check what dates are being considered.
	    	    if (expDate != null && !expDate.isEmpty()) {
	    	        expiredAssets.add(new TableAsset(
	    	            rs.getString("name"),
	    	            rs.getString("category"),
	    	            rs.getString("locationName"),
	    	            rs.getString("purchaseDate"),
	    	            rs.getString("description"),
	    	            rs.getString("purchasedValue"),
	    	            expDate
	    	        ));
	    	    }

	        }
	    } catch (SQLException ex) {
	        System.out.println("Error fetching expired assets: " + ex.getMessage());
	    }
	    return expiredAssets;
	}

	/*
	 * Checks if the code is connected to the database
	 */
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
