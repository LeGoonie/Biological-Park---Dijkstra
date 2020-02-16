/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import Graph.Edge;
import Graph.Graph;
import Graph.DiWeightedGraph;
import Graph.Vertex;
import BiologicalPark.Way;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author André Reis 170221035 Bruno Alves 170221041
 */
public class DiWeightedGraphTest {
     private Graph diWeightedGraph;
     private Vertex<Integer> v1,v2,v3,v4,v5,v6,v7;
     private Edge<Integer, Vertex<Integer>> e1,e2,e3,e4,e5,e6,e7,e8,e9;
    
    public DiWeightedGraphTest() {
    }
    
    
    @Before
    public void setUp() {
        diWeightedGraph = new DiWeightedGraph();
        v1 = diWeightedGraph.insertVertex(1);
        v2 = diWeightedGraph.insertVertex(2);
        v3 = diWeightedGraph.insertVertex(3);
        v4 = diWeightedGraph.insertVertex(4);
        v5 = diWeightedGraph.insertVertex(5);
        v6 = diWeightedGraph.insertVertex(6);
        v7 = diWeightedGraph.insertVertex(7);
        e1 = diWeightedGraph.insertEdge(1,2,1);
        e2 = diWeightedGraph.insertEdge(2,3,2);
        e3 = diWeightedGraph.insertEdge(3,4,3);
        e4 = diWeightedGraph.insertEdge(1,4,4);
        e5 = diWeightedGraph.insertEdge(3,5,5);
        e6 = diWeightedGraph.insertEdge(5,1,6);
        e7 = diWeightedGraph.insertEdge(2,6,7);
        e8 = diWeightedGraph.insertEdge(6,7,8);
        e9 = diWeightedGraph.insertEdge(7,4,9);
    }
    
    
    /**
     * Testa ao numero de vertices
     */
    @Test
    public void testVertices() throws IOException{
        assertEquals(7, diWeightedGraph.numVertices());
    }
    
    /**
     * Testa ao numero de arestas
     */
    @Test
    public void testEdges() throws IOException{
        assertEquals(9, diWeightedGraph.numEdges());
    }
    
    
    /**
     * Testa se as arestas incidentes estão a ser develvidas corretamente
     */
    @Test
    public void testIncidentEdges() throws IOException{
        
        List<Edge<Integer, Vertex<Integer>>> ie1 = new ArrayList<>(); ie1.add(e6);
        List<Edge<Integer, Vertex<Integer>>> ie2 = new ArrayList<>(); ie2.add(e1);
        List<Edge<Integer, Vertex<Integer>>> ie3 = new ArrayList<>(); ie3.add(e2);
        List<Edge<Integer, Vertex<Integer>>> ie4 = new ArrayList<>(); ie4.add(e3); ie4.add(e4); ie4.add(e9);
        List<Edge<Integer, Vertex<Integer>>> ie5 = new ArrayList<>(); ie5.add(e5);
        List<Edge<Integer, Vertex<Integer>>> ie6 = new ArrayList<>(); ie6.add(e7);
        List<Edge<Integer, Vertex<Integer>>> ie7 = new ArrayList<>(); ie7.add(e8);
        
        assertEquals(ie1, diWeightedGraph.incidentEdges(v1));
        assertEquals(ie2, diWeightedGraph.incidentEdges(v2));
        assertEquals(ie3, diWeightedGraph.incidentEdges(v3));
        assertEquals(ie4, diWeightedGraph.incidentEdges(v4));
        assertEquals(ie5, diWeightedGraph.incidentEdges(v5));
        assertEquals(ie6, diWeightedGraph.incidentEdges(v6));
        assertEquals(ie7, diWeightedGraph.incidentEdges(v7));
    }
    
    /**
     * Testa se as arestas acedentes estão a ser develvidas corretamente
     */
    @Test
    public void testAcedenteEdges() throws IOException{
        List<Edge<Integer, Vertex<Integer>>> ae1 = new ArrayList<>(); ae1.add(e1); ae1.add(e4);
        List<Edge<Integer, Vertex<Integer>>> ae2 = new ArrayList<>(); ae2.add(e2); ae2.add(e7);
        List<Edge<Integer, Vertex<Integer>>> ae3 = new ArrayList<>(); ae3.add(e3); ae3.add(e5);
        List<Edge<Integer, Vertex<Integer>>> ae4 = new ArrayList<>();
        List<Edge<Integer, Vertex<Integer>>> ae5 = new ArrayList<>(); ae5.add(e6);
        List<Edge<Integer, Vertex<Integer>>> ae6 = new ArrayList<>(); ae6.add(e8);
        List<Edge<Integer, Vertex<Integer>>> ae7 = new ArrayList<>(); ae7.add(e9);
        
        assertEquals(ae1, diWeightedGraph.acedenteEdges(v1));
        assertEquals(ae2, diWeightedGraph.acedenteEdges(v2));
        assertEquals(ae3, diWeightedGraph.acedenteEdges(v3));
        assertEquals(ae4, diWeightedGraph.acedenteEdges(v4));
        assertEquals(ae5, diWeightedGraph.acedenteEdges(v5));
        assertEquals(ae6, diWeightedGraph.acedenteEdges(v6));
        assertEquals(ae7, diWeightedGraph.acedenteEdges(v7));
        
        
    }
    
}
