package sw1williamkorb_View;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sw1williamkorb_Model.*;

/**
 * FXML Controller class
 *
 * @author wckor
 */
public class AddPartController {

    @FXML
    private RadioButton InHouseRadioBtn;
    @FXML
    private ToggleGroup Origin;
    @FXML
    private RadioButton OutsourcedRadioBtn;
    @FXML
    private TextField NameField;
    @FXML
    private TextField InvLvlField;
    @FXML
    private TextField CostField;
    @FXML
    private TextField MachineIDField;
    @FXML
    private TextField CompanyNameField;
    @FXML
    private TextField MaxField;
    @FXML
    private TextField MinField;
    @FXML
    private Button CancelBtn;
    @FXML
    private Button SaveBtn;
    @FXML
    private Label CompanyNameLabel;
    @FXML
    private Label MachineIDLabel;
    
    
    @FXML
    public void initialize() {
        InvLvlField.setText("0");
        MinField.setText("0");
        MaxField.setText("0");
        CompanyNameField.setVisible(false);
        CompanyNameLabel.setVisible(false);
    }

    @FXML
    private void OutsourcedRadioBtnHandler(ActionEvent event){
        MachineIDField.setVisible(false);
        MachineIDLabel.setVisible(false);
        CompanyNameField.setVisible(true);
        CompanyNameLabel.setVisible(true);
    }
    
    @FXML
    private void InHouseRadioBtnHandler(ActionEvent event){
        CompanyNameField.setVisible(false);
        CompanyNameLabel.setVisible(false);
        MachineIDField.setVisible(true);
        MachineIDLabel.setVisible(true);
    }

    @FXML
    private void CancelBtnHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("If you cancel, you will lose any changes made.");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) CancelBtn.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void SaveBtnHandler(ActionEvent event){
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("You are about to add this part to the inventory.");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        
        boolean valid = true;
        
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
        
        //check that max is not less than min
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
        
        //if all validation checks pass, add part to inventory
        if(valid){
        if (InHouseRadioBtn.isSelected()){
            String machineID=MachineIDField.getText();
            if (machineID.isEmpty()){
                machineID = "0";//if no machineID is entered, default value of 0 is specified
            }
            //if user enters something other than a number for Machine ID, show error message and set value to 0
            if (!machineID.matches("[0-9]+")){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Invalid");
                alert.setHeaderText("Format Error");
                alert.setContentText("Machine ID must be a whole number! Value will be set to 0.");

                alert.show();
                machineID = "0";//if invalid machineID is entered, default value of 0 is specified
            }
            InhousePart newpart = new InhousePart(Part.generateID(), name, Double.parseDouble(cost), Integer.parseInt(invlvl), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(machineID));
            Inventory.addPart(newpart);
        }
        else if (OutsourcedRadioBtn.isSelected()){
            String company=CompanyNameField.getText();
            if (company.isEmpty()){
                company = "NA";//if no company is entered, default value of NA is specified
            }
            OutsourcedPart newpart = new OutsourcedPart(Part.generateID(), name, Double.parseDouble(cost), Integer.parseInt(invlvl), Integer.parseInt(min), Integer.parseInt(max), company);
            Inventory.addPart(newpart);
        }
        else {
            System.out.println("Error: Must select Inhouse or Outsourced!");
        }
        
        Stage stage = (Stage) SaveBtn.getScene().getWindow();
        stage.close();
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
            Stage stage = (Stage) CancelBtn.getScene().getWindow();
            stage.close();
        }
    }
    
}
