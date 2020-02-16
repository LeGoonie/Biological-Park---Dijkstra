/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppDisplay;

import BiologicalPark.Client;
import Documents.Ticket;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Painel com uma tabela com todos os ticket comprados pelo utilizador.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class TicketsTablePane extends BorderPane{
    public TicketsTablePane(Client client){
        //Table
        TableView tableView = new TableView();
        tableView.setMinWidth(600);
        tableView.setMinHeight(750);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn invoiceClm = new TableColumn("Invoice");
        invoiceClm.setMaxWidth(1000);
        TableColumn referenceClm = new TableColumn("Ticket reference");
        invoiceClm.setMaxWidth(1000);
        TableColumn dateClm = new TableColumn("Purchase date");
        invoiceClm.setMaxWidth(1000);
        
        invoiceClm.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        referenceClm.setCellValueFactory(new PropertyValueFactory<>("reference"));
        dateClm.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        
        tableView.getColumns().addAll(invoiceClm, referenceClm, dateClm);
        
        List<Ticket> listTickets = client.getTickets();
        ObservableList<Ticket> observableListTickets = FXCollections.observableArrayList(listTickets);
        tableView.setItems(observableListTickets);
        
        //SplitPane
        Label titleLbl = new Label("My Tickets");
        titleLbl.setStyle( "-fx-font-weight: bold;" + "-fx-font-size: 14;" );
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(15, 15, 15, 15));
        vb.setSpacing(10);
        vb.getChildren().addAll(titleLbl, tableView);
        
        setTop(new SecundaryTopBox());
        setCenter(vb);
    }
}
