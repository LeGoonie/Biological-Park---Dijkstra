
package AppDisplay;

import Statistics.Statistics;
import Statistics.StatisticsDAO;
import Statistics.StatisticsDAOSerialization;
import java.util.Map.Entry;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Painel com gráfico de estatisticas dos pontos mais visitados.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class GraphicMostVisitedPointsPanel extends BorderPane{
    
    public GraphicMostVisitedPointsPanel(){
        //Top
        this.setTop(new SecundaryTopBox());
        
        //Bar graphic
        StatisticsDAO statsDao = Statistics.createStatisticsDAO();
        Statistics stats = Statistics.getInstance();
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart bc = new BarChart(xAxis,yAxis);
        bc.setTitle("Ten Most Visited Points");
        xAxis.setLabel("Interest Point");
        yAxis.setLabel("Nº Of Times Visited");
        
        Series<String, Integer> series = new Series<>();
        series.setName("Interest Point");
        
        for(Entry<String, Integer> dataItem : stats.calcMostVisited10Points().entrySet()){
            series.getData().add(new XYChart.Data<>(dataItem.getKey(), dataItem.getValue()));
        }
        
        bc.getData().add(series);
        
        this.setCenter(bc);
    }
}
