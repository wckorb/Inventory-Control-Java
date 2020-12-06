package sw1williamkorb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sw1williamkorb_View.*;


/**
 *
 * @author wckor
 */
public class InventoryControl extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sw1williamkorb_View/MainMenu.fxml"));
            root = loader.load();
            MainMenuController controller = loader.getController();
            Stage window = new Stage();
            window.setTitle("Inventory Control System");
            window.setScene(new Scene(root, 855, 417));
            window.setOnCloseRequest(e -> { if (!controller.ExitHandler()) { e.consume(); } }); //consume window close event if user hits cancel
            window.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
