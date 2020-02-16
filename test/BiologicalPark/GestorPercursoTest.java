
package BiologicalPark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe GestorPercuso
 * @author André Reis 170221035 Bruno Alves 170221041
 */
public class GestorPercursoTest {
    
    private Client client;
    private GestorPercurso gp;
    InterestPoint interestPoint1, interestPoint2, interestPoint3, interestPoint4, interestPoint5, interestPoint6, interestPoint7;
    Way way1, way2, way3, way4, way5, way6, way7, way8, way9;
    
    public GestorPercursoTest() {
    }
    
   
    @Before
    public void setUp() {
        client = new Client("PrimoO", "pass");
        GestorPercurso.reset();
        gp = GestorPercurso.getInstance();
        gp.setClient(client);
        interestPoint1 = new InterestPoint(1, "Lonely Lodge");
        interestPoint2 = new InterestPoint(2, "Tilted Towers");
        interestPoint3 = new InterestPoint(3, "Linked Links");
        interestPoint4 = new InterestPoint(4, "Graphys Graphs");
        interestPoint5 = new InterestPoint(5, "Mid Point");
        interestPoint6 = new InterestPoint(6, "Walk point");
        interestPoint7 = new InterestPoint(7, "Big Foot");
        way1 =  new Way(1,Way.WAY_TYPE.BRIDGE,"Bridgy Bridge",50, 10, true);
        way2 =  new Way(2,Way.WAY_TYPE.PATH,"Bridgy Path",30, 20, true);
        way3 =  new Way(3,Way.WAY_TYPE.BRIDGE,"Bridg nicers!",100, 1, true);
        way4 =  new Way(4,Way.WAY_TYPE.PATH,"Caminho",100, 0, false);
        way5 =  new Way(5,Way.WAY_TYPE.PATH,"Walking Path",100, 5, true);
        way6 =  new Way(6,Way.WAY_TYPE.PATH,"Return Path",100, 5, true);
        way7 =  new Way(7,Way.WAY_TYPE.PATH,"Walk to Walk",300, 5, true);
        way8 =  new Way(8,Way.WAY_TYPE.BRIDGE,"Site",100, 10, false);
        way9 =  new Way(9,Way.WAY_TYPE.PATH,"Jail path",300, 5, true);
        gp.getBiologicalPark().addInterestPoint(interestPoint1);
        gp.getBiologicalPark().addInterestPoint(interestPoint2);
        gp.getBiologicalPark().addInterestPoint(interestPoint3);
        gp.getBiologicalPark().addInterestPoint(interestPoint4);
        gp.getBiologicalPark().addInterestPoint(interestPoint5);
        gp.getBiologicalPark().addInterestPoint(interestPoint6);
        gp.getBiologicalPark().addInterestPoint(interestPoint7);
        gp.getBiologicalPark().addWay(interestPoint1, interestPoint2, way1);
        gp.getBiologicalPark().addWay(interestPoint2, interestPoint3, way2);
        gp.getBiologicalPark().addWay(interestPoint3, interestPoint4, way3);
        gp.getBiologicalPark().addWay(interestPoint1, interestPoint4, way4);
        gp.getBiologicalPark().addWay(interestPoint3, interestPoint5, way5);
        gp.getBiologicalPark().addWay(interestPoint5, interestPoint1, way6);
        gp.getBiologicalPark().addWay(interestPoint2, interestPoint6, way7);
        gp.getBiologicalPark().addWay(interestPoint6, interestPoint7, way8);
        gp.getBiologicalPark().addWay(interestPoint7, interestPoint4, way9);
        System.out.println(gp.getBiologicalPark().getInterestPoints());
        System.out.println(gp.getWaysBetween(interestPoint1, interestPoint4));
    }
    
   /**
     * Teste do metodo calcBestPath, da classe GestorPercurso.
     * Pretende-se o trageto mais curto de "Lonely Lodge" (interestPoint1) até "Graphys Graphs" (interestPoint4), 
     * sendo o resultado correto o trageto, interestPoint1, interestPoint4, interestPoint1
     */
    @Test
    public void testCalcBestPath() {
        System.out.println("calcBestPath");
        System.out.println("minimumCostPath");
        
        List<InterestPoint> list = new ArrayList<>();
        list.add(interestPoint1);
        list.add(interestPoint4);
        list.add(interestPoint1);
        gp.calcBestPath(interestPoint4.getName());
        assertEquals( list, gp.getPointsToPass());
    }

    /**
     * Teste do metodo calcBest3Path, da classe GestorPercurso.
     * Pretende-se o trageto mais curto com início em "Lonely Lodge" (interestPoint1) e que passe por "Tilted Towers" (interestPoint2) e "Linked Links" (interestPoint3), 
     * sendo o resultado correto o trageto, interestPoint1, interestPoint4, interestPoint1
     */
    @Test
    public void testCalcBest3Path(){
        System.out.println("calcBest3Path");
        List<InterestPoint> list = new ArrayList<>();
        list.add(interestPoint2);
        list.add(interestPoint3);
        gp.setPointsToVisit(list);
        
        List<InterestPoint> path = new ArrayList<>();
        path.add(interestPoint1);
        path.add(interestPoint2);
        path.add(interestPoint3);
        path.add(interestPoint4);
        path.add(interestPoint1);
        
        gp.calcBest3Path();
        assertEquals( path, gp.getPointsToPass());
    }
   
    /**
     * Teste do metodo calcBest3Path, da classe GestorPercurso, em que o cliente usa uma bicicleta.
     * Pretende-se o trageto mais curto com início em "Lonely Lodge" (interestPoint1) e que passe por "Tilted Towers" (interestPoint2) e "Linked Links" (interestPoint3), 
     * sendo o resultado correto o trageto, interestPoint1, interestPoint2, interestPoint3, interestPoint5, interestPoint1
     */
    @Test
    public void testCalcBest3PathBike(){
        gp.alterTypeRoute();
        List<InterestPoint> list = new ArrayList<>();
        list.add(interestPoint2);
        list.add(interestPoint3);
        gp.setPointsToVisit(list);
        
        List<InterestPoint> path = new ArrayList<>();
        path.add(interestPoint1);
        path.add(interestPoint2);
        path.add(interestPoint3);
        path.add(interestPoint5);
        path.add(interestPoint1);
        gp.calcBest3Path();
        assertEquals( path,gp.getPointsToPass());
    }
    
    /**
     * Testa se o Readfile() está a ler os vertices e as arestas corretamente
     * @throws java.io.IOException
     */
    @Test
    public void testReadFile() throws IOException{
        gp.readFile("mapa1.dat");
        assertEquals(8, gp.getBiologicalPark().numVertices());
        assertEquals(21, gp.getBiologicalPark().numEdges());
    }
    
}
