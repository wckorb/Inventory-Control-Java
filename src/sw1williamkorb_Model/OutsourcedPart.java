package sw1williamkorb_Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author wckor
 */
public class OutsourcedPart extends Part {
    private final SimpleStringProperty companyName = new SimpleStringProperty("");
    
    public OutsourcedPart(int partID, String partName, double partCost, int inStock, int min, int max, String company){
        setPartID(partID);
        setPartName(partName);
        setPartCost(partCost);
        setInStock(inStock);
        setMin(min);
        setMax(max);
        setCompanyName(company);
    }
    
    private void setCompanyName(String name){
        companyName.set(name);
    }
    
    public String getCompanyName(){
        return companyName.get();
    }
    
    public StringProperty companyNameProperty(){
        return companyName;
    }
}
