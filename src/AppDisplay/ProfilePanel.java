/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppDisplay;

import BiologicalPark.Client;
import BiologicalPark.ClientDAOJson;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Painel com informações do utilizador, onde é possivel alterar a mesmas.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class ProfilePanel extends BorderPane{
    private static final ClientDAOJson savedClients = new ClientDAOJson();
    
    public ProfilePanel(Client client){
        //Labels
        Label lbl = new Label("Confirm your informations para a fatura");
        lbl.setStyle( "-fx-font-weight: bold;" + "-fx-font-size: 14;" );
        
        Label nameLbl = new Label("Name");
        Label usernameLbl = new Label("Username");
        Label emailLbl = new Label("Email");
        Label addressLbl = new Label("Address");
        Label cityLbl = new Label("City");
        Label countryLbl = new Label("Country");
        Label postalCodeLbl = new Label("PostalCode");
        Label nifLbl = new Label("NIF");
        
        //Info TextBoxes
        TextField nameField = new TextField(client.getName());
        TextField usernameField = new TextField(client.getUsername());
        TextField emailField = new TextField(client.getEmail());
        TextField addressField = new TextField(client.getAddress());
        TextField cityField = new TextField(client.getCity());
        TextField countryField = new TextField(client.getCountry());
        TextField postalCodeField = new TextField(client.getPostalCode());
        TextField nifField = new TextField(client.getNif());
        Text changePassTxt = new Text("Change Password");
        changePassTxt.setUnderline(true);
        changePassTxt.setOnMouseClicked(e -> changePassDialog(client));

        //Grid com info do client
        GridPane clientInfoGrid = new GridPane();
        clientInfoGrid.setPadding(new Insets(10));
        clientInfoGrid.setHgap(10);
        clientInfoGrid.setVgap(10);
        clientInfoGrid.setAlignment(Pos.CENTER);
        
        clientInfoGrid.add(nameLbl, 0, 0);
        clientInfoGrid.add(usernameLbl, 0, 1);
        clientInfoGrid.add(emailLbl, 0, 2);
        clientInfoGrid.add(addressLbl, 0, 3);
        clientInfoGrid.add(cityLbl, 0, 4);
        clientInfoGrid.add(countryLbl, 0, 5);
        clientInfoGrid.add(postalCodeLbl, 0, 6);
        clientInfoGrid.add(nifLbl, 0, 7);
        
        clientInfoGrid.add(nameField, 1, 0);
        clientInfoGrid.add(usernameField, 1, 1);
        clientInfoGrid.add(emailField, 1, 2);
        clientInfoGrid.add(addressField, 1, 3);
        clientInfoGrid.add(cityField, 1, 4);
        clientInfoGrid.add(countryField, 1, 5);
        clientInfoGrid.add(postalCodeField, 1, 6);
        clientInfoGrid.add(nifField, 1, 7);
        clientInfoGrid.add(changePassTxt, 1, 8);
        
        //Buttons
        Button saveBtn = new Button("Save Changes");
        saveBtn.setOnAction(e -> {
            client.setName(nameField.getText());
            String oldUser = client.getUsername();
            client.setUsername(usernameField.getText());
            client.setEmail(emailField.getText());
            client.setAddress(addressField.getText());
            client.setCity(cityField.getText());
            client.setCountry(countryField.getText());
            client.setPostalCode(postalCodeField.getText());
            client.setNif(nifField.getText());
            
            savedClients.changeSavedClient(client, oldUser);
        });
        
        //VBox principal
        VBox vb = new VBox();
        vb.setPadding(new Insets(10));
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(lbl, clientInfoGrid, saveBtn);
        
        //Posicionar elemento no borderpane
        setTop(new SecundaryTopBox());
        setCenter(vb);
        
    }
    
    /**
     * Janela para alterar a password
     * @param client 
     */
    private void changePassDialog(Client client){
        Label titleLabel = new Label("Change Password");
        titleLabel.setStyle("-fx-font-weight: bold;");
        
        //Grid
        GridPane passwordGrid = new GridPane();
        passwordGrid.setPadding(new Insets(10));
        passwordGrid.setHgap(10);
        passwordGrid.setVgap(10);
        passwordGrid.setAlignment(Pos.CENTER);
        
        //Set grid content
        Label oldPassLbl = new Label("Old Password");
        Label newPassLbl = new Label("New Password");
        Label confirmPassLbl = new Label("Confirm New Password");
        
        PasswordField oldPassPF = new PasswordField();
        PasswordField newPassPF = new PasswordField();
        PasswordField confirmPassPF = new PasswordField();
        
        Text errorTxt1 = new Text("");
        errorTxt1.setFill(Color.RED);
        Text errorTxt2 = new Text("");
        errorTxt2.setFill(Color.RED);
        
        passwordGrid.add(oldPassLbl, 0, 0);
        passwordGrid.add(newPassLbl, 0, 1);
        passwordGrid.add(confirmPassLbl, 0, 2);
        passwordGrid.add(oldPassPF, 1, 0);
        passwordGrid.add(newPassPF, 1, 1);
        passwordGrid.add(confirmPassPF, 1, 2);
        passwordGrid.add(errorTxt1, 2, 0);
        passwordGrid.add(errorTxt2, 2, 2);
        
        //Button
        Button changeBtn = new Button("Change Password");
        changeBtn.setOnAction(e -> {
            if(!oldPassPF.getText().equals(client.getPassword())){
                errorTxt1.setText("Wrong password");
                errorTxt2.setText("");
            } else if(!newPassPF.getText().equals(confirmPassPF.getText())){
                errorTxt2.setText("Confirmation don't match");
                errorTxt1.setText("");
            } else{
                client.setPassword(oldPassPF.getText(), newPassPF.getText(), confirmPassPF.getText());
                savedClients.changeSavedClient(client, client.getUsername());
                ((Stage)changeBtn.getScene().getWindow()).close();
            }
        });
        
        //VBox
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 15, 15, 15));
        vb.setSpacing(10);
        vb.getChildren().addAll(titleLabel, passwordGrid, changeBtn);
        
        //Create window
        Stage janela = new Stage();
        janela.setTitle("Change Password");
        janela.setResizable(false);
        janela.initStyle(StageStyle.UTILITY);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.setIconified(false);

        janela.centerOnScreen();
        janela.setScene(new Scene(vb, 450, 250));
        janela.show();
    }
}
