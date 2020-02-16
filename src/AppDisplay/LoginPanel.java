/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppDisplay;

import BiologicalPark.Client;
import BiologicalPark.ClientDAOJson;
import BiologicalPark.GestorPercurso;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Painel de login.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class LoginPanel extends GridPane {

    private ClientDAOJson savedClients;
    private static String MAP;

    public LoginPanel(Scene scene) {
        setPadding(new Insets(10, 15, 10, 15));
        setHgap(10);
        setVgap(10);
        GestorPercurso gp = GestorPercurso.getInstance();
        savedClients = new ClientDAOJson();

        StackPane sp = new StackPane();
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-weight: bold;");
        Label name = new Label("Username ");
        Label code = new Label("Code ");
        TextField nameField = new TextField();
        PasswordField codeField = new PasswordField();
        Text errorText1 = new Text();
        errorText1.setFill(Color.RED);
        Text errorText2 = new Text();
        errorText2.setFill(Color.RED);
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction((ActionEvent event) -> {
            Properties prop = new Properties();
            InputStream input = null;
            String mapa0 = "", mapa1= "", mapa2= "", mapa3= "";
            try {
                input = new FileInputStream("propertiesFile.properties");

                prop.load(input);

                mapa0 = prop.getProperty("mapa0.dat");
                mapa1 = prop.getProperty("mapa1.dat");
                mapa2 = prop.getProperty("mapa2.dat");
                mapa3 = prop.getProperty("mapa3.dat");

            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (mapa0.equals("true")) {
                MAP = "mapa0.dat";
            } else if (mapa1.equals("true")) {
                MAP = "mapa1.dat";
            }else if (mapa2.equals("true")) {
                MAP = "mapa2.dat";
            }else if (mapa3.equals("true")) {
                MAP = "mapa3.dat";
            } else {
                throw new IllegalArgumentException("map has not been loaded");
            }

            Client client = savedClients.loadClient(nameField.getText());
            if (client == null || !client.getUsername().equals(nameField.getText())) {
                errorText1.setText("username is invalid");
                errorText2.setText("");
            } else if (client.getPassword().equals(codeField.getText())) {
                gp.setClient(client);
                try {
                    gp.readFile(MAP);
                } catch (IOException ex) {
                    Logger.getLogger(LoginPanelView.class.getName()).log(Level.SEVERE, null, ex);
                }
                new MainPanel(scene);
            } else {
                errorText1.setText("");
                errorText2.setText("code is invalid");
            }
        });

        Text registerText = new Text("Register user on console");
        Font serif = Font.font("Serif", 12);
        registerText.setFont(serif);
        registerText.setFill(Color.BLUE);
        registerText.setUnderline(true);
        registerText.setOnMouseClicked(e -> new RegisterDialog(savedClients));

        //Posicionar elementos
        add(titleLabel, 0, 0);
        add(name, 0, 1);
        add(code, 0, 2);
        add(loginBtn, 0, 4);
        add(nameField, 1, 1);
        add(codeField, 1, 2);
        add(registerText, 1, 4);
        add(errorText1, 2, 1);
        add(errorText2, 2, 2);
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);
    }
}
