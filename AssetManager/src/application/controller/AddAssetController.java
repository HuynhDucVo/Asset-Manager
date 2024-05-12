package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class AddAssetController implements Initializable {

    public CategoryModel category = new CategoryModel();
    public LocationModel locations = new LocationModel();
    public AssetModel assets = new AssetModel();

    @FXML
    HBox mainBox;

    @FXML
    private ComboBox<String> categorydropdownlist;

    @FXML
    private ComboBox<String> locationdropdownlist;
    
    @FXML
    private ComboBox<String> categorySearch;
    
    @FXML 
    private ComboBox<String> locationSearch;

    @FXML
    private Label isConnected;

    @FXML
    TextField assetName;

    @FXML
    DatePicker purchaseBox;

    @FXML
    TextArea descirptionBox;

    @FXML
    TextField valueBox;

    @FXML
    DatePicker expBox;

    @FXML
    TextField txtSearch;

    @FXML
    TableView<TableAsset> table;

    @FXML
    TableColumn<TableAsset, String> asset_name;
    @FXML
    TableColumn<TableAsset, String> category_col;
    @FXML
    TableColumn<TableAsset, String> location_col;
    @FXML
    TableColumn<TableAsset, String> purchase_col;
    @FXML
    TableColumn<TableAsset, String> value_col;
    @FXML
    TableColumn<TableAsset, String> exp_col;
    @FXML
    TableColumn<TableAsset, String> description_col;

    ObservableList<TableAsset> viewList = FXCollections.observableArrayList();

    private TableAsset selectedAsset;
    private String txtFilter = "";
    private String categoryFilter = "";
    private String locationFilter = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            assets.createTable();

        } catch (Exception e) {
            // Handle exception
        }

        if (category.isDbConnected()) {
            System.out.println("Connected");
        } else {
            System.out.println("Not connected");
        }

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectAsset(newValue));

        // Fill the drop down menus
        categorydropdownlist.setItems(FXCollections.observableList(category.showAll()));
        locationdropdownlist.setItems(FXCollections.observableList(locations.showAll()));
        ArrayList<String> categoryList = category.showAll();
        categoryList.add(0, categorySearch.getPromptText());
        ArrayList<String> locationList = locations.showAll();
        locationList.add(0, locationSearch.getPromptText());
        categorySearch.setItems(FXCollections.observableList(categoryList));
        locationSearch.setItems(FXCollections.observableList(locationList));
     
        // Set the table
        updateTable();
        
        // Set up the search functions
        setupSearchFilter();
    }

    /*
     * When the user clicks on an asset, display all of the asset information
     * in the text fields and areas at the top of the page. This allows them
     * to edit and update them.
     */
    public void selectAsset(TableAsset asset) {
        if (asset != null) {
            selectedAsset = asset;
            assetName.setText(asset.getName());
            categorydropdownlist.setValue(asset.getCategory());
            locationdropdownlist.setValue(asset.getLocation());
            if (!asset.getPurchase_date().equals("")) {
                String purchaseDateStr = asset.getPurchase_date();
                LocalDate purchaseDate = LocalDate.of(Integer.parseInt(purchaseDateStr.substring(0, 4)),
                        Integer.parseInt(purchaseDateStr.substring(5, 7)),
                        Integer.parseInt(purchaseDateStr.substring(8)));
                purchaseBox.setValue(purchaseDate);
            } else {
                purchaseBox.getEditor().clear();
            }

            if (!asset.getExp_date().equals("")) {
                String expDateStr = asset.getExp_date();
                LocalDate expDate = LocalDate.of(Integer.parseInt(expDateStr.substring(0, 4)),
                        Integer.parseInt(expDateStr.substring(5, 7)),
                        Integer.parseInt(expDateStr.substring(8)));
                expBox.setValue(expDate);
            } else {
                expBox.getEditor().clear();
            }
            valueBox.setText(asset.getValue());
            descirptionBox.setText(asset.getDescription());

        }
    }

    /*
     * Fills the table with assets
     */
    public void updateTable() {
        table.getItems().clear();

        asset_name.setCellValueFactory(new PropertyValueFactory<TableAsset, String>("name"));
        category_col.setCellValueFactory(new PropertyValueFactory<TableAsset, String>("category"));
        location_col.setCellValueFactory(new PropertyValueFactory<TableAsset, String>("location"));
        purchase_col.setCellValueFactory(new PropertyValueFactory<TableAsset, String>("purchase_date"));
        value_col.setCellValueFactory(new PropertyValueFactory<TableAsset, String>("value"));
        exp_col.setCellValueFactory(new PropertyValueFactory<TableAsset, String>("exp_date"));
        description_col.setCellValueFactory(new PropertyValueFactory<TableAsset, String>("description"));

        try {

            String inst = "SELECT * FROM Asset";

            Statement st = assets.connection.createStatement();
            ResultSet re = st.executeQuery(inst);

            while (re.next()) {
                viewList.add(new TableAsset(re.getString("name"), re.getString("category"),
                        re.getString("locationName"), re.getString("purchaseDate"), re.getString("description"),
                        re.getString("purchasedValue"), re.getString("expDate")));

            }
            table.setItems(viewList);

        } catch (Exception e) {
            // Handle exception
        }

    }

    /*
     * Creates a new asset with all data given from the user
     */
    @FXML
    public void createNewAsset() {
        if (assetName.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter asset name.");
            alert.showAndWait();
        } else if (categorydropdownlist.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select the category.");
            alert.showAndWait();
        } else if (locationdropdownlist.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select the location.");
            alert.showAndWait();
        } else {
            String a = "";
            String b = "";
            String c = "";
            String d = "";
            String e = "";
            String f = "";
            String g = "";

            if (assetName.getText() != null) {
                a = assetName.getText();
            }

            if (categorydropdownlist.getValue() != null) {
                b = categorydropdownlist.getValue();
            }

            if (locationdropdownlist.getValue() != null) {
                c = locationdropdownlist.getValue();
            }

            if (purchaseBox.getValue() != null) {
                d = purchaseBox.getValue().toString();
            }

            if (descirptionBox.getText() != null) {
                e = descirptionBox.getText();
            }

            if (valueBox.getText() != null) {
                f = valueBox.getText();
            }

            if (expBox.getValue() != null) {
                g = expBox.getValue().toString();
            }

            TableAsset addedAsset = new TableAsset(a, b, c, d, e, f, g);
            assets.saveAsset(a, b, c, d, e, f, g);
            viewList.add(addedAsset);
        }

        deselect();
    }

    /*
     * Deselects the asset in the table. This also removes all the data from the 
     * data entry fields above.
     */
    public void deselect() {
        //if (selectedAsset != null) {
        assetName.clear();
        categorydropdownlist.valueProperty().set(null);
        locationdropdownlist.valueProperty().set(null);
        purchaseBox.getEditor().clear();
        purchaseBox.setValue(null);
        expBox.getEditor().clear();
        expBox.setValue(null);
        valueBox.clear();
        descirptionBox.clear();
        //}
        selectedAsset = null;
        table.getSelectionModel().clearSelection();
    }

    /*
     * Changes the data of the selected asset to be the data in the entry fields
     */
    @FXML
    public void updateAssetOp() {
    	if(selectedAsset == null)
    	{
    		
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an asset to edit.");
            alert.showAndWait();
    		return;
    	}
    	
    	// Get the inputs from the input fields
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";
        String f = "";
        String g = "";

        if (assetName.getText() != null) {
            a = assetName.getText();
        }

        if (categorydropdownlist.getValue() != null) {
            b = categorydropdownlist.getValue();
        }

        if (locationdropdownlist.getValue() != null) {
            c = locationdropdownlist.getValue();
        }

        if (purchaseBox.getValue() != null) {
            d = purchaseBox.getValue().toString();
        }

        if (descirptionBox.getText() != null) {
            e = descirptionBox.getText();
        }

        if (valueBox.getText() != null) {
            f = valueBox.getText();
        }

        if (expBox.getValue() != null) {
            g = expBox.getValue().toString();
        }

        // Update the asset with the new input values
        TableAsset updatedAsset = new TableAsset(a, b, c, d, e, f, g);
        assets.updateAsset(selectedAsset, updatedAsset);
        selectedAsset.setName(a);
        selectedAsset.setCategory(b);
        selectedAsset.setLocation(c);
        selectedAsset.setPurchase_date(d);
        selectedAsset.setDescription(e);
        selectedAsset.setValue(f);
        selectedAsset.setExp_date(g);
        table.refresh();
        deselect();
    }

    /*
     * Deletes the selected asset and removes the data from the entry fields
     */
    @FXML
    public void deleteAssetOp() {
    	if(selectedAsset == null)
    	{
    		
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an asset to delete.");
            alert.showAndWait();
    		return;
    	}
    	
        assets.deleteAsset(selectedAsset);
        viewList.remove(selectedAsset);
        deselect();
    }

    /*
     * Get input from the deselect button
     */
    @FXML
    public void deselectAssetOp() {
        table.getSelectionModel().clearSelection();
        deselect();
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
     * Sets up the search functions
     */
    private void setupSearchFilter() {
        FilteredList<TableAsset> filteredData = new FilteredList<>(viewList, b -> true);
        // Search filter by substring
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
        	filteredData.setPredicate(asset -> {
                if (newValue == null || newValue.isEmpty()) {
                    //return true;
                	txtFilter = "";
                }
                else
                {
                	txtFilter = newValue.toLowerCase();
                }
                return search(asset);
            });
        });

        // Search filter by location
        locationSearch.valueProperty().addListener((observable, oldValue, newValue) -> {
        	filteredData.setPredicate(asset -> {
                if (newValue == null || locationSearch.getValue().equals(locationSearch.getPromptText())) {
                    locationFilter = "";
                }
                else
                {
                	locationFilter = locationSearch.getValue().toLowerCase();
                }
                return search(asset);
            });
        });
        
        // Search filter by category
        categorySearch.valueProperty().addListener((observable, oldValue, newValue) -> {
        	filteredData.setPredicate(asset -> {
                if (newValue == null || categorySearch.getValue().equals(categorySearch.getPromptText())) {
                    categoryFilter = "";
                }
                else
                {
                	categoryFilter = categorySearch.getValue().toLowerCase();
                }
                return search(asset);
            });
        });
        SortedList<TableAsset> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    
    /*
     * Search function helper method. It allows the search to combine multiple
     * different search criteria. For example, the user can search by location
     * and category and it will show only assets that match both of them
     */
    private boolean search(TableAsset asset)
    {
    	return asset.getName().toLowerCase().contains(txtFilter) &&
    		   asset.getCategory().toLowerCase().contains(categoryFilter) &&
    		   asset.getLocation().toLowerCase().contains(locationFilter);
    }
}
