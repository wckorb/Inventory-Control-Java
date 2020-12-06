package sw1williamkorb_Model;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author wckor
 */
public class Product {
    private final SimpleIntegerProperty productID= new SimpleIntegerProperty(0);
    private final SimpleStringProperty productName=new SimpleStringProperty("");
    private final SimpleDoubleProperty productCost=new SimpleDoubleProperty(0.0);
    private final SimpleDoubleProperty partsCost=new SimpleDoubleProperty(0.0);
    private final SimpleIntegerProperty inStock = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty min = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty max = new SimpleIntegerProperty(0);

    public ObservableList<Part> associatedParts=FXCollections.observableArrayList();
    
    public Product(){
        this(0,"",0,0,0,0);
    }
    
    public Product(int productID, String productName, double productCost, int inStock, int min, int max){
        setProductID(productID);
        setProductName(productName);
        setProductCost(productCost);
        setInStock(inStock);
        setMin(min);
        setMax(max);
    }
    
    private static final AtomicInteger generatedID = new AtomicInteger();

    //returns a new sequential id each time function is run
    public static int generateID(){
        return generatedID.incrementAndGet();
    }

    public void setProductName(String name){
        productName.set(name);
    }
    
    public void setProductID(int ID){
        productID.set(ID);
    }
    
    public void setProductCost(double cost){
        productCost.set(cost);
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
    
    public String getProductName(){
        return productName.get();
    }
    
    public int getProductID(){
        return productID.get();
    }
    
    public double getProductCost(){
        return productCost.get();
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
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int PartID) {
        associatedParts.removeIf(part -> part.getPartID() == PartID);
        return true;
    }
    
    public IntegerProperty productIDProperty(){
        return productID;
    }
    
    public StringProperty productNameProperty(){
        return productName;
    }
    
    public DoubleProperty productCostProperty(){
        return productCost;
    }
    
    public IntegerProperty productInStockProperty(){
        return inStock;
    }
    
    public IntegerProperty productMinProperty(){
        return min;
    }
    
    public IntegerProperty productMaxProperty(){
        return max;
    }
}
