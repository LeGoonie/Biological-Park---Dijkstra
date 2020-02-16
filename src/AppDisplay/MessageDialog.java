/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppDisplay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Diálogo de informação. Este painel é utilizado várias vezes para avisos e informações.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class MessageDialog extends Stage {
    
    public MessageDialog(String message){
        //Labels
        Label lbl = new Label(message);
        lbl.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14;");
        
        //Button
        Button okBtn = new Button("OK");
                
        //HBox
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 15, 15, 15));
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(lbl);
        vb.getChildren().add(okBtn);
        
        //StackPane
        StackPane pane = new StackPane();
        pane.getChildren().add(vb);
        
        //show window
        setResizable(false);
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setIconified(false);
        centerOnScreen();
        setTitle("Message");
        Scene dialogScene = new Scene(pane);
        setScene(dialogScene);
        show();
        
        //função dos butões
        okBtn.setOnAction(e -> ((Stage)okBtn.getScene().getWindow()).close());
    }
}
