/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppDisplay;

import BiologicalPark.Client;
import BiologicalPark.ClientDAOJson;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Dialogo de Registo de um novo utilizador.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class RegisterDialog {
    public RegisterDialog(ClientDAOJson savedClients){
        //Criar o painel com a informação
        GridPane registerPane = new GridPane();
        registerPane.setPadding(new Insets(10));
        registerPane.setHgap(10);
        registerPane.setVgap(10);
        registerPane.setAlignment(Pos.CENTER);
        
        //criação dos elementos para registo
        Label titleLabel = new Label("Register");
        titleLabel.setStyle("-fx-font-weight: bold;");
        Label name = new Label("Username ");
        Label code1 = new Label("Password ");
        Label code2 = new Label("Confirm Password ");
        TextField nameField = new TextField();
        PasswordField  codeField1 = new PasswordField ();
        PasswordField  codeField2 = new PasswordField ();
        
        //Criar botões
        Button registerBtn = new Button("Registar");
        registerBtn.setOnAction((ActionEvent event) -> {
            if (!nameField.getText().equals("") && codeField1.getText().equals(codeField2.getText()) && !codeField1.getText().equals("")) {
                savedClients.saveClient(new Client(nameField.getText(), codeField1.getText()));
                ((Stage) registerBtn.getScene().getWindow()).close();
            } else {
                if (nameField.getText().equals("")) {
                    Text insertName = new Text("enter an username");
                    Font serif = Font.font("Serif", 13);
                    insertName.setFont(serif);
                    insertName.setFill(Color.RED);
                    registerPane.add(insertName, 2, 1);
                }
                System.out.println(!nameField.getText().equals(""));
                System.out.println(!codeField1.getText().equals(""));
                System.out.println(codeField1.getText().equals(codeField2));
            }
        });
        final Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> ((Stage) cancelBtn.getScene().getWindow()).close());
        
        //Adicionar elementos à gridPane
        registerPane.add(titleLabel, 0, 0);
        registerPane.add(name, 0, 1);
        registerPane.add(code1, 0, 2);
        registerPane.add(code2, 0, 3);
        registerPane.add(registerBtn, 0, 5);
        registerPane.add(nameField, 1, 1);
        registerPane.add(codeField1, 1, 2);
        registerPane.add(codeField2, 1, 3);
        registerPane.add(cancelBtn, 1, 5);
        
        //Criar janela
        Stage janela = new Stage();
        janela.setTitle("Regist new user");
        janela.setResizable(false);
        janela.initStyle(StageStyle.UTILITY);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.setIconified(false);

        janela.centerOnScreen();
        janela.setScene(new Scene(registerPane, 450, 250));
        janela.show();
    }
}
