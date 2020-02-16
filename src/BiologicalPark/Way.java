package BiologicalPark;

/**
 * Representa um trajeto do parque biologico
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class Way {
    /**
     * Indica o tipo de trajeto, pode ser um caminho ou ponte.
     */
    public enum WAY_TYPE{BRIDGE, PATH} 
    
    protected int wayNumber, distance, cost;
    protected String name;
    protected boolean bikesCanCirculate;
    protected WAY_TYPE wayType;
    
    public Way(int wayNumber, String name){
        this(wayNumber, null, name, 0, 0, true);
    }
    
    public Way(int wayNumber, WAY_TYPE wayType, String name, int distance, int cost, boolean bikesCanCirculate){
        this.wayNumber = wayNumber >= 0 ? wayNumber : 0;
        this.wayType = wayType != null ? wayType : WAY_TYPE.PATH;
        this.name = !name.isEmpty() ? name : "Unnamed way " + wayNumber;
        this.distance = distance >= 0 ? distance : 0;
        this.cost = cost >= 0 ? cost : 0;
        this.bikesCanCirculate = bikesCanCirculate;
    }
    
    public int getWayNumber(){ return wayNumber;}
    
    public String getName(){ return name;}
    
    public int getDistance(){ return distance; }
    
    public int getCost(){ return cost; }
    
    public boolean canBikesCirculate(){ return bikesCanCirculate; }
    
    public boolean isBridge(){return (wayType == WAY_TYPE.BRIDGE);}
    
    @Override
    public String toString(){
        return name + ": " + distance + " metros - " + cost + " € ";
    }
}
