
package AppDisplay;

import BiologicalPark.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Painel inicial.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class LoginPanelView extends Application {
    private Stage primaryStage;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        
        GridPane root = new GridPane();
        root.setPadding(new Insets(10, 15, 10, 15));
        root.setHgap(10);
        root.setVgap(10);
        scene = new Scene(root, 800, 900);
        scene.setRoot(new LoginPanel(scene));
        this.primaryStage.setResizable(false);
        Stage stage = new Stage(StageStyle.DECORATED);
        this.primaryStage.setTitle("Biological Park");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
