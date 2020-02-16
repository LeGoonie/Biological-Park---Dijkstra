/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import BiologicalPark.InterestPoint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Complementa a classe "Statistics" de forma a esta ter acesso não aos tickets em si, 
 * mas sim apenas á informações de que esta realmente precisa.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class StatsRow implements Serializable{

        protected List<InterestPoint> interestPoints;
        protected int cost;
        protected boolean byBike;

        public StatsRow(List<InterestPoint> interestPoints, int cost, boolean byBike) {
            this.interestPoints = interestPoints;
            this.cost = cost;
            this.byBike = byBike;
        }
        
        public StatsRow(int cost, boolean byBike) {
            this.interestPoints = new ArrayList<>();
            this.cost = cost;
            this.byBike = byBike;
        }
        
        public void setInterestPoints(List<InterestPoint> points){
            if(!points.isEmpty())
                interestPoints = points;
        }
        
        public List<InterestPoint> getPoints(){
            return interestPoints;
        }
    }
