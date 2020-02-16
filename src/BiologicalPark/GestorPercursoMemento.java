
package BiologicalPark;

import java.util.ArrayList;
import java.util.List;

/**
 * Memento do estado de um objecto GestorPercurso. Guarda todas as informações do gestor de percurso e do percurso em si.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class GestorPercursoMemento {

    private int mementoCost, mementoDistance;
    private Client mementoClient;
    private BiologicalPark mementoBiologicalPark;
    private GestorPercurso.TYPE_ROUTE mementoTypeRoute;
    private MinimizedStrategy strategy;
    private List<InterestPoint> mementoPointsToVisit;
    private List<InterestPoint> mementoPointsToPass = new ArrayList<>();
    
    public GestorPercursoMemento(Client client, BiologicalPark biologicalPark, GestorPercurso.TYPE_ROUTE typeRoute, MinimizedStrategy strategy, 
                                        List<InterestPoint> pointsToVisit, List<InterestPoint> pointsToPass, int cost, int distance){
        this.mementoClient = client;
        this.mementoBiologicalPark = biologicalPark;
        this.mementoTypeRoute = typeRoute;
        this.strategy = strategy;
        this.mementoPointsToVisit = pointsToVisit;
        this.mementoPointsToPass = pointsToPass;
        this.mementoCost = cost;
        this.mementoDistance = distance;
    }
    
    public Client getMementoClient(){
        return mementoClient;
    }
    
    public BiologicalPark getMementoBiologicalPark(){
        return mementoBiologicalPark;
    }
    
    public GestorPercurso.TYPE_ROUTE getMementoTypeRoute(){
        return mementoTypeRoute;
    }
    
    public MinimizedStrategy getMementoMinimizedStrategy(){
        return strategy;
    }
    
    public List<InterestPoint> getMementoPointsToVisit(){
        return mementoPointsToVisit;
    }
    
    public List<InterestPoint> getMementoPointsToPass(){
        return mementoPointsToPass;
    }
    
    public int getMementoCost() {
        return mementoCost;
    }

    public void setMementoCost(int mementoCost) {
        this.mementoCost = mementoCost;
    }

    public int getMementoDistance() {
        return mementoDistance;
    }

    public void setMementoDistance(int mementoDistance) {
        this.mementoDistance = mementoDistance;
    }
    
    
}
