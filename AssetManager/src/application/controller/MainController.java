package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML
	HBox mainBox;

	@FXML
	public void initialize() {
		showHomeOp();
	}

	/*
	 * Shows the home page. Runs at startup
	 */
	@FXML
	public void showHomeOp() {

		URL url = getClass().getClassLoader().getResource("view/Home.fxml");

		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}

			mainBox.getChildren().add(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Shows add asset page
	 */
	/*
	@FXML
	public void showAddAssetOp() {

		URL url = getClass().getClassLoader().getResource("view/AddAsset.fxml");

		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}

			mainBox.getChildren().add(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	*/

	/*
	 * Shows add/search/delete/update asset page
	 */
	@FXML
	public void showAssets() {

		URL url = getClass().getClassLoader().getResource("view/Assets.fxml");

		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}

			mainBox.getChildren().add(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Shows search asset page
	 */
	/*
	@FXML
	public void showSearchAssetOp() {

		URL url = getClass().getClassLoader().getResource("view/SearchAsset.fxml");

		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}

			mainBox.getChildren().add(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

	/*
	 * Shows add category page
	 */
	@FXML
	public void showAddCategoryOp() {

		URL url = getClass().getClassLoader().getResource("view/AddCategory.fxml");

		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}

			mainBox.getChildren().add(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Shows add location page
	 */
	@FXML
	public void showAddLocationOp() {

		URL url = getClass().getClassLoader().getResource("view/AddLocation.fxml");

		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}

			mainBox.getChildren().add(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Shows expired warranty page
	 */
	@FXML
	public void showExpiredWarrantyAssets() {
		URL url = getClass().getClassLoader().getResource("view/ExpiredWarrantyAssets.fxml");

		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}

			mainBox.getChildren().add(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
