package BiologicalPark;

import java.io.Serializable;

/**
 * Representa um ponto de interesse do parque biologico
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class InterestPoint implements Comparable<InterestPoint>, Serializable{
    
    protected int pointNumber;
    protected String name;
    
    public InterestPoint(int pointNumber, String name){
        this.pointNumber = pointNumber >= 0 ? pointNumber : -1;
        this.name = !name.isEmpty() ? name : "Unnamed Point";
    }

    public int getPointNumber() {
        return pointNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "(" + pointNumber + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        
        if (obj == null) { return false; }
        
        if (getClass() != obj.getClass()) { return false; }
        
        final InterestPoint other = (InterestPoint) obj;
        return this.pointNumber == other.pointNumber;
    }

    @Override
    public int compareTo(InterestPoint t) {
        return new Integer(this.getPointNumber()).compareTo(t.getPointNumber());
    }
}
