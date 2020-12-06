package sw1williamkorb_View;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sw1williamkorb_Model.*;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author wckor
 */
public class MainMenuController {

    @FXML
    public TableView<Part> partsTable;
    @FXML
    public TableView<Product> productsTable;
    @FXML
    private Label PartsLabel;
    @FXML
    private TextField PartsSearchField;
    @FXML
    private Button PartsSearchBtn;
    @FXML
    private TableColumn<Part, Integer> PartIDColumn;
    @FXML
    private TableColumn<Part, String> PartNameColumn;
    @FXML
    private TableColumn<Part, Integer> PartInvLvlColumn;
    @FXML
    private TableColumn<Part, Double> PartCostColumn;
    @FXML
    private Button PartsDelBtn;
    @FXML
    private Button PartsModBtn;
    @FXML
    private Button PartsAddBtn;
    @FXML
    private Label ProductsLabel;
    @FXML
    private TextField ProductsSearchField;
    @FXML
    private Button ProductsSearchBtn;
    @FXML
    private TableColumn<Product, Integer> ProductIDColumn;
    @FXML
    private TableColumn<Product, String> ProductNameColumn;
    @FXML
    private TableColumn<Product, Integer> ProductInvLvlColumn;
    @FXML
    private TableColumn<Product, Double> ProductCostColumn;
    @FXML
    private Button ProductsDelBtn;
    @FXML
    private Button ProductsModBtn;
    @FXML
    private Button ProductsAddBtn;
    @FXML
    private Button ExitBtn;
    @FXML
    public ObservableList<Part> searchResult = FXCollections.observableArrayList();
    @FXML
    public ObservableList<Product> searchResult2 = FXCollections.observableArrayList();
    
    
    //temporary variables to be used for passing information to modify screens
    public static Part tempPart;
    public static InhousePart tempInhousePart;
    public static OutsourcedPart tempOutsourcedPart;
    public static Product tempProduct;
    
    @FXML
    public void initialize() {
        
        //set up the columns for parts table
        PartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        PartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        PartInvLvlColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        PartCostColumn.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
        partsTable.setItems(Inventory.parts);
        
        //set up the columns for products table
        ProductIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        ProductNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        ProductInvLvlColumn.setCellValueFactory(cellData -> cellData.getValue().productInStockProperty().asObject());
        ProductCostColumn.setCellValueFactory(cellData -> cellData.getValue().productCostProperty().asObject());
        productsTable.setItems(Inventory.products);

    }  
    
