package application.controller;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddLocationController implements Initializable {

	public LocationModel locat = new LocationModel();

	@FXML
	private Label status;

	@FXML
	private TextField locationName;
	@FXML
	private TextArea description;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (locat.isDbConnected()) {
			status.setText("Connected");
		} else {
			status.setText("Not connected");

		}

		locat.createTable();

	}

	/*
	 * Creates a location and stores it in the database
	 */
	@FXML
	public void createLocationOp() {

		String locText = locationName.getText();
		String str1 = description.getText();

		locat.saveLocation(locText, str1);
		
		System.out.println(locText);
		System.out.println("working");

		status.setText("Saved");

	}

	/*
	 * Shows location in console
	 */
	@FXML
	public void showAllLocationOp() {
		//String result = locat.showAll();
		//status.setText("Saved: " + result);
		//System.out.println(result);
		//	 
	}

}
