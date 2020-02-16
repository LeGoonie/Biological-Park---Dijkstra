
package Statistics;

import BiologicalPark.InterestPoint;
import Documents.Invoice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Implementa a interface "Statistics" e guarda a informação das estatísticas numa base de dados SQLite.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class StatisticsDAOSQLite implements StatisticsDAO {
    private Statistics statistics;
    public static final String databaseName = "rows";
    private Connection con;
    
    public StatisticsDAOSQLite(){
        statistics = Statistics.getInstance();
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:jar files/rows.sqlite");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(StatisticsDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadData();
    }
    
    private boolean isDbConnected(){
        try {
            return !con.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    @Override
    public void addInvoice(Invoice invoice) {
        if(!statistics.getRows().contains(invoice)){
            statistics.addInvoice(invoice);
            saveData();
        }
    }

    @Override
    public void saveData() {
        
        try {
            for(StatsRow s : statistics.getRows()){
                ResultSet maxId = con.createStatement().executeQuery("SELECT MAX(id) FROM StatsRow");
                int id = maxId.getInt("MAX(id)");
                PreparedStatement ps = con.prepareStatement("INSERT INTO StatsRow VALUES (" + (++id) + ", ?, ?)");
                ps.setInt(1, s.cost);
                if(s.byBike)
                    ps.setString(2, "true");
                else
                    ps.setString(2, "false");
                ps.executeUpdate();
                ResultSet maxId1 = con.createStatement().executeQuery("SELECT MAX(id) FROM InterestPoint");
                int id1 = maxId1.getInt("MAX(id)");
                for(InterestPoint i : s.getPoints()){
                    PreparedStatement ps1 = con.prepareStatement("INSERT INTO InterestPoint VALUES (" + (++id1) + ", ?, ?, " + id + ")");
                    ps1.setInt(1, i.getPointNumber());
                    ps1.setString(2, i.getName());
                    ps1.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void loadData() {
        Statement stmt;
        try {
            stmt = con.createStatement();
            ResultSet rows = stmt.executeQuery("SELECT * FROM StatsRow");
            List<StatsRow> statsRows = new ArrayList<>();
            List<Integer> ids = new ArrayList<>();
            while(rows.next()){
                String byBike = rows.getString("byBike");
                if(byBike.equals("false"))
                    statsRows.add(new StatsRow(rows.getInt("cost"), false));
                else
                    statsRows.add(new StatsRow(rows.getInt("cost"), true));
                ids.add(rows.getInt("id"));
            }
            for(Integer i : ids){
                ResultSet points = stmt.executeQuery("SELECT * FROM InterestPoint WHERE idStatsRow = " + i);
                List<InterestPoint> interestPoints = new ArrayList<>();
                while(points.next()){
                    interestPoints.add(new InterestPoint(points.getInt("pointNumber"), points.getString("name")));
                }
                for(StatsRow s : statsRows){
                    s.setInterestPoints(interestPoints);
                }
            }
            
            statistics.setRows(statsRows);
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