    //opens the Add Part window
    public void addPartBtnHandler(ActionEvent event){
        Parent root;
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sw1williamkorb_View/AddPart.fxml"));
            root = loader.load();
            AddPartController controller = loader.getController();
            Stage addWindow = new Stage();
            addWindow.setTitle("Add Part");
            addWindow.setScene(new Scene(root, 345, 491));
            addWindow.setOnCloseRequest(e -> controller.ExitHandler());
            addWindow.show();
        }
        catch (IOException e) {
            System.out.println("FXML Not Found");
            e.printStackTrace();
        }
        
    }
    
    //opens the Modify Part window
    public void modifyPartBtnHandler(ActionEvent event){
        
        if (!partsTable.getSelectionModel().isEmpty()){
        //Check to see if selected part is inhouse or outsourced
        if (partsTable.getSelectionModel().getSelectedItem() instanceof InhousePart){
            tempPart = (InhousePart) partsTable.getSelectionModel().getSelectedItem();
            tempInhousePart = (InhousePart) tempPart;
        }
        
        if (partsTable.getSelectionModel().getSelectedItem() instanceof OutsourcedPart){
            tempPart = (OutsourcedPart) partsTable.getSelectionModel().getSelectedItem();
            tempOutsourcedPart = (OutsourcedPart) tempPart;
        }
        
        //load the modify window
        Parent root;
        try {
            //root = FXMLLoader.load(getClass().getClassLoader().getResource("sw1williamkorb_View/ModPart.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sw1williamkorb_View/ModPart.fxml"));
            root = loader.load();
            ModPartController controller = loader.getController();
            Stage addWindow = new Stage();
            addWindow.setTitle("Modify Part");
            addWindow.setScene(new Scene(root, 345, 491));
            addWindow.setOnCloseRequest(e -> controller.ExitHandler());
            addWindow.show();
        }
        catch (IOException e) {
            System.out.println("FXML Not Found");
            e.printStackTrace();
        }
        
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please make sure a part is selected before trying to modify!");

            alert.showAndWait();
        }
        
        
    }
    
    //deletes selected part from inventory
    public void deletePartBtnHandler(ActionEvent event){
        if (!partsTable.getSelectionModel().isEmpty()){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("You are about to delete this part.");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Inventory.removePart(partsTable.getSelectionModel().getSelectedItem().getPartID());
            partsTable.setItems(Inventory.parts);
        }
        }
        else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please make sure a part is selected before trying to delete!");

            alert.showAndWait();
        }
    }
    
    
    public void searchPartsBtnHandler(ActionEvent event){
        searchResult.clear(); //prevents search results piling on top of one another
        
        String searchItem=PartsSearchField.getText(); //retrieve text from the search field
        
        //check if search field is empty, reset the table if so
        if (searchItem.equals("")){
                partsTable.setItems(Inventory.parts); //reset the parts table
            }
        //if user enters a number, run a search for part ID
        else{
            boolean found=false;
            try{
            int itemNumber=Integer.parseInt(searchItem);
            for(Part p: Inventory.parts){
                if(p.getPartID()==itemNumber){
                    found=true;
                    searchResult.add(p);

                    partsTable.setItems(searchResult);
            
                }
            
        }
            
        //return error message if there are no matches
        if (found==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Part Not Found!");
            alert.setContentText("Please try another search.");

            alert.showAndWait();
        }
    }
            
    //if user enters something other than a number, run search for part name
    catch(NumberFormatException e){
        for(Part p: Inventory.parts){
            if(p.getPartName().equals(searchItem)){
                found=true;

                searchResult.add(p);
                partsTable.setItems(searchResult);
            }
        }
        
        //return error message if there are no matches
        if (found==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Part Not Found!");
            alert.setContentText("Please try another search.");

            alert.showAndWait();
        }
    }
    }
    }
    
    public void addProductBtnHandler(ActionEvent event){
        Parent root;
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sw1williamkorb_View/AddProduct.fxml"));
            root = loader.load();
            AddProductController controller = loader.getController();
            Stage addWindow = new Stage();
            addWindow.setTitle("Add Product");
            addWindow.setScene(new Scene(root, 782, 502));
            addWindow.setOnCloseRequest(e -> controller.ExitHandler());
            addWindow.show();
            
        }
        catch (IOException e) {
            System.out.println("FXML Not Found");
            e.printStackTrace();
        }
    }
    
    public void modifyProductBtnHandler(ActionEvent event){
        if (!productsTable.getSelectionModel().isEmpty()){
        //set up the tempProduct to pass information to the modify window
        tempProduct = productsTable.getSelectionModel().getSelectedItem();
        
        //load the modify window
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sw1williamkorb_View/ModProduct.fxml"));
            root = loader.load();
            ModProductController controller = loader.getController();
            Stage window = new Stage();
            window.setTitle("Modify Product");
            window.setScene(new Scene(root, 782, 502));
            window.setOnCloseRequest(e -> controller.ExitHandler());
            window.show();
        }
        catch (IOException e) {
            System.out.println("FXML Not Found");
            e.printStackTrace();
        }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please make sure a product is selected before trying to modify!");

            alert.showAndWait();
        }
    }
    
    public void deleteProductBtnHandler(ActionEvent event){
        if (!productsTable.getSelectionModel().isEmpty()){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("If you delete this product, its associated parts must be cleared.");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Inventory.removeProduct(productsTable.getSelectionModel().getSelectedItem().getProductID());
            productsTable.setItems(Inventory.products);
        }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please make sure a product is selected before trying to delete!");

            alert.showAndWait();
        }
    }
    
    public void searchProductsBtnHandler(ActionEvent event){
        searchResult2.clear(); //prevents search results piling on top of one another
        
        String searchItem=ProductsSearchField.getText(); //retrieve text from the search field
        
        //check if search field is empty, reset the table if so
        if (searchItem.equals("")){
                productsTable.setItems(Inventory.products); //reset the parts table
            }
        //if user enters a number, run a search for part ID
        else{
            boolean found=false;
            try{
            int itemNumber=Integer.parseInt(searchItem);
            for(Product p: Inventory.products){
                if(p.getProductID()==itemNumber){
                    found=true;
                    searchResult2.add(p);

                    productsTable.setItems(searchResult2);
            
                }
            
        }
            
        //return error message if there are no matches
        if (found==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Product Not Found!");
            alert.setContentText("Please try another search.");

            alert.showAndWait();
        }
    }
            
    //if user enters something other than a number, run search for part name
    catch(NumberFormatException e){
        for(Product p: Inventory.products){
            if(p.getProductName().equals(searchItem)){
                found=true;

                searchResult2.add(p);
                productsTable.setItems(searchResult2);
            }
        }
        
        //return error message if there are no matches
        if (found==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Product Not Found!");
            alert.setContentText("Please try another search.");

            alert.showAndWait();
        }
    }
    }
    }
    
    //close the program
    public void exitBtnHandler(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
        }
    }
    
    //the following functions are for the retrieval of temp objects, which are used to set up modification screens
    public static Part getTempPart(){
        return tempPart;
    }
    
    public static InhousePart getTempInhousePart(){
        return tempInhousePart;
    }
    
    public static OutsourcedPart getTempOutsourcedPart(){
        return tempOutsourcedPart;
    }
    
    public static Product getTempProduct(){
        return tempProduct;
    }
    
    public boolean ExitHandler(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
            return true;
        } else {
            return false;
        }
    }
}
