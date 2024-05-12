package application.controller;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCategoryController implements Initializable {

	public CategoryModel category = new CategoryModel();

	@FXML
	private Label isConnected;

	@FXML
	private TextField categoryName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (category.isDbConnected()) {
			isConnected.setText("Connected");
		} else {
			isConnected.setText("Not connected");
		}

		category.createTable();

	}

	/*
	 * Creates a category and stores it in the database
	 */
	@FXML
	public void createCategoryOp() {

		String catText = categoryName.getText();

		category.saveCategory(catText);
		System.out.println(catText);
		System.out.println("working");

		isConnected.setText("Saved");

	}

	/*
	 * Displays categories in console
	 */
	@FXML
	public void displayCat() {
		ArrayList<String> allText = category.showAll();
		//isConnected.setText("Categories Saved: " + allText);
		for (int i = 0; i < allText.size(); i++) {
			System.out.println(allText.get(i));
		}

	}

}
