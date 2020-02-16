/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppDisplay;

import GraphView.VertexPlacementStrategy;
import GraphView.GraphPanel;
import GraphView.CircularSortedPlacementStrategy;
import BiologicalPark.*;
import Graph.Vertex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Painel principal, onde está a maioria da interface da aplicação.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class MainPanel extends BorderPane {
    
    private GestorPercurso gp;
    private GestorPercursoCareTaker caretaker = new GestorPercursoCareTaker();
    
    public MainPanel(Scene scene){ 
        gp = GestorPercurso.getInstance();
        
        //Boxes
        HBox hb1 = new HBox();
        hb1.setPadding(new Insets(15, 15, 15, 15));
        hb1.setSpacing(10);
        HBox hb2 = new HBox();
        hb2.setPadding(new Insets(15, 12, 15, 12));
        hb2.setSpacing(100);
        hb2.setAlignment(Pos.CENTER);
        HBox totalPriceBox = new HBox();
        totalPriceBox.setPadding(new Insets(15, 12, 15, 12));
        totalPriceBox.setSpacing(10);
        totalPriceBox.setAlignment(Pos.CENTER);
        HBox totalDistBox = new HBox();
        totalDistBox.setPadding(new Insets(15, 12, 15, 12));
        totalDistBox.setSpacing(10);
        totalDistBox.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15, 15, 15, 15));
        grid.setHgap(10);
        grid.setVgap(10);
        VBox vb = new VBox();
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(15, 12, 15, 12));
        
        //Info do percurso
        Label pathLbl = new Label("Path: ");
        pathLbl.setStyle("-fx-font-weight: bold;");
        Text pathTxt1 = new Text();
        Text pathTxt2 = new Text();
        if(gp.getPointsToPass().isEmpty()){
            pathTxt1.setText("º" + gp.getBiologicalPark().getOriginName());
        } else{
            pathTxt1.setText(gp.printPath());
        }
        Label totalCostLbl = new Label("Total Cost: ");
        totalCostLbl.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 16;");
        Label costLbl = new Label("0");
        costLbl.setStyle("-fx-font-size: 16;");
        Label totalDistLbl = new Label("Total Distance: ");
        totalDistLbl.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 16;");
        Label distLbl = new Label("0");
        distLbl.setStyle("-fx-font-size: 16;");
                
        //Top
        MenuBar menuBar = new MenuBar();
        Menu accountMenu = new Menu("Account");
        MenuItem profile = new MenuItem("Profile");
        profile.setOnAction(e -> scene.setRoot(new ProfilePanel(gp.getClient())));
        MenuItem menuTickets = new MenuItem("My Tickets");
        menuTickets.setOnAction(e -> scene.setRoot(new TicketsTablePane(gp.getClient()))); 
        accountMenu.getItems().addAll(profile, menuTickets);
        
        Menu optionsMenu = new Menu("Options");
        MenuItem menuUndo = new MenuItem("Restore last path");
        menuUndo.setOnAction(e -> {
            caretaker.restoreState(gp);
            String path = gp.printPath();
            String[] lines = path.split("\r\n|\r|\n");
            if(lines.length > 30){
                if(!hb2.getChildren().contains(pathTxt2)){
                    hb2.getChildren().add(pathTxt2);
                }
                String aux1 = "", aux2 = "";
                int count = 0;
                for(String s : lines){
                    if(count <= 23) aux1 += s + "\n";
                    else aux2 += s + "\n";
                    count++;
                }
                pathTxt1.setText(aux1);
                pathTxt2.setText(aux2);
            } else{
                hb2.getChildren().remove(pathTxt2);
                pathTxt1.setText(gp.printPath());
            }
            costLbl.setText("" + gp.getCost());
            distLbl.setText("" + gp.getDistance());
        });
        MenuItem menuLogout = new MenuItem("Sign Out");
        menuLogout.setOnAction(e -> {GridPane root = new GridPane();
            root.setPadding(new Insets(10, 15, 10, 15));
            root.setHgap(10);
            root.setVgap(10);
            scene.setRoot(new LoginPanel(scene));}
        );
        optionsMenu.getItems().addAll(menuUndo, menuLogout);
        
        Menu statisticsMenu = new Menu("Statistics");
        MenuItem mostVisitedPointsMI = new MenuItem("Most Visited Points");
        mostVisitedPointsMI.setOnAction(e -> scene.setRoot(new GraphicMostVisitedPointsPanel()));
        MenuItem ticketsSoldMI = new MenuItem("Percentages of tickets sold");
        ticketsSoldMI.setOnAction(e -> scene.setRoot(new GraphicTicketsPercentages()));
        
        statisticsMenu.getItems().addAll(mostVisitedPointsMI, ticketsSoldMI);
        
        menuBar.getMenus().addAll(optionsMenu, accountMenu, statisticsMenu);
        
        //Separators
        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        separator1.setValignment(VPos.CENTER);
        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.HORIZONTAL);
        separator2.setHalignment(HPos.CENTER);
        separator2.setValignment(VPos.BOTTOM);
        Separator separator3 = new Separator();
        separator3.setOrientation(Orientation.HORIZONTAL);
        separator3.setHalignment(HPos.CENTER);
        separator3.setValignment(VPos.BOTTOM);
        
        //CheckBoxes com pontos a visitar
        TilePane cbPointsToVisit = new TilePane();
        cbPointsToVisit.setVgap(12);
        cbPointsToVisit.setPrefWidth(250);
        cbPointsToVisit.setAlignment(Pos.BASELINE_LEFT);
        Label targetsLbl = new Label("Select points to visit:");
        targetsLbl.setStyle("-fx-font-weight: bold;");
        cbPointsToVisit.getChildren().add(targetsLbl);
        ArrayList<InterestPoint> pointsToVisit = new ArrayList<>();
        for(Vertex<InterestPoint> i : gp.getBiologicalPark().getInterestPoints()){
            if(i.element().getPointNumber() != 1) pointsToVisit.add(i.element());
        }
        Collections.sort(pointsToVisit);
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        pointsToVisit.stream().map((i) -> new CheckBox(i.toString())).map((cb) -> {
            checkBoxes.add(cb);
            cb.setStyle( "-fx-border-color: lightblue; "
                            + "-fx-font-size: 11;"
                            + "-fx-border-insets: -5; "
                            + "-fx-border-radius: 5;"
                            + "-fx-border-style: dotted;"
                            + "-fx-border-width: 2;"
            );
            return cb;
        }).forEachOrdered((cb) -> {
            cbPointsToVisit.getChildren().add(cb);
        }); 
        
        //ComboBox tipo de rota
        ComboBox<String> routeTypeCB = new ComboBox<>(FXCollections.observableArrayList("Walking", "Cycling"));
        Label selectRouteTypeLbl = new Label("Route Type:");
        selectRouteTypeLbl.setStyle("-fx-font-weight: bold;");
        HBox hbRouteTypeSelect = new HBox(10);
        hbRouteTypeSelect.getChildren().addAll(selectRouteTypeLbl, routeTypeCB);
        
        //ComboBox critério a minimizar
        ComboBox<String> minimizedCriterionCB = new ComboBox<>(FXCollections.observableArrayList("Cost", "Distance"));
        Label criterionLbl = new Label("Criterion to be minimized:");
        criterionLbl.setStyle("-fx-font-weight: bold;");
        HBox hbMinimizedCriterion = new HBox(10);
        hbMinimizedCriterion.getChildren().addAll(criterionLbl, minimizedCriterionCB);
        
        //ComboBox para selecionar um ponto a visitar
        List<String> interestPoints = new ArrayList<>();
        for(Vertex<InterestPoint> i : gp.getBiologicalPark().getInterestPoints()){
            if(i.element().getPointNumber() != 1) interestPoints.add(i.element().toString());
        }
                                //Collections.sort(interestPoints);
        ComboBox<String> pointToVisitCB = new ComboBox<>(FXCollections.observableArrayList(interestPoints));
        Label selectEquip = new Label("Point To Visit:");
        selectEquip.setStyle("-fx-font-weight: bold;");
        HBox hbInterestPointSelect = new HBox(10);
        hbInterestPointSelect.getChildren().addAll(selectEquip, pointToVisitCB);
        
        //Buttons
        Button btnCalc1 = new Button("Calculate");
        btnCalc1.setOnAction((ActionEvent e) -> {
            if(routeTypeCB.getSelectionModel().getSelectedItem() == null){
                new MessageDialog("Please select a route type!");
                return;
            }else if(!routeTypeCB.getSelectionModel().getSelectedItem().equalsIgnoreCase(gp.getTypeRoute().name())){
                gp.alterTypeRoute();
            }
            if(minimizedCriterionCB.getSelectionModel().getSelectedItem() == null){
                new MessageDialog("Please select a criterion to minimize!");
                return;
            }else if(!minimizedCriterionCB.getSelectionModel().getSelectedItem().equalsIgnoreCase(gp.getMinimizedStrategy().toString())){
                gp.alterMinimizedStrategy();
            }
            List<InterestPoint> pointsToPass = new ArrayList<>();
            for(CheckBox i : checkBoxes){
                if(i.isSelected()){
                    pointsToPass.add(gp.getBiologicalPark().checkInterestPoint(i.getText().split("\\(")[0]).element());
                }
            }
            if(pointsToPass.isEmpty()){
                new MessageDialog("Please select at least one point!");
                return;
            }
            caretaker.saveState(gp);
            gp.setPointsToVisit(pointsToPass);
            gp.calcBest3Path();
            String path = gp.printPath();
            String[] lines = path.split("\r\n|\r|\n");
            if(lines.length > 30){
                if(!hb2.getChildren().contains(pathTxt2)){
                    hb2.getChildren().add(pathTxt2);
                }
                String aux1 = "", aux2 = "";
                int count = 0;
                for(String s : lines){
                    if(!s.isEmpty()){
                        if(count <= 23) aux1 += s + "\n";
                        else aux2 += s + "\n";
                        count++;
                    }
                }
                pathTxt1.setText(aux1);
                pathTxt2.setText(aux2);
            } else{
                hb2.getChildren().remove(pathTxt2);
                pathTxt1.setText(gp.printPath());
            }
            costLbl.setText("" + gp.getCost());
            distLbl.setText("" + gp.getDistance());
        });
        Button btnCalc2 = new Button("Calculate");
        btnCalc2.setOnAction(e -> {
            if(routeTypeCB.getSelectionModel().getSelectedItem() == null){
                new MessageDialog("Please select a route type!");
                return;
            }else if(!routeTypeCB.getSelectionModel().getSelectedItem().equalsIgnoreCase(gp.getTypeRoute().name())){
                gp.alterTypeRoute();
            }
            if(minimizedCriterionCB.getSelectionModel().getSelectedItem() == null){
                new MessageDialog("Please select a criterion to minimize!");
                return;
            }else if(!minimizedCriterionCB.getSelectionModel().getSelectedItem().equalsIgnoreCase(gp.getMinimizedStrategy().toString())){
                gp.alterMinimizedStrategy();
            }
            if(pointToVisitCB.getSelectionModel().getSelectedItem() == null){
                new MessageDialog("Please select one point!");
                return;
            }
            List<InterestPoint> pointsToPass = new ArrayList<>();
            caretaker.saveState(gp);
            pointsToPass.add(gp.getBiologicalPark().checkInterestPoint(pointToVisitCB.getSelectionModel().getSelectedItem().split("\\(")[0]).element());
            gp.setPointsToVisit(pointsToPass);
            gp.calcBestPath(pointToVisitCB.getSelectionModel().getSelectedItem().split("\\(")[0]);
            String path = gp.printPath();
            String[] lines = path.split("\r\n|\r|\n");
            if(lines.length > 30){
                if(!hb2.getChildren().contains(pathTxt2)){
                    hb2.getChildren().add(pathTxt2);
                }
                String aux1 = "", aux2 = "";
                int count = 0;
                for(String s : lines){
                    if(count <= 23) aux1 += s + "\n";
                    else aux2 += s + "\n";
                    count++;
                }
                pathTxt1.setText(aux1);
                pathTxt2.setText(aux2);
            } else{
                hb2.getChildren().remove(pathTxt2);
                pathTxt1.setText(gp.printPath());
            }
            costLbl.setText("" + gp.getCost());
            distLbl.setText("" + gp.getDistance());
        });
        Image iconTicket = new Image(getClass().getResourceAsStream("icons/ticket.png"), 70, 70, false, false);
        Button btnTicket = new Button("Buy Ticket", new ImageView(iconTicket));
        btnTicket.setOnAction(e -> {
            if(gp.getPointsToPass().size() > 0){
                new BuyTicketDialog(scene);
            } else{
                new MessageDialog("You need to insert at least one interest point to visit to buy a ticket");
            }
        });
        Image iconMap = new Image(getClass().getResourceAsStream("icons/map.png"), 100, 100, false, false);
        Button btnMap = new Button("", new ImageView(iconMap));
        btnMap.setOnAction(e -> openMap());
        btnMap.setAlignment(Pos.CENTER);
        
        //Posicionar elementos na VBox 1
        grid.add(hbRouteTypeSelect, 0, 0);
        grid.add(hbMinimizedCriterion, 0, 1);
        grid.add(cbPointsToVisit, 0 , 2);
        grid.add(btnCalc1, 0, 3);
        grid.add(separator3, 0, 4);
        grid.add(hbInterestPointSelect, 0, 5);
        grid.add(btnCalc2, 0, 6);
        grid.add(btnTicket, 0, 15);
        
        //Posicionar elementos nas HBoxes
        hb1.getChildren().addAll(grid, separator1);
        hb2.getChildren().addAll(pathTxt1);
        
        //Posicionar elementos na VBox 2
        Label mapViewLbl = new Label("See map");
        mapViewLbl.setStyle("-fx-font-weight: bold;");
        vb.getChildren().addAll(btnMap, mapViewLbl, new Text(""), separator2, pathLbl, hb2);
        
        //Posicionar elementos na totalPriceBox e totalDistBox
        totalPriceBox.getChildren().addAll(totalCostLbl, costLbl);
        totalDistBox.getChildren().addAll(totalDistLbl, distLbl);
        HBox bottomBox = new HBox();
        bottomBox.setPadding(new Insets(15, 15, 15, 15));
        bottomBox.setSpacing(100);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(totalPriceBox, totalDistBox);
        
        //Posicionar elementos no borderPane
        this.setTop(menuBar);
        this.setLeft(hb1);
        this.setCenter(vb);
        this.setBottom(bottomBox);
        scene.setRoot(this);
    }
    
    private void openMap(){
        VertexPlacementStrategy strategy = new CircularSortedPlacementStrategy();
        //VertexPlacementStrategy strategy = new RandomPlacementStrategy();
        
        GraphPanel<InterestPoint, Way> graphView = new GraphPanel<>(gp.getBiologicalPark().graph, strategy);
        
        Scene scene = new Scene(graphView, 700, 700);
        
        graphView.plotGraph();
        
        InterestPoint pre = null;
        for(InterestPoint i : gp.getPointsToPass()){
            graphView.setVertexColor(gp.getBiologicalPark().checkInterestPoint(i.getName()), Color.GOLD, Color.BROWN);
            /*if(pre != null){
                Way w = gp.getBiologicalPark().getDirectedWayBetween(pre, i);
                graphView.setEdgeColor(gp.getBiologicalPark().checkWay(w.getName()), Color.CYAN, 0.8);
            }
            pre = i;*/
        }
        //after creating, shows how to change some elements
        //graphView.setEdgeColor(gp.getBiologicalPark().checkWay("ponteLilas"), Color.CYAN, 0.8);
        
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Biological Park Map");
        stage.setScene(scene);
        stage.show();
    }
    
}
