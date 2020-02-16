package BiologicalPark;

import Graph.Vertex;
import Graph.InvalidVertexException;
import Graph.DiWeightedGraph;
import Graph.Graph;
import Graph.Edge;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o parque biológico em si.
 *
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class BiologicalPark {

    public Graph<InterestPoint, Way> graph;

    public BiologicalPark() {
        this.graph = new DiWeightedGraph<>();
    }

    /**
     * Verifica se o ponto de interesse existe no graph e devolve um vertex do mesmo.
     *
     * @param name nome do ponto de interesse
     * @return um vertex do ponto de interesse inserido
     * @throws BiologicalParkException
     */
    public Vertex<InterestPoint> checkInterestPoint(String name) throws BiologicalParkException {
        if (name.isEmpty()) {
            throw new BiologicalParkException("Interest point name cannot be empty");
        }

        Vertex<InterestPoint> find = null;
        for (Vertex<InterestPoint> v : graph.vertices()) {
            if (v.element().getName().equals(name)) {
                find = v;
            }
        }

        if (find == null) {
            throw new BiologicalParkException("Interest point with name (" + name + ") does not exist");
        }

        return find;
    }

    private Edge<Way, InterestPoint> checkWay(String name) {
        if (name.isEmpty()) {
            throw new BiologicalParkException("Way name cannot be empty");
        }

        Edge<Way, InterestPoint> find = null;
        for (Edge<Way, InterestPoint> e : graph.edges()) {
            if (e.element().name.equals(name)) {
                find = e;
            }
        }

        if (find == null) {
            throw new BiologicalParkException("Way with name (" + name + ") does not exist");
        }

        return find;
    }

    public String getOriginName(){
        return getInterestPoint(1).getName();
    }
    
    public Graph<InterestPoint, Way> getGraph() {
        return graph;
    }

    public int numVertices() {
        return graph.numVertices();
    }

    public int numEdges() {
        return graph.numEdges();
    }

    public void addInterestPoint(InterestPoint interestPoint) throws BiologicalParkException {

        if (interestPoint == null) {
            throw new BiologicalParkException("Interest point cannot be null");
        }

        try {
            graph.insertVertex(interestPoint);
        } catch (InvalidVertexException e) {
            throw new BiologicalParkException("Interest point with number (" + interestPoint.getPointNumber() + ") already exists");
        }
    }

    public void addWay(InterestPoint interestPoint1, InterestPoint interestPoint2, Way way) throws BiologicalParkException {
        if (way == null) {
            throw new BiologicalParkException("Way is null");
        }

        Vertex<InterestPoint> p1 = checkInterestPoint(interestPoint1.getName());
        Vertex<InterestPoint> p2 = checkInterestPoint(interestPoint2.getName());

        try {
            if (way.isBridge()) {
                graph.insertEdge(p1, p2, way);
            } else {
                graph.insertEdge(p1, p2, way);
                Way inverceWay = new Way(way.getWayNumber(), Way.WAY_TYPE.PATH, way.getName(), way.getDistance(), way.getCost(), way.canBikesCirculate());
                graph.insertEdge(p2, p1, inverceWay);
            }
        } catch (InvalidVertexException e) {
            throw new BiologicalParkException("The way (" + way.toString() + ") already exists");
        }
    }

    /**
     * Remove um ponto de interesse
     *
     * @param interestPoint nome do ponto
     * @throws BiologicalParkException
     */
    public void removeInterestPoint(String interestPoint) throws BiologicalParkException {
        Vertex<InterestPoint> point = checkInterestPoint(interestPoint);
        for (Edge<Way, InterestPoint> e : graph.edges()) {
            Vertex<InterestPoint>[] ver = e.vertices();
            if (ver[0].equals(point) || ver[1].equals(point)) {
                graph.removeEdge(e);
            }

        }
        graph.removeVertex(point);
    }

    /**
     * Remove os caminhos que liga os dois pontos inseridos em parametro
     *
     * @param interestPoint1 nome do primeiro ponto
     * @param interestPoint2 nome do segundo ponto
     * @throws BiologicalParkException
     */
    public void removeWay(String interestPoint1, String interestPoint2) throws BiologicalParkException {
        Vertex<InterestPoint> point1 = checkInterestPoint(interestPoint1);
        Vertex<InterestPoint> point2 = checkInterestPoint(interestPoint2);
        for (Edge<Way, InterestPoint> e : graph.edges()) {
            Vertex<InterestPoint>[] ver = e.vertices();
            if (ver[0].equals(point1) && ver[1].equals(point2) || ver[1].equals(point1) && ver[0].equals(point2)) {
                graph.removeEdge(e);
            }
        }
    }

    /**
     * Remove o caminho com o nome inserido em parametro
     *
     * @param wayName nome do caminho
     * @throws BiologicalParkException
     */
    public void removeWay(String wayName) throws BiologicalParkException {
        for (Edge<Way, InterestPoint> e : graph.edges()) {
            if (e.element().getName().equals(wayName)) {
                graph.removeEdge(e);
            }
        }
    }

    /**
     * Devolve o ponto de interesse com o nome inserido em parametro
     *
     * @param pointName nome do ponto
     * @return ponto
     */
    public InterestPoint getInterestPoint(String pointName) {
        for (Vertex<InterestPoint> ip : graph.vertices()) {
            if (ip.element().getName().equalsIgnoreCase(pointName)) {
                return ip.element();
            }
        }
        return null;
    }

    /**
     * Devolve o ponto de interesse com o id inserido em parametro
     *
     * @param id id do ponto a procurar
     * @return ponto
     */
    public InterestPoint getInterestPoint(int id) {
        for (Vertex<InterestPoint> ip : graph.vertices()) {
            if (ip.element().getPointNumber() == id) {
                return ip.element();
            }
        }
        return null;
    }

    public Iterable<Vertex<InterestPoint>> getInterestPoints() {
        return graph.vertices();
    }

    public List<Edge<Way, InterestPoint>> getAcedentCanCyclingEdges(String interestPoint) {
        List<Edge<Way, InterestPoint>> acedentCanCyclingEdges = new ArrayList<>();
        for (Edge<Way, InterestPoint> edge : graph.acedenteEdges(checkInterestPoint(interestPoint)))
            if (edge.element().bikesCanCirculate)
                acedentCanCyclingEdges.add(edge);
        
        return acedentCanCyclingEdges;
    }

}
