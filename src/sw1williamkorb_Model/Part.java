package sw1williamkorb_Model;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.property.*;

/**
 *
 * @author wckor
 */
public abstract class Part {
    private final SimpleIntegerProperty partID= new SimpleIntegerProperty(0);
    private final SimpleStringProperty partName=new SimpleStringProperty("");
    private final SimpleDoubleProperty partCost=new SimpleDoubleProperty(0.0);
    private final SimpleIntegerProperty inStock = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty min = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty max = new SimpleIntegerProperty(0);
    
    private static final AtomicInteger generatedID = new AtomicInteger();

    //returns a new sequential id each time function is run
    public static int generateID(){
        return generatedID.incrementAndGet();
    }
    
    public void setPartName(String name){
        partName.set(name);
    }
    
    public void setPartID(int ID){
        partID.set(ID);
    }
    
    public void setPartCost(double cost){
        partCost.set(cost);
    }
    
    public void setInStock(int stock){
        inStock.set(stock);
    }
    
    public void setMin (int Min){
        min.set(Min);
    }
    
    public void setMax (int Max){
        max.set(Max);
    }
    
    public String getPartName(){
        return partName.get();
    }
    
    public int getPartID(){
        return partID.get();
    }
    
    public double getPartCost(){
        return partCost.get();
    }
    
    public int getInStock(){
        return inStock.get();
    }
    
    public int getMin(){
        return min.get();
    }
    
    public int getMax(){
        return max.get();
    }
    
    public IntegerProperty partIDProperty(){
        return partID;
    }
    
    public StringProperty partNameProperty(){
        return partName;
    }
    
    public DoubleProperty partCostProperty(){
        return partCost;
    }
    
    public IntegerProperty partInStockProperty(){
        return inStock;
    }
    
    public IntegerProperty partMinProperty(){
        return min;
    }
    
    public IntegerProperty partMaxProperty(){
        return max;
    }
}
