package sw1williamkorb_View;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sw1williamkorb_Model.*;

/**
 * FXML Controller class
 *
 * @author wckor
 */
public class ModProductController {

    @FXML
    private TableColumn<Part, Integer> PartIDColumn;
    @FXML
    private TableColumn<Part, String> PartNameColumn;
    @FXML
    private TableColumn<Part, Integer> PartInvLvlColumn;
    @FXML
    private TableColumn<Part, Double> PartCostColumn;
    @FXML
    private TextField NameField;
    @FXML
    private TextField InvLvlField;
    @FXML
    private TextField CostField;
    @FXML
    private TextField MaxField;
    @FXML
    private TextField MinField;
    @FXML
    private TextField PartSearchField;
    @FXML
    private Button PartSearchBtn;
    @FXML
    private TableColumn<Part, Integer> PartIDColumn1;
    @FXML
    private TableColumn<Part, String> PartNameColumn1;
    @FXML
    private TableColumn<Part, Integer> PartInvLvlColumn1;
    @FXML
    private TableColumn<Part, Double> PartCostColumn1;
    @FXML
    private Button AddSelectedPartBtn;
    @FXML
    private Button CancelBtn;
    @FXML
    private Button SaveBtn;
    @FXML
    private Label IDLabel;
    @FXML
    public TableView<Part> partsTable;
    @FXML
    public TableView<Part> associatedPartsTable;
    @FXML
    public ObservableList<Part> searchResult = FXCollections.observableArrayList();
    
    private Product tempproduct = MainMenuController.getTempProduct();
    private ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

    public void initialize() {
        tempAssociatedParts.setAll(tempproduct.associatedParts);
        
        IDLabel.setText(Integer.toString(tempproduct.getProductID()));
        NameField.setText(tempproduct.getProductName());
        CostField.setText(Double.toString(tempproduct.getProductCost()));
        InvLvlField.setText(Integer.toString(tempproduct.getInStock()));
        MaxField.setText(Integer.toString(tempproduct.getMax()));
        MinField.setText(Integer.toString(tempproduct.getMin()));
        
        //set up the columns for parts table
        PartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        PartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        PartInvLvlColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        PartCostColumn.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
        partsTable.setItems(Inventory.parts);
        
        //set up the columns for the associated parts table
        PartIDColumn1.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        PartNameColumn1.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        PartInvLvlColumn1.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        PartCostColumn1.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
        associatedPartsTable.setItems(tempproduct.associatedParts);
    }    
    
    @FXML
    private void CancelBtnHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("If you cancel, you will lose any changes made.");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            tempproduct.associatedParts.setAll(tempAssociatedParts);//this will reset the associated parts list to what it was, in case changes were made
            Stage stage = (Stage) CancelBtn.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void SaveBtnHandler(ActionEvent event){
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Changes");
        alert.setHeaderText("Changes to this product will be saved to the inventory.");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        
        //total up the cost of all the parts in the associatedPartsTable
        double totalPartsCost = 0 ;
        for (Part part : associatedPartsTable.getItems()) {
            totalPartsCost = totalPartsCost + part.getPartCost();
        }
        
        boolean valid = true;//this will make the form input valid unless it fails one of the below checks
        
        //to prevent multiple error pop-ups and unhandled exceptions
        boolean passedEmptyCheck = false;
        boolean passedFormatCheck = false;
        boolean passedMaxMinCheck = false;
        
        //get info from text fields
        String invlvl=InvLvlField.getText();
        String name=NameField.getText();
        String cost=CostField.getText();
        String max=MaxField.getText();
        String min=MinField.getText();
        
        
        //check if any fields are empty
        if (invlvl.isEmpty() || name.isEmpty() || cost.isEmpty() || max.isEmpty() || min.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("Form is Incomplete!");
            alert.setContentText("Please be sure all fields are filled out before saving.");

            alert.showAndWait();
            valid = false;
        }
        else{
            passedEmptyCheck = true;
        }
        
        //check that inv, max, and min fields only have whole numbers
        if ((!invlvl.matches("[0-9]+") || !max.matches("[0-9]+") || !min.matches("[0-9]+")) && (passedEmptyCheck)){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("Format Error");
            alert.setContentText("Inv, Max, and Min fields must contain only whole numbers!");

            alert.showAndWait();
            valid = false;
        }
        else{
            passedFormatCheck = true;
        }
        
        //check that cost field contains a number
        try{
            Double.parseDouble(cost);
        }
        catch(NumberFormatException e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("Format Error");
            alert.setContentText("Cost field must contain only whole or decimal numbers!");

            alert.showAndWait();
            valid = false;
            passedFormatCheck = false;
        }
        
        if ((passedEmptyCheck) && (passedFormatCheck) && (Integer.parseInt(max) < Integer.parseInt(min))){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("Max/Min Error");
            alert.setContentText("Max value must be equal to or greater than Min value!");

            alert.showAndWait();
            valid = false;
        }
        else{
            passedMaxMinCheck = true;
        }
        
        //check if inventory is out of bounds
        if (((passedEmptyCheck) && (passedFormatCheck) && (passedMaxMinCheck)) && (Integer.parseInt(invlvl) > Integer.parseInt(max) || Integer.parseInt(invlvl) < Integer.parseInt(min))){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("Inventory Level Error");
            alert.setContentText("Inventory cannot be less than Min or more than Max!");

            alert.showAndWait();
            valid = false;
        }
        
        //if user-entered price is less than the total cost of associated parts, invalidate the form
        if (Double.parseDouble(cost) < totalPartsCost){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("Pricing Error");
            alert.setContentText("Cost of the product cannot be less than the cost of the parts!");

            alert.showAndWait();
            valid = false;
        }
        
        //check that at least one associated part exists
        if (tempproduct.associatedParts.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("Associated Parts Error");
            alert.setContentText("A product must have at least one associated part!");

            alert.showAndWait();
            valid = false;
        }
        
        //if input passes all of the above checks, save it to inventory and close the window
        if (valid){
            //remove the old part
            int productID = MainMenuController.getTempProduct().getProductID();
            Inventory.removeProduct(productID);
            
            tempproduct.setProductID(productID);
            tempproduct.setProductName(name);
            tempproduct.setProductCost(Double.parseDouble(cost));
            tempproduct.setInStock(Integer.parseInt(invlvl));
            tempproduct.setMin(Integer.parseInt(min));
            tempproduct.setMax(Integer.parseInt(max));
            Inventory.addProduct(tempproduct);
            
            Stage stage = (Stage) SaveBtn.getScene().getWindow();
            stage.close();
        }
        }
        
    }
    
    @FXML
    private void AddSelectedPartBtnHandler(ActionEvent event){
        tempproduct.addAssociatedPart(partsTable.getSelectionModel().getSelectedItem());
        associatedPartsTable.setItems(tempproduct.associatedParts);
    }
    
    @FXML
    private void RemoveSelectedPartBtnHandler(ActionEvent event){
        tempproduct.removeAssociatedPart(associatedPartsTable.getSelectionModel().getSelectedItem().getPartID());
    }
    
    @FXML
    private void SearchPartsBtnHandler(ActionEvent event){
        searchResult.clear(); //prevents search results piling on top of one another
        
        String searchItem=PartSearchField.getText(); //retrieve text from the search field
        
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
    
    public void ExitHandler(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("If you cancel, you will lose any changes made.");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            tempproduct.associatedParts.setAll(tempAssociatedParts);//this will reset the associated parts list to what it was, in case changes were made
            Stage stage = (Stage) CancelBtn.getScene().getWindow();
            stage.close();
        }
    }
}
