
package BiologicalPark;

import BiologicalPark.GestorPercurso.*;
import BiologicalPark.Way.WAY_TYPE;
import Graph.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe BiologicalPark
 * @author André Reis 170221035 Bruno Alves 170221041
 */
public class BiologicalParkTest {
    private BiologicalPark biologicalPark;
    
    public BiologicalParkTest() {
    }
    
    
    @Before
    public void setUp() {
       biologicalPark = new BiologicalPark();
    }
    
    /**
     * Test of addInterestPoint method, of class BiologicalPark.
     */
    @Test
    public void testAddInterestPoint() {
        System.out.println("addInterestPoint");
        InterestPoint interestPoint = new InterestPoint(14, "Lonely Lodge");
        biologicalPark.addInterestPoint(interestPoint);
        assertEquals(1,biologicalPark.numVertices());
    }
    /**
     * Test of addWay method, of class BiologicalPark.
     */
    @Test
    public void testAddWay() {
        System.out.println("addWay");
        
        InterestPoint interestPoint1 = new InterestPoint(14, "Lonely Lodge");
        InterestPoint interestPoint2 = new InterestPoint(15, "Tilted Towers");
        Way way =  new Way(15,"Bridgy Path");
        biologicalPark.addInterestPoint(interestPoint1);
        biologicalPark.addInterestPoint(interestPoint2);
        biologicalPark.addWay(interestPoint1, interestPoint2, way);
        assertEquals(2, biologicalPark.numEdges());
        InterestPoint interestPoint3 = new InterestPoint(16, "Moisty Mire");
        Way way1 =  new Way(16,WAY_TYPE.BRIDGE,"Bridgy Bridge",10, 10, true);
        biologicalPark.addInterestPoint(interestPoint3);
        biologicalPark.addWay(interestPoint1, interestPoint3, way1);
        assertEquals(3, biologicalPark.numEdges());
    }

    /**
     * Test of removeInterestPoint method, of class BiologicalPark.
     */
    @Test
    public void testRemoveInterestPoint() {
        System.out.println("removeInterestPoint");
        InterestPoint interestPoint = new InterestPoint(14, "Lonely Lodge");
        biologicalPark.addInterestPoint(interestPoint);
        assertEquals(1,biologicalPark.numVertices());
        biologicalPark.removeInterestPoint("Lonely Lodge");
        assertEquals(0,biologicalPark.numVertices());
    }

    /**
     * Test of removeWay method, of class BiologicalPark.
     */
    @Test
    public void testRemoveWay() {
        System.out.println("removeWay");
        InterestPoint interestPoint1 = new InterestPoint(14, "Lonely Lodge");
        InterestPoint interestPoint2 = new InterestPoint(15, "Tilted Towers");
        Way way =  new Way(15,"Bridgy Path");
        biologicalPark.addInterestPoint(interestPoint1);
        biologicalPark.addInterestPoint(interestPoint2);
        biologicalPark.addWay(interestPoint1, interestPoint2, way);
        assertEquals(2, biologicalPark.numEdges());
        InterestPoint interestPoint3 = new InterestPoint(16, "Moisty Mire");
        Way way1 =  new Way(16,WAY_TYPE.BRIDGE,"Bridgy Bridge",10, 10, true);
        biologicalPark.addInterestPoint(interestPoint3);
        biologicalPark.addWay(interestPoint1, interestPoint3, way1);
        assertEquals(3, biologicalPark.numEdges());
        biologicalPark.removeWay(interestPoint1.getName(), interestPoint2.getName());
        assertEquals(1, biologicalPark.numEdges());
        biologicalPark.removeWay("Bridgy Bridge");
        assertEquals(0, biologicalPark.numEdges());
    }

    /**
     * Test of getInterestPoint method, of class BiologicalPark.
     */
    @Test
    public void testGetInterestPoint() {
        System.out.println("getInterestPoint");
        InterestPoint interestPoint1 = new InterestPoint(14, "Lonely Lodge");
        biologicalPark.addInterestPoint(interestPoint1);
        assertEquals(interestPoint1, biologicalPark.getInterestPoint("Lonely Lodge"));
    }

    

    
    
}
