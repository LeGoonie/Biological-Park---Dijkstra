/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import Documents.Invoice;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementa a interface "Statistics" e guarda a informação das estatísticas através de serialização.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class StatisticsDAOSerialization implements StatisticsDAO {

    private Statistics statistics;
    private final static String filename = "statistics.bin";

    public StatisticsDAOSerialization() {
        statistics = Statistics.getInstance();
        loadData();
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
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fileOut);
            os.writeObject(statistics.getRows());
            os.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void loadData() {
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fileIn);
            List<StatsRow> list = (List<StatsRow>) is.readObject();
            statistics.setRows(list);
            is.close();
            fileIn.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Não existem estatisticas guardadas");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void resetData() {
        statistics.resetStats();
        saveData();
    }

    
    
}
