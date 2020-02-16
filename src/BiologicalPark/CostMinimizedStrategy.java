/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BiologicalPark;

import Graph.Edge;
import Graph.Vertex;
import java.util.HashMap;

/**
 * Implementa a classe MinimizedStrategy e executa as funções para o critério "Cost".
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class CostMinimizedStrategy implements MinimizedStrategy {

    @Override
    public void applyCriterion(Vertex<InterestPoint> target, HashMap<Vertex<InterestPoint>, Double> costsOrDistances, Percurso percurso) {
        int cost = 0;
        cost += costsOrDistances.get(target);
        percurso.incrementCost(cost);
    }

    @Override
    public void applyInverseCriterion(Percurso percurso) {
        for (Way w : percurso.getPathWays()) {
            percurso.incrementDistance(w.getDistance());
        }
    }

    @Override
    public int getCriterionFromEdge(Edge< Way,InterestPoint> edge) {
        return edge.element().getCost();
    }

    @Override
    public String toString(){
        return "Cost";
    }
}
