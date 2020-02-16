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
 * Implementa a classe MinimizedStrategy e executa as funções para o critério "Distance".
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class DistanceMinimizedStrategy implements MinimizedStrategy {

    @Override
    public void applyCriterion(Vertex<InterestPoint> target, HashMap<Vertex<InterestPoint>, Double> costsOrDistances, Percurso percurso) {
        int distance = 0;
        distance += costsOrDistances.get(target);
        percurso.incrementDistance(distance);
    }

    @Override
    public void applyInverseCriterion(Percurso percurso) {
        for (Way w : percurso.getPathWays()) 
                    percurso.incrementCost(w.getCost());
    }

    @Override
    public int getCriterionFromEdge(Edge<Way,InterestPoint> edge) {
        return edge.element().getDistance();
    }

    @Override
    public String toString(){
        return "Distance";
    }
}
