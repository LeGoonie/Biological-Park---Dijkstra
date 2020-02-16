/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import Documents.Invoice;

/**
 * Interface do padrão Strategy para o DAO das estatísticas
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public interface StatisticsDAO {
    public void addInvoice(Invoice invoice);
    public void saveData();
    public void loadData();
    public void resetData();
}
