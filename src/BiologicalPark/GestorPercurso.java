package BiologicalPark;

import AppDisplay.MessageDialog;
import Graph.Edge;
import Graph.Vertex;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa um planeamento de percursos onde é possivel definir se o percurso será a pé ou de bicicleta e se o critério a minimizar será o custo ou a distancia, 
 * permite ainda definir os ponto principais a visitar do parque biologico e gerará o caminho mais económico que passe por esses pontos. 
 * Pertence somente a um utilizador.
 *
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public final class GestorPercurso {

    private static GestorPercurso instance = new GestorPercurso(null, TYPE_ROUTE.WALKING, new CostMinimizedStrategy());
    /*Tipo de trajeto (a pé ou de bicicleta)*/
    public enum TYPE_ROUTE {WALKING, CYCLING}
    /*Representa o critério a ser minimizado*/
    private Client client;
    private BiologicalPark biologicalPark;
    private TYPE_ROUTE typeRoute;
    private MinimizedStrategy strategy;
    private Percurso percurso;

    private GestorPercurso(Client client, TYPE_ROUTE typeRoute, MinimizedStrategy strategy) {
        this.client = client;
        this.biologicalPark = new BiologicalPark();
        this.typeRoute = typeRoute != null ? typeRoute : TYPE_ROUTE.WALKING;
        this.strategy = strategy != null ? strategy : new CostMinimizedStrategy();
        percurso = new Percurso();
    }

    public static GestorPercurso getInstance() {
        return instance;
    }

    public static void reset() {
        instance = new GestorPercurso(null, TYPE_ROUTE.WALKING, new CostMinimizedStrategy());
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        if (client != null) {
            this.client = client;
        }
    }

    public BiologicalPark getBiologicalPark() {
        return biologicalPark;
    }

    public void alterTypeRoute() {
        switch(typeRoute){
            case CYCLING:
                typeRoute = TYPE_ROUTE.WALKING;
                break;
            case WALKING:
                typeRoute = TYPE_ROUTE.CYCLING;
                break;
        }
    }

    public TYPE_ROUTE getTypeRoute() {
        return typeRoute;
    }

    public void alterMinimizedStrategy() {
        if (strategy instanceof CostMinimizedStrategy) {
            strategy = new DistanceMinimizedStrategy();
        } else {
            strategy = new CostMinimizedStrategy();
        }
    }

    public MinimizedStrategy getMinimizedStrategy() {
        return strategy;
    }

    public void setPointsToVisit(List<InterestPoint> pointsToVisit) {
        if (!pointsToVisit.isEmpty())
            percurso.setTargets(pointsToVisit);
    }

    public List<InterestPoint> getPointsToPass() {
        return percurso.getPathPoints();
    }

    public int getNumberOfPointsToPass(){
        return percurso.getPathPoints().size();
    }
    
    public int getCost() {
        return percurso.getCost();
    }

    public int getDistance() {
        return percurso.getDistance();
    }

    /**
     * Lê um ficheiro e adiciona o conteúdo lido ao parque biológico
     *
     * @param fileName nome do ficheiro
     * @throws IOException
     */
    public void readFile(String fileName) throws IOException {
        ArrayList<InterestPoint> points;
        ArrayList<Way> ways;
        biologicalPark = new BiologicalPark();

        points = new ArrayList<>();
        ways = new ArrayList<>();
        String[] lines;
        lines = Files.readAllLines(new File("src/mapas/" + fileName).toPath()).toArray(new String[0]);
        for (String line : lines) {
            String[] variables = line.split(", ");
            int n_pontos = Integer.parseInt(lines[0].substring(0, 1));
            if (variables.length == 2) {
                int id_ponto = Integer.parseInt(variables[0]);
                String ponto = variables[1].trim();
                InterestPoint point = new InterestPoint(id_ponto, ponto);
                points.add(point);
                biologicalPark.addInterestPoint(point);
            } else if (variables.length == 1) {
                int n_conexoes = Integer.parseInt(line.substring(0, 1));
            } else if (variables.length > 2) {
                Way way = null;
                InterestPoint point1 = null;
                InterestPoint point2 = null;
                int id_c = Integer.parseInt(variables[0]);
                String tipo = variables[1].trim();
                String conexao = variables[2].trim();
                int id_ponto1 = Integer.parseInt(variables[3]);
                int id_ponto2 = Integer.parseInt(variables[4]);
                String navegabilidade = variables[5].trim();
                int custo = Integer.parseInt(variables[6]);
                int distancia = Integer.parseInt(variables[7]);
                if (tipo.equalsIgnoreCase("ponte")) {
                    boolean navegabilidade1 = Boolean.valueOf(navegabilidade);
                    way = new Way(id_c, Way.WAY_TYPE.BRIDGE, conexao, distancia, custo, navegabilidade1);
                    ways.add(way);
                } else if (tipo.equalsIgnoreCase("caminho")) {
                    boolean navegabilidade1 = Boolean.valueOf(navegabilidade);
                    way = new Way(id_c, Way.WAY_TYPE.PATH, conexao, distancia, custo, navegabilidade1);
                    ways.add(way);
                }
                for (InterestPoint p : points) {
                    if (p.getPointNumber() == id_ponto1) {
                        point1 = p;
                    }
                    if (p.getPointNumber() == id_ponto2) {
                        point2 = p;
                    }
                }
                biologicalPark.addWay(point1, point2, way);
            }
        }
    }

    /**
     * Encontra e devolve o ponto de interesse mais barato de chegar.
     *
     * @param unvisited lista de pontos de interesses ainda não visitados
     * @param costs map com o custo para chegar a cada ponto de interesse
     * @return ponto de interesse mais barato
     */
    private InterestPoint findLowerCostVertex(List<InterestPoint> unvisited, Map<Vertex<InterestPoint>, Double> costs) {
        double initial = Double.MAX_VALUE;
        InterestPoint bestInterestPoint = null;
        for (InterestPoint point : unvisited)
            if (costs.get(biologicalPark.checkInterestPoint(point.getName())) <= initial) {
                bestInterestPoint = point;
                initial = costs.get(biologicalPark.checkInterestPoint(point.getName()));
            }
        
        return bestInterestPoint;
    }
    
    
    /**
     * Procura e devolve os caminhos e pontes entre os dois pontos inseridos em parametro
     * @param interestPoint1 ponto 
     * @param interestPoint2 outro ponto
     * @return lista de caminho e pontes
     * @throws BiologicalParkException 
     */
    public List<Way> getWaysBetween(InterestPoint interestPoint1, InterestPoint interestPoint2) throws BiologicalParkException {
	Vertex<InterestPoint> a1 = biologicalPark.checkInterestPoint(interestPoint1.getName());
	Vertex<InterestPoint> a2 = biologicalPark.checkInterestPoint(interestPoint2.getName());
	List<Way> ways = new ArrayList<>();

	for (Edge<Way, InterestPoint> e : biologicalPark.getGraph().edges()) {
            if (e.vertices()[0].equals(a1) && e.vertices()[1].equals(a2) || e.vertices()[1].equals(a1) && e.vertices()[0].equals(a2)) {
		ways.add(e.element());
            }
	}
	return ways;
    }

    /**
     * Calcula os antecessores de cada ponto de interesse e os seus respetivos
     * custos
     *
     * @param minimizedCriterion critério a minimizar (custo ou distancia)
     * @param typeRoute tipo de trajeto (a pé ou de bicicleta)
     * @param orig ponto de origem
     * @param costs custos
     * @param predecessors antecessores
     */
    private void dijkstra(MinimizedStrategy strategy, GestorPercurso.TYPE_ROUTE typeRoute, Vertex<InterestPoint> orig, Map<Vertex<InterestPoint>, Double> costs,
            Map<Vertex<InterestPoint>, Vertex<InterestPoint>> predecessors) {

        boolean canCirculate = true;
        List<InterestPoint> unvisited = new ArrayList<>();
        for (Vertex<InterestPoint> v : biologicalPark.getInterestPoints()) {
            unvisited.add(v.element());
            costs.put(v, Double.MAX_VALUE);
            predecessors.put(orig, null);
        }
        costs.put(orig, 0.0);

        while (!unvisited.isEmpty()) {
            Vertex<InterestPoint> u = biologicalPark.checkInterestPoint(findLowerCostVertex(unvisited, costs).getName());
            List<Edge<Way, InterestPoint>> acedentCanCyclingEdges = new ArrayList<>();
            if (typeRoute == TYPE_ROUTE.CYCLING)
                acedentCanCyclingEdges = biologicalPark.getAcedentCanCyclingEdges(u.element().getName());

            if (!(acedentCanCyclingEdges.isEmpty() && typeRoute == TYPE_ROUTE.CYCLING))
                if (costs.get(u) == Double.MAX_VALUE)
                    throw new InvalidParameterException(u.element().toString() + " não tem saída");

            unvisited.remove(u.element());
            for (Edge<Way, InterestPoint> edge : biologicalPark.getGraph().acedenteEdges(u)) {
                if (typeRoute == TYPE_ROUTE.CYCLING && !edge.element().canBikesCirculate())
                    canCirculate = false;

                if (canCirculate) {
                    Vertex<InterestPoint> v = biologicalPark.getGraph().opposite(u, edge);
                    if (unvisited.contains(v.element())) {
                        double auxCost = strategy.getCriterionFromEdge(edge);
                        

                        if (u != orig)
                            auxCost += costs.get(u);

                        if (auxCost < costs.get(v)) {
                            costs.put(v, auxCost);
                            predecessors.put(v, u);
                        }
                    }
                }
                canCirculate = true;
            }
        }
    }

    /**
     * Calcula o melhor trajeto entre dois pontos, o de origem e o destino
     *
     * @param origin ponto de origem
     * @param target ponto de destino
     */
    public void calcBestPath(String target) {
        if (target.equals(biologicalPark.getOriginName())) { return; }
        percurso.setOneTarget(biologicalPark.getInterestPoint(target));
        calcBest3Path();
    }

    /**
     * Calcula o trajeto mais barato/curto que passe pelos pontos inseridos na
     * lista targets
     *
     * @param origem ponto de origem
     * @throws BiologicalParkException
     */
    public void calcBest3Path() throws BiologicalParkException {
        HashMap<Vertex<InterestPoint>, Double> pointsCriterion = new HashMap<>();
        HashMap<Vertex<InterestPoint>, Vertex<InterestPoint>> pre = new HashMap<>();
        Vertex<InterestPoint> origin = biologicalPark.checkInterestPoint(biologicalPark.getOriginName()), 
                destination = biologicalPark.checkInterestPoint(biologicalPark.getOriginName());
        List<InterestPoint> interestPoints1 = new ArrayList<>(), visitedTargets = new ArrayList<>();
        List<Way> ways1 = new ArrayList<>(), ways2 = new ArrayList<>();
        Vertex<InterestPoint> newOrigin = origin;
        percurso.reset(); 
        percurso.addTarget(origin.element());
        
        for (InterestPoint t : percurso.getTargets()) {
            if(!visitedTargets.contains(t) || t.getPointNumber() == 1){
                destination = biologicalPark.checkInterestPoint(t.getName());
                List<Edge<Way, InterestPoint>> acedentCanCyclingEdges = new ArrayList<>();
                acedentCanCyclingEdges = biologicalPark.getAcedentCanCyclingEdges(t.getName());

                if (acedentCanCyclingEdges.isEmpty() && typeRoute == TYPE_ROUTE.CYCLING) {
                    new MessageDialog("It's not possible to go to '" + t.getName() + "' by bicycle!"); return;
                }

                dijkstra(strategy, typeRoute, newOrigin, pointsCriterion, pre);
                strategy.applyCriterion(destination, pointsCriterion,percurso);
                visitedTargets.add(t);

                do {
                    interestPoints1.add(0, pre.get(destination).element());
                    ways1.add(0, getWaysBetween(destination.element(), pre.get(destination).element()).get(0));
                    if (percurso.getTargets().contains(pre.get(destination).element()) && !visitedTargets.contains(pre.get(destination).element())
                            && pre.get(destination).element().pointNumber != 1) {
                        visitedTargets.add(pre.get(destination).element());
                    }
                    destination = pre.get(destination);
                } while (destination != newOrigin);

                percurso.addPathPoints(interestPoints1);
                percurso.addPathWays(ways1);

                newOrigin = biologicalPark.checkInterestPoint(t.getName());
                interestPoints1.clear();
                ways1.clear();
            }
        }
        percurso.addPathPoint(origin.element());
        
        strategy.applyInverseCriterion(percurso);

        calcDiscontedWays();
    }
   
    
    private List<Way> calcDiscontedWays(){
        List<Way> discontedWays = new ArrayList<>();
        for (Way w : percurso.getPathWays()) {
            int count = 0;
            for (Way w1 : percurso.getPathWays()) 
                if (w.getName().equals(w1.getName())) count++;
            
            if (count > 1 && !discontedWays.contains(w)) {
                discontedWays.add(w);
                percurso.incrementCost(-(w.getCost() * (count - 1)));
            }
        }
        return discontedWays;
    }
    
    public String printPath() {
        String str = "";
        InterestPoint prev = null;
        for (InterestPoint ip : percurso.getPathPoints()) {
            if (prev != null) {
                List<Way> waysbetween = getWaysBetween(prev, ip);
                for (Way w : waysbetween) {
                    for (Edge<Way, InterestPoint> actualWay : biologicalPark.graph.incidentEdges(biologicalPark.checkInterestPoint(ip.getName()))) {
                        if (w.equals(actualWay.element())) {
                            str += "\n" + w.toString();
                            break;
                        }
                    }
                }
            }
            if (percurso.getTargets().contains(ip) && ip.pointNumber != 1) {
                str += "\n---> º " + ip.toString();
            } else {
                str += "\nº " + ip.toString();
            }
            prev = ip;
        }
        return str;
    }

    public GestorPercursoMemento createMemento() {
        return new GestorPercursoMemento(client, biologicalPark, typeRoute, strategy, percurso.getTargets(), 
                                            percurso.getPathPoints(), percurso.getCost(), percurso.getDistance());
    }

    public void setMemento(GestorPercursoMemento memento) {
        this.client = memento.getMementoClient();
        this.biologicalPark = memento.getMementoBiologicalPark();
        this.typeRoute = memento.getMementoTypeRoute();
        this.strategy= memento.getMementoMinimizedStrategy();
        percurso.setTargets(memento.getMementoPointsToVisit());
        percurso.setPathPoints(memento.getMementoPointsToPass());
        percurso.setCost(memento.getMementoCost());
        percurso.setDistance(memento.getMementoDistance());
    }
}
