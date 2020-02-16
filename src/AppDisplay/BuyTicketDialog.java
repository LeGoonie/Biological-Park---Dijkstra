/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppDisplay;

import BiologicalPark.ClientDAOJson;
import BiologicalPark.GestorPercurso;
import BiologicalPark.InterestPoint;
import Documents.Invoice;
import Statistics.Statistics;
import Documents.Ticket;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Janela de dialogo para compra de bilhetes.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class BuyTicketDialog extends Stage {
    private int quantity;
    
    public BuyTicketDialog(Scene scene){
        GestorPercurso gp = GestorPercurso.getInstance();
        quantity = 1;
        //Labels
        Label lbl = new Label("Confirm your informations para a fatura");
        lbl.setStyle( "-fx-font-weight: bold;" + "-fx-font-size: 14;" );
        Label nameLbl = new Label("Name");
        Label addressLbl = new Label("Address *");
        Label cityLbl = new Label("City *");
        Label countryLbl = new Label("Country *");
        Label postalCodeLbl = new Label("PostalCode *");
        Label nifLbl = new Label("NIF");
        Label ticketsQuantityLbl = new Label("Number of tickets ");
        
        //Info TextBoxes
        TextField nameField = new TextField(gp.getClient().getName());
        TextField addressField = new TextField(gp.getClient().getAddress());
        TextField cityField = new TextField(gp.getClient().getCity());
        TextField countryField = new TextField(gp.getClient().getCountry());
        TextField postalCodeField = new TextField(gp.getClient().getPostalCode());
        TextField nifField = new TextField(gp.getClient().getNif());
        
        //ComboBox Quantity
        ComboBox<Integer> quantityCB = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        quantityCB.getSelectionModel().select(0);

        //Grid com info do client
        GridPane clientInfoGrid = new GridPane();
        clientInfoGrid.setPadding(new Insets(10));
        clientInfoGrid.setHgap(10);
        clientInfoGrid.setVgap(10);
        clientInfoGrid.setAlignment(Pos.CENTER);
        clientInfoGrid.add(nameLbl, 0, 0);
        clientInfoGrid.add(addressLbl, 0, 1);
        clientInfoGrid.add(cityLbl, 0, 2);
        clientInfoGrid.add(countryLbl, 0, 3);
        clientInfoGrid.add(postalCodeLbl, 0, 4);
        clientInfoGrid.add(nifLbl, 0, 5);
        clientInfoGrid.add(ticketsQuantityLbl, 0, 6);
        clientInfoGrid.add(nameField, 1, 0);
        clientInfoGrid.add(addressField, 1, 1);
        clientInfoGrid.add(cityField, 1, 2);
        clientInfoGrid.add(countryField, 1, 3);
        clientInfoGrid.add(postalCodeField, 1, 4);
        clientInfoGrid.add(nifField, 1, 5);
        clientInfoGrid.add(quantityCB, 1, 6);
        
        Button confirmBtn = new Button("Confirm");
        
        //VBox principal
        VBox vb = new VBox();
        vb.setPadding(new Insets(10));
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(lbl, clientInfoGrid, confirmBtn);
        
        //Show window
        setResizable(false);
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setIconified(false);
        centerOnScreen();
        setTitle("Buy ticket");
        Scene dialogScene = new Scene(vb, 600, 600);
        setScene(dialogScene);
        show();
        
        //ação do butão de confirmação
        confirmBtn.setOnAction(e -> {
            if(addressField.getText().isEmpty() || cityField.getText().isEmpty() || countryField.getText().isEmpty() || postalCodeField.getText().isEmpty()){
                new MessageDialog("Please fill in the required fields");
            }else{
                gp.getClient().setName(nameField.getText());
                gp.getClient().setAddress(addressField.getText());
                gp.getClient().setCity(cityField.getText());
                gp.getClient().setCountry(countryField.getText());
                gp.getClient().setPostalCode(postalCodeField.getText());
                gp.getClient().setNif(nifField.getText());
                quantity = quantityCB.getSelectionModel().getSelectedItem();
                paymentMethod(gp, dialogScene, scene);
            }
        });
        
    }
    
    private void paymentMethod(GestorPercurso gp, Scene dialogScene, Scene scene){
        //Labels
        Label paymentLbl = new Label("Select the payment method:");
        paymentLbl.setStyle( "-fx-font-weight: bold;" + "-fx-font-size: 14;" );

        //Criar butões
        Image iconVisa = new Image(getClass().getResourceAsStream("icons/visa.png"), 100, 80, false, false);
        Button btnVisa = new Button("", new ImageView(iconVisa));

        Image iconMastercard = new Image(getClass().getResourceAsStream("icons/mastercard.png"), 100, 80, false, false);
        Button btnMastercard = new Button("", new ImageView(iconMastercard));

        Image iconMultibanco = new Image(getClass().getResourceAsStream("icons/multibanco.png"), 100, 80, false, false);
        Button btnMultibanco = new Button("", new ImageView(iconMultibanco));

        Image iconPaypal = new Image(getClass().getResourceAsStream("icons/paypal.png"), 100, 80, false, false);
        Button btnPaypal = new Button("", new ImageView(iconPaypal));
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> ((Stage) cancelBtn.getScene().getWindow()).close());
        
        //Center grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15,15,15,15));
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        grid.add(btnVisa, 0, 0);
        grid.add(btnMastercard, 0, 1);
        grid.add(btnMultibanco, 1, 0);
        grid.add(btnPaypal, 1, 1);
        
        //Painel principal
        VBox paymentPane = new VBox();
        paymentPane.setPadding(new Insets(10));
        paymentPane.setSpacing(10);
        paymentPane.setAlignment(Pos.CENTER);
        paymentPane.getChildren().add(paymentLbl);
        paymentPane.getChildren().add(grid);
        paymentPane.getChildren().add(cancelBtn);
        
        //função dos butões
        btnVisa.setOnAction(e -> paymentConfirmation(gp, dialogScene, scene));
        btnMastercard.setOnAction(e -> paymentConfirmation(gp, dialogScene, scene));
        btnMultibanco.setOnAction(e -> paymentConfirmation(gp, dialogScene, scene));
        btnPaypal.setOnAction(e -> paymentConfirmation(gp, dialogScene, scene));
        
        dialogScene.setRoot(paymentPane);
    }
    
    private void paymentConfirmation(GestorPercurso gp, Scene dialogScene, Scene scene){
        //Menssagem
        Label lbl1 = new Label("O valor a pagar será de " + gp.getCost()*quantity + "€\npara o seguinte percurso:");
        String targets = "";
        int count = 0;
        for(InterestPoint ip : gp.getPointsToPass()){
            if(count != 0) targets += " - ";
            if(count%5 == 0 && count != 0) targets += "\n- ";
            targets += ip.toString();
            count++;
        }
        Label lbl2 = new Label(targets);
        
        //Confirmation buttons
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnAction(e -> {
            try {
                File dir = new File("purchases/" + (Invoice.lastInvoiceNumb() + 1));
                dir.mkdir();
                List<Ticket> tickets = new ArrayList<>();
                for(int i = 0; i < quantity; i++){
                    boolean isBikePath = false;
                    if(gp.getTypeRoute() == GestorPercurso.TYPE_ROUTE.CYCLING) isBikePath = true;
                    Ticket t = new Ticket(gp.getPointsToPass(), i+1, quantity, gp.getClient().getName(), isBikePath);
                    gp.getClient().buyTicket(t);
                    tickets.add(t);
                }
                Invoice invoice = new Invoice(tickets, gp.getCost(), gp.getClient());
                Statistics.createStatisticsDAO().addInvoice(invoice);
                ClientDAOJson savedClients = new ClientDAOJson();
                savedClients.changeSavedClient(gp.getClient(), gp.getClient().getUsername());
            } catch (IOException | WriterException ex) {
                Logger.getLogger(BuyTicketDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((Stage) confirmBtn.getScene().getWindow()).close();
        });
        Button cancelBtn = new Button("Cancel"); 
        cancelBtn.setOnAction(e -> ((Stage) cancelBtn.getScene().getWindow()).close());
        HBox buttonsBox = new HBox();
        buttonsBox.setPadding(new Insets(15, 15, 15, 15));
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().add(confirmBtn);
        buttonsBox.getChildren().add(cancelBtn);
        
        //VBox principal
        VBox confirmationPane = new VBox();
        confirmationPane.setPadding(new Insets(10));
        confirmationPane.setSpacing(10);
        confirmationPane.setAlignment(Pos.CENTER);
        confirmationPane.getChildren().add(lbl1);
        confirmationPane.getChildren().add(lbl2);
        confirmationPane.getChildren().add(buttonsBox);
        
        dialogScene.setRoot(confirmationPane);
    }
}
