/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BiologicalPark;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class Percurso {
    protected int cost,distance;
    protected List<InterestPoint> targets, pathPoints;
    protected List<Way> pathWay;

    public Percurso(){
        this(new ArrayList<InterestPoint>());
    }
    
    public Percurso(List<InterestPoint> targets) {
        this.targets = targets;
        this.pathPoints = new ArrayList();
        this.pathWay = new ArrayList();
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void incrementCost(int adicionalCost){
        this.cost += adicionalCost;
    }
    
    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    public void incrementDistance(int adicionalDistance){
        this.distance += adicionalDistance;
    }

    public List<InterestPoint> getTargets(){
        return targets;
    }
    
    public void setTargets(List<InterestPoint> targets) {
        this.targets = targets;
    }
    
    public void addTarget(InterestPoint target){
        if(target != null)
            targets.add(target);
    }
    
    public void setOneTarget(InterestPoint target){
        List<InterestPoint> list = new ArrayList<>();
        list.add(target);
        targets = list;
    }

    public List<InterestPoint> getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(List<InterestPoint> pathPoints) {
        this.pathPoints = pathPoints;
    }

    public void addPathPoints(List<InterestPoint> pathPoints){
        this.pathPoints.addAll(pathPoints);
    }
    
    public void addPathPoint(InterestPoint pathPoint){
        pathPoints.add(pathPoint);
    }
    
    public List<Way> getPathWays() {
        return pathWay;
    }
    
    public void addPathWays(List<Way> pathWay){
        this.pathWay.addAll(pathWay);
    }
    
    /**
     * Reinicia o percurso (custo, distancia, pontos e caminhos)
     */
    public void reset(){
        cost = 0;
        distance = 0;
        pathPoints = new ArrayList();
        pathWay = new ArrayList();
    }
   
}
