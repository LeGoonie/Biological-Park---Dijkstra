/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppDisplay;

import Statistics.Statistics;
import Statistics.StatisticsDAO;
import Statistics.StatisticsDAOSerialization;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Painel com gráfico de estatisticas da percentagem de bilhetes a pé e de bicicleta.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class GraphicTicketsPercentages extends BorderPane {

    public GraphicTicketsPercentages() {
        //Top
        this.setTop(new SecundaryTopBox());

        //pie chart graphic
        StatisticsDAO statsDao = Statistics.createStatisticsDAO();
        Statistics stats = Statistics.getInstance();
        double walkTicketsPercentage = stats.walkTicketsPercentage();
        double bikeTicketsPercentage = stats.bikeTicketsPercentage();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("On foot", walkTicketsPercentage),
                new PieChart.Data("By bike", bikeTicketsPercentage));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Percentage of tickets sold by route type");

        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 25 arial;");
        Tooltip container = new Tooltip();
        container.setGraphic(caption);

        for (PieChart.Data data : chart.getData()) {
            data.getNode().setOnMouseEntered(e -> {
                if (container.isShowing()) {
                    container.hide();
                }
                caption.setText(String.valueOf(data.getPieValue()) + "%");
                container.show((Stage) chart.getScene().getWindow(), e.getScreenX(), e.getScreenY());
            });
        }
        this.setCenter(chart);

        //Price average
        StackPane sp = new StackPane();
        Label avgPriceLbl = new Label("Average price of tickets sold: " + stats.avgTicketsPrice());
        avgPriceLbl.setTextFill(Color.DARKORANGE);
        avgPriceLbl.setStyle("-fx-font: 25 arial;" + "-fx-font-weight: bold;");
        avgPriceLbl.setAlignment(Pos.CENTER);
        sp.getChildren().add(avgPriceLbl);
        sp.setAlignment(Pos.CENTER);
        sp.setPadding(new Insets(15, 15, 15, 15));

        this.setBottom(sp);
    }
}
