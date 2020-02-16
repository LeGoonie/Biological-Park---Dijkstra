package Statistics;

import AppDisplay.MainPanel;
import BiologicalPark.InterestPoint;
import Documents.Invoice;
import Documents.Ticket;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tem uma lista de todos os tickets vendidos e executa o calculo das estatísticas
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public final class Statistics implements Serializable {

    private static Statistics instance = new Statistics();
    private static List<StatsRow> statsRows;

    private Statistics() {
        statsRows = new ArrayList<>();
    }

    public static Statistics getInstance() {
        return instance;
    }

    protected List<StatsRow> getRows() {
        return statsRows;
    }

    protected void setRows(List<StatsRow> statsRows) {
        if(statsRows == null) return;
        this.statsRows = new ArrayList<>();
        for (StatsRow r : statsRows) {
            this.statsRows.add(r);
        }
    }

    public void addInvoice(Invoice invoice) {
        if (invoice != null) {
            for(Ticket t : invoice.getTickets())
                statsRows.add(new StatsRow(t.getPointsToVisit(), invoice.getTicketsCost(), t.isBikePath()));
        }
    }

    public HashMap<String, Integer> calcMostVisited10Points() {
        HashMap<String, Integer> allPoints = new HashMap<>();
        for (StatsRow r : statsRows) {
            for (InterestPoint ip : r.interestPoints) {
                if (ip.getPointNumber() != 1) {
                    if (allPoints.containsKey(ip.toString())) {
                        allPoints.put(ip.toString(), allPoints.get(ip.toString()) + 1);
                    } else {
                        allPoints.put(ip.toString(), 1);
                    }
                }
            }
        }
        int count = 0;
        HashMap<String, Integer> best10 = new HashMap<>();
        String smallerValuePoint = null;
        for (Entry<String, Integer> i : allPoints.entrySet()) {
            if (count == 0) {
                smallerValuePoint = i.getKey();
            }
            if (count <= 10) {
                if (i.getValue() < allPoints.get(smallerValuePoint)) {
                    smallerValuePoint = i.getKey();
                }
                best10.put(i.getKey(), i.getValue());
            } else if (i.getValue() > allPoints.get(smallerValuePoint)) {
                best10.remove(smallerValuePoint);
                best10.put(i.getKey(), i.getValue());
            }
            count++;
        }
        return best10;
    }

    public int avgTicketsPrice() {
        int totalPrice = 0;
        for (StatsRow r : statsRows) {
            totalPrice += r.cost;
        }
        if (statsRows.size() > 0) {
            return totalPrice / statsRows.size();
        } else {
            return 0;
        }
    }

    public double walkTicketsPercentage() {
        int totalWalkTickets = 0;
        for (StatsRow r : statsRows) {
            if (!r.byBike) {
                totalWalkTickets += 1;
            }
        }
        if (statsRows.size() > 0) {
            return totalWalkTickets * 100 / statsRows.size();
        } else {
            return 0;
        }
    }

    public double bikeTicketsPercentage() {
        int totalBikeTickets = 0;
        for (StatsRow r : statsRows) {
            if (r.byBike) {
                totalBikeTickets += 1;
            }
        }
        if (statsRows.size() > 0) {
            return totalBikeTickets * 100 / statsRows.size();
        } else {
            return 0;
        }
    }

    public void resetStats() {
        instance = new Statistics();
    }

    public static StatisticsDAO createStatisticsDAO() {
        Properties prop = new Properties();
        InputStream input = null;
        String serializationDao = "", sqlDao = "";
        try {
            input = new FileInputStream("propertiesFile.properties");

            prop.load(input);

            serializationDao = prop.getProperty("Serialization");
            sqlDao = prop.getProperty("sqLite");

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
        if (serializationDao.equals("true")) {
            return new StatisticsDAOSerialization();
        } else if (sqlDao.equals("true")) {
            return new StatisticsDAOSQLite();
        } else {
            throw new IllegalArgumentException("Statistics have not been loaded");
        }

    }

    
}
