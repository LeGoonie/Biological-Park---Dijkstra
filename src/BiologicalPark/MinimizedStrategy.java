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
 * Interface do padrão Strategy para o critério a minimizar.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public interface MinimizedStrategy {
    
    public void applyCriterion(Vertex<InterestPoint> target, HashMap<Vertex<InterestPoint>, Double> costsOrDistances, Percurso percurso);
    
    public void applyInverseCriterion(Percurso percurso);
    
    public int getCriterionFromEdge(Edge<Way,InterestPoint> edge);
    
}
