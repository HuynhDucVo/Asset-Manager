package application.controller;

import java.time.LocalDate;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import application.SqlConnect;

public class ExpiredAssetsController {

    @FXML
    private TableView<TableAsset> table;

    @FXML
    private void initialize() {
    	System.out.println("Initializing Expired Assets View...");
        setupTableColumns();
        loadExpiredAssets();
    }

    /*
     * Sets up the table
     */
    private void setupTableColumns() {
        // Assuming the TableView in your FXML already has columns with fx:id set
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("location"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("purchase_date"));
        table.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("value"));
        table.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("exp_date"));
        table.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    /*
     * Fills the table with assets. Only fills with assets with expired warranties.
     */
    private void loadExpiredAssets() {
        AssetModel model = new AssetModel();
        ObservableList<TableAsset> data = FXCollections.observableArrayList(model.getExpiredAssets());
        table.setItems(data);
    }
    
    /*
     * Shows the selected asset information in a read-only page
     */
    @FXML
    public void showAssetInfo() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Asset Selected");
            alert.setContentText("Please select an asset in the table.");
            alert.showAndWait();
        } else {
            TableAsset selectedAsset = table.getSelectionModel().getSelectedItem();
            
            // Creating and configuring the alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asset Information");
            alert.setHeaderText("Detailed Asset Information");
            alert.setContentText(String.format("ASSET NAME: %s\nCategory: %s\nLocation: %s\nPurchase Date: %s\nPurchase Value: $%s\nWarranty Expiration Date: %s\nDescription: %s",
                                                selectedAsset.getName(), 
                                                selectedAsset.getCategory(), 
                                                selectedAsset.getLocation(), 
                                                selectedAsset.getPurchase_date(), 
                                                selectedAsset.getValue(), 
                                                selectedAsset.getExp_date(), 
                                                selectedAsset.getDescription()));
            
            // Displaying the alert
            alert.showAndWait();
        }
    }
    
    /*
     * Checks all assets to see if any are expired. Return true if at least
     * 1 expired asset exists.
     */
    public static boolean hasExpiredAssets()
    {
        AssetModel model = new AssetModel();

    	return model.getExpiredAssets().size() > 0;
    }

   

}
