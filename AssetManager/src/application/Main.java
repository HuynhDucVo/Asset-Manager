package application;

import java.io.IOException;
import java.net.URL;

import application.controller.ExpiredAssetsController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		try {
			HBox mainBox = (HBox) FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			Scene scene = new Scene(mainBox, 1200, 770);

			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			// Keep a reference of the mainBox inside the commonObjs object
			CommonObjs commonObjs = CommonObjs.getInstance();
			commonObjs.setMainBox(mainBox);
			// Automatically open the alert when the application starts
			// Use if else statement to decide when the alert is popped up
	        
			if(ExpiredAssetsController.hasExpiredAssets())
			{
				showAlert();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Displays an alert if there are any expired assets in the database
	 */
	public void showAlert() {
		Alert warningAlert = new Alert(AlertType.WARNING);
        warningAlert.setTitle("Warning Dialog");
        warningAlert.setHeaderText(null);
        warningAlert.setContentText("You have assets whose warranties are expired.");
        ButtonType okBtn = new ButtonType("OK");
        ButtonType showMeBtn = new ButtonType("Show Me");
        warningAlert.getButtonTypes().setAll(okBtn, showMeBtn);
        warningAlert.showAndWait().ifPresent(type -> {
        	CommonObjs commonObjs = CommonObjs.getInstance();
        	HBox mainBox = commonObjs.getMainBox();
            if (type == okBtn) {
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
             else {
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
        });
        
	}

	public static void main(String[] args) {
		launch(args);
	}
}
