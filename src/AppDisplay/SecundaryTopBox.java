/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppDisplay;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * HBox auxiliar para menus secundários ao MainPanel. Somente tem um butão "Back".
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class SecundaryTopBox extends HBox{
    public SecundaryTopBox(){
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        Image iconBack = new Image(getClass().getResourceAsStream("icons/back.png"), 15, 15, false, false);
        Button backBtn = new Button(" Back", new ImageView(iconBack));
        backBtn.setOnAction(e -> new MainPanel(this.getScene()));
        getChildren().add(backBtn);
    }
}
