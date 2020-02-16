
package Graph;

/**
 * Interface do DiWeightedGraph (graph orientado e valorado)
 *
 * @author André Reis 170221035 Bruno Alves 170221041
 * @param <E> tipo da variavel guardada nas arestas
 * @param <V> tipo da variavel guardada nos vertices
 */
public interface Graph<V, E>{

    /**
     * @return numero de vertices totais do graph
     */
    public int numVertices();

    /**
     * @return numero de arestas totais do graph
     */
    public int numEdges();

    /**
     * @return coleção iterable dos vertices do graph
     */
    public Iterable<Vertex<V>> vertices();

    /**
     * @return coleção iterable das arestas do graph
     */
    public Iterable<Edge<E, V>> edges();

    /**
     * @param v vertice onde se pretende procurar as arestas incidentes ao mesmo
     * @return coleção iterable de arestas
     * @exception InvalidEdgeException se a aresta for inválida para o graph
     */
    public Iterable<Edge<E, V>> incidentEdges(Vertex<V> v)
            throws InvalidEdgeException;

    
    /**
     * @param v vertice onde se pretende procurar as arestas acedentes ao mesmo
     * @return coleção iterable de arestas
     * @exception InvalidEdgeException se a aresta for inválida para o graph
     */
    public Iterable<Edge<E, V>> acedenteEdges(Vertex<V> v)
            throws InvalidEdgeException;
    
    /**
     * @param v vertice onde se pretende procurar o seu oposto na aresta introduzida também em parametro
     * @param e aresta
     * @return vertice oposto
     * @exception InvalidVertexException se o vertice for inválido para o graph
     * @exception InvalidEdgeException se a aresta for inválida para o graph
     */
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e)
            throws InvalidVertexException, InvalidEdgeException;

    /**
     * Verifica se ambos os vértices são adjacentes.
     * @param u vertice (outbound)
     * @param v vertice (inbound)
     * @return true se forem adjacentes, false caso contrário
     * @exception InvalidVertexException se o vertice for inválido para o graph
     */
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v)
            throws InvalidVertexException;

    /**
     * Insere um vertice com no graph com o elemento recebido em parametro
     * @param vElement elemento guardado no vertice
     */
    public Vertex<V> insertVertex(V vElement);

    /**
     * Insere uma aresta com no graph com o elemento recebido em parametro entre dois vértices
     * @param u vertice (outbound)
     * @param v vertice (inbound)
     * @param edgeElement elemento guardado na aresta
     * @exception InvalidVertexException se o vertice for inválido para o graph
     */
    public Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E edgeElement)
            throws InvalidVertexException;

    
    /**
     * Insere uma aresta com no graph com o elemento recebido em parametro entre dois vértices
     * @param vElement1 elemento a guardar no vertice (outbound)
     * @param vElement2 elemento a guardar no vertice (inbound)
     * @param edgeElement elemento a guardar na aresta
     * @exception InvalidVertexException se o vertice for inválido para o graph
     */
    public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement)
            throws InvalidVertexException;

    /**
     * Remove um vertice correspondente ao inserido em parametro e todas as arestas incidentes e acendentes a este vertice
     * @param v vertice a remover
     * @return elemento do vertice removido
     * @exception InvalidVertexException se o vertice for inválido para o graph
     */
    public V removeVertex(Vertex<V> v) throws InvalidVertexException;

    /**
     * remove uma aresta correspondente à inserida em parametro
     * @param e aresta a remover
     * @return elemento da aresta removido
     * @exception InvalidVertexException se o vertice for inválido para o graph
     */
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException;
    
    /**
     * Altera o elemento do vertice inserida em parametro para o elemento também inserido
     * @param v vertice
     * @param newElement novo elemento para guardar no vertice
     * @return o elemento antigo do vertice
     * @exception InvalidVertexException se o vertice for inválido para o graph
     */
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException;
    
    /**
     * Altera o elemento da aresta inserida em parametro para o elemento também inserido
     * @param e aresta
     * @param newElement novo elemento para guardar na aresta
     * @return o elemento antigo da aresta
     * @exception InvalidEdgeException se a aresta for inválida para o graph
     */
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException;
}
