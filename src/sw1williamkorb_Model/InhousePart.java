package sw1williamkorb_Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author wckor
 */
public class InhousePart extends Part {
    private final SimpleIntegerProperty machineID = new SimpleIntegerProperty(0);
    
    public InhousePart(){
        this(0,"",0,0,0,0,0);
    }

    public InhousePart(int partID, String partName, double partCost, int inStock, int min, int max, int machineID){
        setPartID(partID);
        setPartName(partName);
        setPartCost(partCost);
        setInStock(inStock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);
    }
    
    private void setMachineID(int ID){
        machineID.set(ID);
    }
    
    public int getMachineID(){
        return machineID.get();
    }
    
    public IntegerProperty MachineIDProperty(){
        return machineID;
    }
}
