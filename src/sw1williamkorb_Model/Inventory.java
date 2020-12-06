package sw1williamkorb_Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author wckor
 */
public class Inventory {
    public static ObservableList<Part> parts=FXCollections.observableArrayList();
    public static ObservableList<Product> products=FXCollections.observableArrayList();

    public static void addProduct(Product newProduct) {
        products.add(newProduct);
    }
    
    public static boolean removeProduct(int productID){
        products.removeIf(product -> product.getProductID() == productID);
        return true;
    }
    
    public static void addPart(Part newPart) {
        parts.add(newPart);
    }
    
    public static boolean removePart(int partID){
        parts.removeIf(part -> part.getPartID() == partID);
        return true;
    }

}
